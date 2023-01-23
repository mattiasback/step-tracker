package mattiasback.steptracker.data.user.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import mattiasback.steptracker.data.user.User
import mattiasback.steptracker.data.user.database.UserLocalDataSource
import mattiasback.steptracker.data.user.toUser
import javax.inject.Inject
import javax.inject.Named

internal class UserRepositoryImpl @Inject constructor(
    private val _userLocalDataSource: UserLocalDataSource,
    @Named("ioDispatcher") private val _ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override val users: Flow<List<User>> = _userLocalDataSource.users.map { users ->
        users.map { it.toUser() }
    }

    override suspend fun createEmptyUser(steps: Long): Long =
        withContext(_ioDispatcher) {
            _userLocalDataSource.createNewUser(steps = steps)
        }

    override suspend fun updateSteps(userId: Long, steps: Long) =
        withContext(_ioDispatcher) {
            _userLocalDataSource.updateSteps(id = userId, steps = steps)
        }

    override suspend fun updateDailyGoal(userId: Long, dailyGoal: Long) =
        withContext(_ioDispatcher) {
            _userLocalDataSource.updateDailyGoal(id = userId, dailyGoal = dailyGoal)
        }

    override suspend fun getCurrentSteps(): Flow<Long> =
        withContext(_ioDispatcher) {
            _userLocalDataSource.getCurrentSteps()
        }

    override suspend fun firstUser(): User? =
        withContext(_ioDispatcher) {
            _userLocalDataSource.firstUser()?.toUser()
        }

}