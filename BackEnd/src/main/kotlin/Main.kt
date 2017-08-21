import Client.BarbellClient
import Controller.HealthController
import Dto.BarbellUser
import Util.LocalDateSerializer
import Util.LocalDateTimeSerializer
import Util.prepare
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import org.apache.log4j.BasicConfigurator
import spark.Service
import spark.Spark


val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
        .create()


fun main(args: Array<String>) {
    BasicConfigurator.configure()

    Service.ignite().apply {
        Spark.port(8050)
        Spark.get("/hello") { _, _ -> "Hello World" }

        Spark.path("v1/") {
            Spark.get("health", HealthController)
        }

        Spark.get("/user", { request, response ->
            val user = BarbellUser(
                    userName = "Username",
                    password = "pass",
                    email = "test@mail.com"
            )
            response.prepare(200, gson.toJson(user))
        })

        Spark.get("/fetchUser"){request, response ->

            val user = BarbellClient.fetchUser("http://localhost:3000")

            response.prepare(200, user.userName)
        }


    }

}

data class Entry(val message: String)