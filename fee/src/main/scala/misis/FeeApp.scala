package misis

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import misis.kafka.FeeStreams
import misis.repository.FeeRepository
import misis.route.FeeRoute

object FeeApp extends App {
    implicit val system: ActorSystem = ActorSystem("MyApp")
    implicit val ec = system.dispatcher

    val port = ConfigFactory.load().getInt("port")
    val limit = ConfigFactory.load().getInt("limit")
    val percentage = ConfigFactory.load().getInt("percentage")

    private val repository = new FeeRepository(limit, percentage)
    private val streams = new FeeStreams(repository)
    private val route = new FeeRoute()

    Http().newServerAt("0.0.0.0", port).bind(route.routes)
}
