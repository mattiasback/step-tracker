package mattiasback.steptracker.data.user.database

import kotlinx.coroutines.flow.Flow
import mattiasback.steptracker.DbUser

internal interface UserLocalDataSource {
    val users: Flow<List<DbUser>>
    suspend fun createNewUser(steps: Long = 0): Long
    suspend fun updateSteps(id: Long, steps: Long)
    suspend fun updateDailyGoal(id: Long, dailyGoal: Long)
    fun getCurrentSteps(): Flow<Long>
    suspend fun firstUser(): DbUser?
}