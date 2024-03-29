package mattiasback.steptracker.data.user.database

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
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
            _database.userQueries.updateSteps(steps, id)
        }

    override suspend fun updateDailyGoal(id: Long, dailyGoal: Long) {
        withContext(_ioDispatcher) {
            _database.userQueries.updateSteps(dailyGoal, id)
        }
    }

    override fun getCurrentSteps() =
        _database.userQueries.getCurrentSteps().asFlow().mapToOne(_ioDispatcher)


    override suspend fun createNewUser(steps: Long): Long =
        withContext(_ioDispatcher) {
            val id = _database.userQueries.transactionWithResult {
                _database.userQueries.insertNewUser(steps)
                _database.userQueries.selectLastInsertedRow().executeAsOne()
            }
            return@withContext id
        }

    override suspend fun firstUser(): DbUser? =
        withContext(_ioDispatcher) {
            return@withContext _database.userQueries.firstUser().executeAsOneOrNull()
        }
}