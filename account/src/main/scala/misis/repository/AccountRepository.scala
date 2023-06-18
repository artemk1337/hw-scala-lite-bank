package misis.repository

import misis.model.{Account, AccountUpdated}

class AccountRepository(startId: Int) {
    var accountList: List[Account] = List()
    var startIdFrom: Int = startId

    def listAccounts(): Iterable[String] = {
        println(s"Список аккаунтов: ${accountList}")
        accountList.map(_.toString)
    }

    def accountMoneyById(accountId: Int): (Int, Boolean) = {
        accountList.indexWhere(acc => acc.id == accountId) match {

            case -1 =>
                println(s"Аккаунт ${accountId} не найден")
                (0, false)

            case index =>
                val money = accountList(index).money

                println(s"Денег на аккаунте ${accountId}: ${money}")
                (money, true)
        }
    }

    private type AccountCreated = Boolean

    def create(accountId: Int): AccountCreated = {
        val newAccount = Account(accountId, money = 0)

        if (accountList.exists(acc => acc.id == accountId)) {
            println(s"Account с id ${accountId} уже существует")
            false
        }
        else {
            accountList = accountList :+ newAccount
            true
        }
    }

    def update(value: Int, accountId: Int, isFee: Boolean = false): AccountUpdated = {
        accountList.indexWhere(acc => acc.id == accountId) match {

            case -1 =>
                AccountUpdated(accountId = accountId, money = value, success = false, balance = 0)

            case index =>
                val srcAccount = accountList(index)
                val dstAccount = Account(srcAccount.id, srcAccount.money + value)

                if (dstAccount.money >= 0 || isFee) {
                    accountList = accountList.map(acc => if (acc.id == dstAccount.id) dstAccount else acc)
                    println(
                        s"Аккаунт ${dstAccount.id} обновлен на сумму ${value}. Баланс: ${accountList(index).money}"
                    )
                    AccountUpdated(accountId = accountId, money = value, success = true, balance = dstAccount.money)
                }
                else
                    AccountUpdated(accountId = accountId, money = value, success = false, balance = srcAccount.money)
        }
    }

}
