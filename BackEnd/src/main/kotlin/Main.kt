import Controller.HealthController
import Util.LocalDateSerializer
import Util.LocalDateTimeSerializer
import spark.Spark.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalDateTime


val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
        .create()


fun main(args: Array<String>) {
    port(8050)
    get("/hello") { req, res -> "Hello World" }

    path("v1/") {
        get("health", HealthController)
    }
}

//https://medium.com/@v.souhrada/build-rest-service-with-kotlin-spark-java-and-requery-part-1-1798844fdf04

data class Entry(val message: String)