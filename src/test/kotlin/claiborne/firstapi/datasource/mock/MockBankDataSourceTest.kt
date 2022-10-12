package claiborne.firstapi.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {

    // no special springboot functionality, just plain POJO object
    private val mockDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`() {
        // given

        // when
        val banks = mockDataSource.getBanks()

        // then
        assertThat(banks).isNotEmpty
        assertThat(banks.size).isGreaterThanOrEqualTo(1)
    }

    @Test
    fun `should return data`() {
        // given

        // when
        val banks = mockDataSource.getBanks()

        // then
        assertThat(banks).allMatch() { it.accountNumber.isNotBlank() }
    }
}