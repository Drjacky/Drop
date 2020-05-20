package app.web.drjackycv.domain.base

sealed class FileResult<T> {

    data class Success<T>(val result: T) : FileResult<T>()

    data class Failed<T>(val exception: Throwable) : FileResult<T>()

}