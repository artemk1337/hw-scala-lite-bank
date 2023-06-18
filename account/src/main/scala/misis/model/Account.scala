package misis.model

case class Account(
                      id: Int,
                      money: Int
                  ) {
    def update(value: Int): Account = this.copy(money = money + value)
}

trait Command

case class ListAccounts() extends Command

case class CreateAccount(
                            accountId: Int
                        ) extends Command

case class AccountUpdate(
                            accountId: Int,
                            money: Int,
                            isFee: Boolean = false
                        ) extends Command

case class ExternalAccountUpdate(
                                    srcAccountId: Int,
                                    dstAccountId: Int,
                                    money: Int,
                                    categoryId: Int,
                                    is_source: Boolean
                                ) extends Command

case class FeeRequest(
                         srcAccountId: Int,
                         money: Int
                     ) extends Command

trait Event

case class AccountUpdated(
                             accountId: Int,
                             money: Int,
                             success: Boolean,
                             balance: Int
                         ) extends Event

case class ExternalAccountUpdated(
                                     srcAccountId: Int,
                                     dstAccountId: Int,
                                     money: Int,
                                     success: Boolean,
                                     categoryId: Int,
                                     is_source: Boolean
                                 ) extends Event

case class ExternalTransactionComplete(
                                          srcAccountId: Int,
                                          dstAccountId: Int,
                                          money: Int,
                                          categoryId: Int
                                      ) extends Event
