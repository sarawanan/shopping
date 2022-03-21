package com.example.shopping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class DbInitializer @Autowired constructor(
    val productRepo: ProductRepo,
    val stockRepo: StockRepo
) {
    @Bean
    fun initialize() = ApplicationRunner {
        val rice = Product(name = "Rice")
        val wheat = Product(name = "Wheat")

        val riceStock1 = Stock(
            product = rice,
            quantity = 100.25f,
            price = 6.75,
            expiryDate = LocalDateTime.of(2021, 12, 31, 12, 30)
        )
        val riceStock2 = Stock(
            product = rice,
            quantity = 50.25f,
            price = 8.50,
            expiryDate = LocalDateTime.of(2022, 6, 30, 14, 30)
        )
        val wheatStock1 = Stock(
            product = wheat,
            quantity = 200.00f,
            price = 4.50,
            expiryDate = LocalDateTime.of(2021, 12, 31, 12, 30)
        )
        productRepo.save(rice)
        productRepo.save(wheat)
        stockRepo.save(riceStock1)
        stockRepo.save(riceStock2)
        stockRepo.save(wheatStock1)
    }
}