package Util.Exception

class BadRequestException(message: String? = null,
                          cause: Throwable? = null) : BarbellException(message, cause) {
    override val statusCode: Int
        get() = 400
}

class UnauthorizedException(message: String? = null,
                            cause: Throwable? = null) : BarbellException(message, cause) {
    override val statusCode: Int
        get() = 401
}

class ForbiddenException(message: String? = null,
                         cause: Throwable? = null) : BarbellException(message, cause) {
    override val statusCode: Int
        get() = 403
}

class NotFoundException(message: String? = null,
                        cause: Throwable? = null) : BarbellException(message, cause) {
    override val statusCode: Int
        get() = 404
}

class TooManyRequestsException(message: String? = null,
                               cause: Throwable? = null) : BarbellException(message, cause) {
    override val statusCode: Int
        get() = 429
}

class InternalErrorException(message: String? = null,
                             cause: Throwable? = null) : BarbellException(message, cause) {
    override val statusCode: Int
        get() = 500
}