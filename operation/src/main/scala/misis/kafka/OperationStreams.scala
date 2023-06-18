package misis.kafka

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import io.circe.generic.auto._
import misis.WithKafka
import misis.model.{ExternalAccountUpdate, ExternalAccountUpdated}

import scala.concurrent.{ExecutionContext, Future}

class OperationStreams()(implicit val system: ActorSystem, executionContext: ExecutionContext) extends WithKafka {
    override def group: String = "operation"

    kafkaSource[ExternalAccountUpdated]
        .filter(command => (!command.is_source) && command.success)
        .mapAsync(1) { command =>
            Future.successful(produceCommand(ExternalAccountUpdate(command.srcAccountId, command.dstAccountId, command.money,
                is_source = false, categoryId = command.categoryId)))
        }
        .to(Sink.ignore)
        .run()
}
