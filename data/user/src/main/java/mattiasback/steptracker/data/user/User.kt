package mattiasback.steptracker.data.user

import mattiasback.steptracker.DbUser

data class User(
    val userId: Long,
    val steps: Long
)

internal fun DbUser.toUser(): User = User(userId = id, steps = steps)