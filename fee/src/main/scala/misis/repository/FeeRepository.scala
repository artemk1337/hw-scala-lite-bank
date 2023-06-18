package misis.repository

import scala.collection.mutable

class FeeRepository(limit: Int, percentage: Int) {

    var feeMap: mutable.Map[Int, Int] = mutable.Map()

    def getFee(transactionMoney: Int): Int = {
        transactionMoney * percentage / 100
    }

    def updateLimit(accountId: Int, transactionMoney: Int): Int = {
        if (feeMap.contains(accountId))
            feeMap(accountId) = feeMap(accountId) - transactionMoney
        else
            feeMap(accountId) = limit - transactionMoney

        feeMap(accountId)
    }
}
