package github.ouchadam.lce

enum class LceStatus {
    LOADING_EMPTY,
    LOADING_ON_CONTENT,

    ERROR_EMPTY,
    ERROR_ON_CONTENT,

    IDLE_EMPTY,
    IDLE_WITH_CONTENT;

    companion object {

        fun <T> next(lce: Lce<T, Throwable>, currentStatus: LceStatus): LceStatus {
            return when (lce) {
                is Lce.Loading -> {
                    return when (currentStatus) {
                        LOADING_EMPTY -> LOADING_EMPTY
                        LOADING_ON_CONTENT -> LOADING_ON_CONTENT
                        ERROR_EMPTY -> LOADING_EMPTY
                        ERROR_ON_CONTENT -> LOADING_ON_CONTENT
                        IDLE_EMPTY -> LOADING_EMPTY
                        IDLE_WITH_CONTENT -> LOADING_ON_CONTENT
                    }
                }
                is Lce.Error -> {
                    return when (currentStatus) {
                        LOADING_EMPTY -> ERROR_EMPTY
                        LOADING_ON_CONTENT -> ERROR_ON_CONTENT
                        ERROR_EMPTY -> ERROR_EMPTY
                        ERROR_ON_CONTENT -> ERROR_ON_CONTENT
                        IDLE_EMPTY -> ERROR_EMPTY
                        IDLE_WITH_CONTENT -> ERROR_ON_CONTENT
                    }
                }
                is Lce.Content -> {
                    IDLE_WITH_CONTENT
                }
            }
        }

    }

}