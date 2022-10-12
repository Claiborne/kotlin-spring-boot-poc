package claiborne.firstapi.datasource

import claiborne.firstapi.model.Bank

interface BankDataSource {

    fun getBanks(): Collection<Bank>
    fun getBank(accountNumber: String): Bank
    fun createBank(bank: Bank): Bank
}