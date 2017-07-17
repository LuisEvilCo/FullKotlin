package Controller

import Util.LoggableRoute
import Util.prepare
import spark.Request
import spark.Response


object HealthController : LoggableRoute() {
    override fun handle(request: Request, response: Response): Any {
        return response.prepare(200, "ok")
    }
}