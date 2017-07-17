package Util

import com.google.gson.*
import com.kevinmost.koolbelt.extension.typeToken
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime> {
    override fun serialize(p0: LocalDateTime?, p1: Type?, p2: JsonSerializationContext?): JsonElement {
        val zoneId = ZoneId.systemDefault()

        val epoch = p0?.atZone(zoneId)?.toEpochSecond()

        return JsonPrimitive(epoch)
    }
}

class LocalDateSerializer : JsonSerializer<LocalDate> {
    override fun serialize(p0: LocalDate?, p1: Type?, p2: JsonSerializationContext?): JsonElement {
        val zoneId = ZoneId.systemDefault()

        val epoch = p0?.atStartOfDay(zoneId)?.toEpochSecond()

        return JsonPrimitive(epoch)
    }
}

fun JsonElement?.safeBoolean(context: JsonDeserializationContext, default: Boolean = false): Boolean {
    return this.deserializeSafe(context, { default }) {
        it.isJsonPrimitive
    }
}

fun JsonElement?.safeInt(context: JsonDeserializationContext, default: Int = 0): Int {
    return this.deserializeSafe(context, { default }) {
        it.isJsonPrimitive
    }
}

fun JsonElement?.safeString(context: JsonDeserializationContext, default: String = ""): String {
    return this.deserializeSafe(context, { default }) {
        it.isJsonPrimitive
    }
}

inline fun <reified T : Any> JsonElement?.safePrimitive(context: JsonDeserializationContext,
                                                        provider: (JsonElement?) -> T): T {
    return this.deserializeSafe(context, provider) {
        it.isJsonPrimitive
    }
}

inline fun <reified T : Any> JsonElement?.safeObject(context: JsonDeserializationContext,
                                                     provider: (JsonElement?) -> T): T {
    return this.deserializeSafe(context, provider) {
        it.isJsonObject
    }
}

fun JsonElement?.safeDate(context: JsonDeserializationContext,
                          default: LocalDate = LocalDate.now()): LocalDate {
    val dateString = this.safeString(context)
    return if (!dateString.isNullOrBlank()) {
        LocalDate.parse(dateString)
    } else {
        default
    }
}

fun JsonElement?.safeDateTime(context: JsonDeserializationContext,
                              default: LocalDateTime = LocalDateTime.now()): LocalDateTime {
    val dateString = this.safeString(context)
    return if (!dateString.isNullOrBlank()) {
        LocalDateTime.parse(dateString)
    } else {
        default
    }
}

inline fun <reified T : Any> JsonElement?.safeArray(context: JsonDeserializationContext,
                                                    provider: (JsonElement?) -> T): T {
    return this.deserializeSafe(context, provider) {
        it.isJsonArray
    }
}

inline fun <reified T : Any> JsonElement?.deserializeSafe(context: JsonDeserializationContext,
                                                          provider: (JsonElement?) -> T, predicate: (JsonElement) -> Boolean?): T {
    this ?: return provider(this)

    return if (predicate(this) ?: false) {
        context.deserialize(this, T::class.java)
    } else {
        provider(this)
    }
}

inline fun <reified T> Gson.fromJson(json: String): T? = this.fromJson(json, typeToken<T>().type)