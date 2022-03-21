package com.example.shopping

data class ProductDTO(
    val id: Long? = null,
    val name: String
)

data class StockDTO(
    val id: Long?,
    val productName: String,
    val quantity: Float,
    val price: Double,
    val expiryDate: String
)