package Dto

import Util.safeInt
import Util.safeString
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.reflect.Type

@JsonAdapter(BarbellUserDeserializer::class)
data class BarbellUser(
        @SerializedName("username")
        val userName: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("email")
        val email: String
) : Serializable

class BarbellUserDeserializer: JsonDeserializer<BarbellUser> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): BarbellUser {
        json ?: throw IllegalStateException("JSON does not exist")
        typeOfT ?: throw IllegalStateException("Type does not exist")
        context ?: throw IllegalStateException("DeserializationContext does not exist")

        val jSon = json.asJsonObject

        return BarbellUser(
                userName = jSon.get("username").safeString(context),
                password = jSon.get("password").safeString(context),
                email = jSon.get("email").safeString(context)
        )
    }
}

@JsonAdapter(BarbellUserProfileDeserializer::class)
data class BarbellUserProfile(
        @SerializedName("firstName")
        val firstName: String,
        @SerializedName("lastName")
        val lastName: String,
        @SerializedName("age")
        val age: Int
)

class BarbellUserProfileDeserializer: JsonDeserializer<BarbellUserProfile> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): BarbellUserProfile {
        json ?: throw IllegalStateException("JSON does not exist")
        typeOfT ?: throw IllegalStateException("Type does not exist")
        context ?: throw  IllegalStateException("DeserializationContext does not exist")

        val jSon = json.asJsonObject

        return BarbellUserProfile(
                firstName = jSon.get("firstName").safeString(context),
                lastName = jSon.get("lastName").safeString(context),
                age = jSon.get("age").safeInt(context)
        )
    }
}