package mattiasback.steptracker.data.user.repository

import kotlinx.coroutines.flow.Flow
import mattiasback.steptracker.data.user.User

interface UserRepository {
    val users: Flow<List<User>>
    suspend fun createEmptyUser(steps: Long = 0): Long
    suspend fun updateSteps(userId: Long, steps: Long)
    suspend fun updateDailyGoal(userId: Long, dailyGoal: Long)
}