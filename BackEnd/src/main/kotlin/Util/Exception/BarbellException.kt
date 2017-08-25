package Util.Exception

abstract class BarbellException(
        message: String? = "Internal Error",
        cause: Throwable? = null
) : Exception(message,cause) {
    abstract val statusCode: Int
}