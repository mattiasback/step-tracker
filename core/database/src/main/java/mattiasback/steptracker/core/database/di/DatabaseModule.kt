package mattiasback.steptracker.core.database.di

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mattiasback.steptracker.Database

@Module
@InstallIn(SingletonComponent::class)
class DatabaseBindModule {

    @Provides
    fun provideIaDatabase(@ApplicationContext context: Context): Database =
        Database(driver = AndroidSqliteDriver(Database.Schema, context, "steptrackerdata.db"))

}