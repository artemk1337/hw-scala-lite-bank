package misis.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._

import misis.repository.AccountRepository

import scala.concurrent.ExecutionContext

class Route(repository: AccountRepository)(implicit ec: ExecutionContext) extends FailFastCirceSupport {

    def routes =
        (path("hello") & get) {
            complete("ok")
        } ~
            (path("accounts") & get) {
                var res = repository.listAccounts()
                complete(StatusCodes.OK -> res.toString)
            } ~
            (path("account" / IntNumber / "money") & get) { accountId =>
                var (money, found) = repository.accountMoneyById(accountId)
                if (found) {
                    complete(StatusCodes.OK -> money.toString)
                } else {
                    complete(StatusCodes.NotFound -> "Account not found")
                }
            }
}
