package ru.vk.shpk.feedmodel

data class FeedModelCheckLogin(
    val token: String ,
    val empty: Boolean = false
)

/** интерфейс, чтобы подгружались посты, которые были написаны в предыдущю сессию------------ **/
sealed interface FeedModelState {
    object Idle : FeedModelState
    object Loading : FeedModelState
    object Refresh : FeedModelState
    object Error : FeedModelState
}
