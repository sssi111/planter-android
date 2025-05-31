package com.example.planter.di

import com.example.planter.data.repository.NotificationRepository
import com.example.planter.data.repository.PlantRepository
import com.example.planter.data.repository.UserRepository
import com.example.planter.data.repository.RecommendationRepository
import com.example.planter.data.repository.impl.PlantRepositoryImpl
import com.example.planter.data.repository.impl.UserRepositoryImpl
import com.example.planter.data.repository.impl.RecommendationRepositoryImpl
import com.example.planter.data.network.PlantApi
import com.example.planter.data.api.PlanterApi
import com.example.planter.data.auth.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNotificationRepository(api: PlantApi): NotificationRepository {
        return NotificationRepository(api)
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
    fun provideRecommendationRepository(api: PlanterApi): RecommendationRepository {
        return RecommendationRepositoryImpl(api)
    }
} 