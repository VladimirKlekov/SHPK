package ru.vk.shpk.feedmodel

/** интерфейс, чтобы подгружались посты, которые были написаны в предыдущю сессию------------ **/
sealed interface FeedModelState {
    object Idle : FeedModelState
    object Loading : FeedModelState
    object Refresh : FeedModelState
    object Error : FeedModelState
}
