package misis.repository

import misis.model.Cashback
import misis.model.ExternalTransactionComplete


class CashbackRepository(category: Int, cashback_percentage: Int) {
    val CashBackCategory: Int = category

    def getCashback(transaction: ExternalTransactionComplete): Cashback = {
        if (category == transaction.categoryId)
            Cashback(is_allowed = true, value = cashback_percentage * transaction.money / 100)
        else
            Cashback(is_allowed = false, value = 0)
    }
}
