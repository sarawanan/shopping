package com.example.shopping

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("shopping")
@ConstructorBinding
data class ShoppingConfig(val productTitle: String, val stockTitle: String)