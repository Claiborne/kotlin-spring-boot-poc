package claiborne.firstapi.datasource.mock

import claiborne.firstapi.datasource.BankDataSource
import claiborne.firstapi.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {

    val banks = mutableListOf(
        Bank("1234", 2.0, 1),
        Bank("1235", 3.4, 1)
    )

    // shorthand return
    // override fun getBanks(): Collection<Bank> = banks

    override fun getBanks(): Collection<Bank> {

        return banks

        // return emptyList()
    }

    override fun getBank(accountNumber: String): Bank {
        return banks.first {  it.accountNumber == accountNumber }
    }

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber}) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }
        banks.add(bank)
        return bank
    }
}