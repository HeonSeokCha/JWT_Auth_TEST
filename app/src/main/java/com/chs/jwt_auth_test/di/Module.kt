package com.chs.jwt_auth_test.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chs.jwt_auth_test.common.Constants
import com.chs.jwt_auth_test.data.DataStoreSource
import com.chs.jwt_auth_test.data.FakeAuthenticator
import com.chs.jwt_auth_test.data.service.LocalService
import com.chs.jwt_auth_test.data.service.TokenService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFERENCES_NAME
)

@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthenticatedClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class TokenRefreshClient

    @Singleton
    @Provides
    fun provideDtaStorePref(@ApplicationContext context: Context): DataStoreSource {
        return DataStoreSource(context.dataStore)
    }

    @Singleton
    @Provides
    fun provideJson() = Json {
        Json {
            this.ignoreUnknownKeys = true
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        jsonProp: Json
    ): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(jsonProp)
            }

            install(Auth) {
                bearer {
                    sendWithoutRequest {
                        true
                    }

                }
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                url(Constants.BASE_URL)
            }

        }
    }

    @Singleton
    @Provides
    fun provideFakService(
        @AuthenticatedClient okHttpClient: OkHttpClient,
        json: Json
    ): LocalService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                json.asConverterFactory(MediaType.parse("application/json")!!)
            )
            .build()
            .create(LocalService::class.java)
    }

    @Singleton
    @Provides
    fun provideTokenService(
        @TokenRefreshClient okHttpClient: OkHttpClient,
        json: Json
    ): TokenService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                json.asConverterFactory(MediaType.parse("application/json")!!)
            )
            .build()
            .create(TokenService::class.java)
    }

    @Provides
    @Singleton
    @TokenRefreshClient
    fun provideTokenClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.TIME_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(Constants.TIME_READ, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIME_WRITE, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @AuthenticatedClient
    fun provideOkHttpClient(
        authAuthenticator: FakeAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.TIME_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(Constants.TIME_READ, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIME_WRITE, TimeUnit.SECONDS)
            .authenticator(authAuthenticator)
            .build()
    }
}