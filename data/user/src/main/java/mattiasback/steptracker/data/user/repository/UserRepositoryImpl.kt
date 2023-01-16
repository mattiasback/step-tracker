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
    private val _stepUserLocalDatasource: UserLocalDataSource,
    @Named("ioDispatcher") private val _ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override val users: Flow<List<User>> = _stepUserLocalDatasource.users.map { users ->
        users.map { it.toUser() }
    }

    override suspend fun createEmptyUser(steps: Long): Long =
        withContext(_ioDispatcher) {
            return@withContext _stepUserLocalDatasource.createNewUser(steps = steps)
        }

    override suspend fun updateSteps(userId: Long, steps: Long) =
        withContext(_ioDispatcher) {
            _stepUserLocalDatasource.updateSteps(id = userId, steps = steps)
        }


}