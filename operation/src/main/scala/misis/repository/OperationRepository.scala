package misis.repository

import misis.TopicName
import misis.kafka.OperationStreams
import misis.model.{CreateAccount, ExternalAccountUpdate, ListAccounts, TransferStart}
import io.circe.generic.auto._

import scala.concurrent.Future

class OperationRepository(streams: OperationStreams) {
    implicit val commandTopicName1: TopicName[ExternalAccountUpdate] = streams.simpleTopicName[ExternalAccountUpdate]
    implicit val commandTopicName2: TopicName[CreateAccount] = streams.simpleTopicName[CreateAccount]
    implicit val commandTopicName3: TopicName[ListAccounts] = streams.simpleTopicName[ListAccounts]

    def transfer(transfer: TransferStart): Unit = {
        if (transfer.value > 0) {
            streams.produceCommand(
                ExternalAccountUpdate(transfer.sourceId, transfer.destinationId, transfer.value, is_source = true,
                    categoryId = transfer.categoryId)
            )
        }
    }

    def createAccount(create: CreateAccount): Unit = {
        streams.produceCommand(create)
    }

    def listAccounts(accountList: ListAccounts): Unit = {
        streams.produceCommand(accountList)
    }
}
