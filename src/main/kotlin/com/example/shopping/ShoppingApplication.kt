package com.example.shopping

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ShoppingConfig::class)
class ShoppingApplication

fun main(args: Array<String>) {
    runApplication<ShoppingApplication>(*args)
}