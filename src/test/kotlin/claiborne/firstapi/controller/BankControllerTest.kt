package claiborne.firstapi.controller

import claiborne.firstapi.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc // needed to be able to create MockMvc bean
internal class BankControllerTest @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper
) {

    /* Instead autowire in constructor directly above ^

    @Autowired // give me a bean of this type
    lateinit var mockMvc: MockMvc // mock http request, no actually http request
    @Autowired
    lateinit var objectMapper: ObjectMapper  */

    val endpoint = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS) // default is PER_METHOD
    inner class GetBanks {

        @Test
        fun `should return all banks`() {
            mockMvc.get(endpoint)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON)}
                    jsonPath("$[0].accountNumber") { value("1234")}
                    jsonPath("$[1].accountNumber") { value("1235")}
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS) // default is PER_METHOD
    inner class GetBank {

        @Test
        fun `should return bank by id`() {
            val accountNumber = 1234
            mockMvc.get("$endpoint/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("1234") }
                }
        }

        @Test
        fun `should return 'not found' if account number doesn't exist`() {
            val accountNumber = 9999999
            mockMvc.get("$endpoint/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS) // default is PER_METHOD
    inner class CreateBank {

        @Test
        fun `should create a new bank`() {
            // given
            val newBank = Bank("abc", 31.43, 2)

            // when
            val performPost = mockMvc.post(endpoint) {
                contentType = MediaType.APPLICATION_JSON
                // serialize to JSON object
                content = objectMapper.writeValueAsString(newBank)
            }

            // then
            performPost.andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.accountNumber") { value("abc") }
            }
        }

        @Test
        fun `should return 'bad request' when account number already exists`() {

            // given
            val newBank = Bank("xyz", 31.43, 2)

            // when
            mockMvc.post(endpoint) {
                contentType = MediaType.APPLICATION_JSON
                // serialize to JSON object
                content = objectMapper.writeValueAsString(newBank)
            }

            val performPost = mockMvc.post(endpoint) {
                contentType = MediaType.APPLICATION_JSON
                // serialize to JSON object
                content = objectMapper.writeValueAsString(newBank)
            }

            // then
            performPost.andExpect {
                status { isBadRequest() }
            }
        }
    }
}