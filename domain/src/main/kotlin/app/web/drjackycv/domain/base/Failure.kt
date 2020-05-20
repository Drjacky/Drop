package app.web.drjackycv.domain.base

sealed class Failure(var retryAction: () -> Unit) : Throwable() {

    abstract class FailureWithMessage(open val msg: String) : Failure({})

    class Error(override val msg: String) : FailureWithMessage(msg)

    class FileReaderException(override val msg: String) : FailureWithMessage(msg)

}