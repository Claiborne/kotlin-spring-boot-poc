package claiborne.firstapi.service

import claiborne.firstapi.datasource.BankDataSource
import claiborne.firstapi.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> {
        return dataSource.getBanks()
    }

    fun getBank(accountNumber: String): Bank {
        return dataSource.getBank(accountNumber)
    }

    fun createBank(bank: Bank): Bank {
        return dataSource.createBank(bank)
    }
}