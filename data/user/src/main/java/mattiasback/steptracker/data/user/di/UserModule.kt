package mattiasback.steptracker.data.user.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mattiasback.steptracker.data.user.database.UserLocalDataSource
import mattiasback.steptracker.data.user.database.UserLocalDataSourceImpl
import mattiasback.steptracker.data.user.repository.UserRepository
import mattiasback.steptracker.data.user.repository.UserRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
internal abstract class OverviewModule {

    @Binds
    abstract fun bindsOverviewLocalDataSource(implementation: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    abstract fun bindsOverviewRepository(implementation: UserRepositoryImpl): UserRepository
}