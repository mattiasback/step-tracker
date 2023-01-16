package mattiasback.steptracker.data.user.database

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import mattiasback.steptracker.Database
import mattiasback.steptracker.DbUser
import javax.inject.Inject
import javax.inject.Named

internal class UserLocalDataSourceImpl @Inject constructor(
    private val _database: Database,
    @Named("ioDispatcher")
    private val _ioDispatcher: CoroutineDispatcher
) : UserLocalDataSource {

    override val users: Flow<List<DbUser>> =
        _database.userQueries.selectAllUsers().asFlow().mapToList(_ioDispatcher)

    override suspend fun updateSteps(id: Long, steps: Long) =
        withContext(_ioDispatcher) {
            _database.userQueries.updateUserSteps(steps, id)
        }

    override suspend fun createNewUser(steps: Long): Long =
        withContext(_ioDispatcher) {
            val id = _database.userQueries.transactionWithResult {
                _database.userQueries.insertNewUser(steps)
                _database.userQueries.selectLastInsertedRow().executeAsOne()
            }
            return@withContext id
        }
}