package com.example.planter.di

import android.content.Context
import com.example.planter.data.api.PlanterApi
import com.example.planter.data.auth.AuthInterceptor
import com.example.planter.data.auth.AuthManager
import com.squareup.moshi.Moshi
import com.example.planter.data.repository.PlantRepository
import com.example.planter.data.repository.RecommendationRepository
import com.example.planter.data.repository.ShopRepository
import com.example.planter.data.repository.UserRepository
import com.example.planter.data.repository.impl.PlantRepositoryImpl
import com.example.planter.data.repository.impl.RecommendationRepositoryImpl
import com.example.planter.data.repository.impl.ShopRepositoryImpl
import com.example.planter.data.repository.impl.UserRepositoryImpl
import dagger.hilt.android.qualifiers.ApplicationContext
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
object AppModule {

    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager {
        return AuthManager(context)
    }
    
    @Provides
    @Singleton
    fun provideAuthInterceptor(authManager: AuthManager): AuthInterceptor {
        return AuthInterceptor(authManager)
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(com.example.planter.data.api.adapter.OffsetDateTimeAdapter())
            .build()
    }

    @Provides
    @Singleton
    fun providePlanterApi(okHttpClient: OkHttpClient, moshi: Moshi): PlanterApi {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Локальный сервер разработки для Android эмулятора
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PlanterApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlantRepository(api: PlanterApi): PlantRepository {
        return PlantRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: PlanterApi, authManager: AuthManager): UserRepository {
        return UserRepositoryImpl(api, authManager)
    }
    
    @Provides
    @Singleton
    fun provideShopRepository(api: PlanterApi): ShopRepository {
        return ShopRepositoryImpl(api)
    }
    
    @Provides
    @Singleton
    fun provideRecommendationRepository(api: PlanterApi): RecommendationRepository {
        return RecommendationRepositoryImpl(api)
    }
}