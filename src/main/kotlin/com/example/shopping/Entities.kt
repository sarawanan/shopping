package com.example.shopping

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Product(
    @Id @GeneratedValue val id: Long? = null,
    var name: String,
    val createdDate: LocalDateTime = LocalDateTime.now()
) {
    fun toDto() = ProductDTO(
        id = this.id,
        name = this.name
    )

    companion object {
        fun fromDto(dto: ProductDTO) = Product(
            name = dto.name
        )
    }
}

@Entity
class Stock(
    @Id @GeneratedValue val id: Long? = null,
    @ManyToOne val product: Product,
    val quantity: Float,
    val price: Double,
    val expiryDate: LocalDateTime,
    val createdDate: LocalDateTime = LocalDateTime.now()
) {
    fun toDto() = StockDTO(
        id = this.id,
        productName = this.product.name,
        quantity = this.quantity,
        price = this.price,
        expiryDate = this.expiryDate.format()
    )
    companion object {
        fun fromDto(dto: StockDTO) = Stock(
            product = Product(name = dto.productName),
            quantity = dto.quantity,
            price = dto.price,
            expiryDate = LocalDateTime.now() // TODO: Need to change this to take from dto and construct
        )
    }
}