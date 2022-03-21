package com.example.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDateTime

@DataJpaTest
internal class RepositoriesTests @Autowired constructor(
    val productRepo: ProductRepo,
    val stockRepo: StockRepo,
    val entityManager: TestEntityManager
) {
    @Test
    fun `test all products`() {
        val rice = Product(name = "Rice")
        val wheat = Product(name = "Wheat")
        entityManager.persist(rice)
        entityManager.persist(wheat)
        entityManager.flush()
        val products = productRepo.findAll()
        assertThat(products).hasSize(2)
        assertThat(products.any { it.name == "Rice" }).isEqualTo(true)
        assertThat(products.none { it.name == "Gold" }).isEqualTo(true)
    }

    @Test
    fun `test all stock`() {
        val rice = Product(name = "Rice")
        entityManager.persist(rice)
        entityManager.flush()
        val riceStock1 = Stock(
            product = rice,
            quantity = 100.25f,
            price = 6.75,
            expiryDate = LocalDateTime.of(2021, 12, 31, 12, 30)
        )
        entityManager.persist(riceStock1)
        val riceStock2 = Stock(
            product = rice,
            quantity = 50.25f,
            price = 8.50,
            expiryDate = LocalDateTime.of(2022, 6, 30, 14, 30)
        )
        entityManager.persist(riceStock2)
        entityManager.flush()

        val stocks = stockRepo.findAll()
        assertThat(stocks).hasSize(2)
        assertThat(stocks.any { it.price == 6.75 }).isEqualTo(true)
        assertThat(stocks.none {
            it.expiryDate <
                    LocalDateTime.of(2021, 11, 1, 1, 1)
        }).isEqualTo(true)
    }
}