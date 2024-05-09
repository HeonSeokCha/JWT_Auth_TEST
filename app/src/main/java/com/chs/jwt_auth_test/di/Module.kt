package com.chs.jwt_auth_test.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chs.jwt_auth_test.common.Constants
import com.chs.jwt_auth_test.data.DataStoreSource
import com.chs.jwt_auth_test.data.FakeAuthenticator
import com.chs.jwt_auth_test.data.FakeService
import com.chs.jwt_auth_test.data.TokenService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideJson(): Json {
        return Json {
            this.ignoreUnknownKeys = true
        }
    }

    @Singleton
    @Provides
    fun provideFakService(
        @AuthenticatedClient okHttpClient: OkHttpClient,
        json: Json
    ): FakeService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_FAKE_API_URL)
            .addConverterFactory(
                json.asConverterFactory(MediaType.parse("application/json")!!)
            )
            .build()
            .create(FakeService::class.java)
    }

    @Singleton
    @Provides
    fun provideTokenService(
        @TokenRefreshClient okHttpClient: OkHttpClient,
        json: Json
    ): TokenService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_FAKE_API_URL)
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