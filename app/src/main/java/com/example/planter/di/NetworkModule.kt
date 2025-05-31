package com.example.planter.di

import com.example.planter.data.network.PlantApi
import com.example.planter.data.api.PlanterApi
import com.example.planter.data.auth.AuthInterceptor
import com.example.planter.data.api.adapter.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(OffsetDateTimeAdapter())
            .add(LanguageAdapter())
            .add(SunlightLevelAdapter())
            .add(HumidityLevelAdapter())
            .add(NotificationTypeAdapter())
            .add(UUIDAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Special Android emulator address that points to host machine's localhost
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providePlantApi(retrofit: Retrofit): PlantApi {
        return retrofit.create(PlantApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlanterApi(retrofit: Retrofit): PlanterApi {
        return retrofit.create(PlanterApi::class.java)
    }
} 