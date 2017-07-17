package Util

import gson
import mu.KLoggable
import mu.KLogging
import spark.Request
import spark.Response
import spark.Route


/**
 * This allows us to create Route objects that have their own logger
 */
abstract class LoggableRoute : Route, KLoggable by KLogging()

/**
 * This function converts the body of a request into a map of [K] to [V]
 */
@Suppress("UNCHECKED_CAST")
fun <K, V> Request.bodyAsMap(): Map<K, V> = bodyAs(Map::class.java) as Map<K, V>

/**
 * This function converts the body of a request into an instance
 * of [clazz].
 */
fun <T> Request.bodyAs(clazz: Class<T>): T = gson.fromJson(body(), clazz)

/**
 * Gets query params in a "flat" state
 * Example: ?id=1,2,3&id=4&id=5,6 produces [1, 2, 3, 4, 5, 6]
 */
fun Request.flatParams(it: String): List<String>? {
    val params = queryParamsValues(it)?.map {
        it.split(",")
    }?.flatMap { it } ?: return null

    return if (params.isNotEmpty()) {
        params
    } else {
        null
    }
}

/**
 * This function helps us format our responses
 */
fun Response.prepare(statusCode: Int, content: Any): String {
    status(statusCode)
    type("application/json")
    body(gson.toJson(content))
    return body()
}