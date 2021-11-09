package com.example.shopping

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingApplicationTests @Autowired constructor(
    val template: TestRestTemplate
) {

    @Test
    fun `test body contains product name Rice`() {
        val entity = template.getForEntity<String>("/product", )
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("Rice")
    }

    @Test
    fun `test body contains stock with price`() {
        val entity = template.getForEntity<String>("/stock")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("Price : 6.75")
    }
}
