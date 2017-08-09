import Controller.HealthController
import Util.LocalDateSerializer
import Util.LocalDateTimeSerializer
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

    }

}

data class Entry(val message: String)