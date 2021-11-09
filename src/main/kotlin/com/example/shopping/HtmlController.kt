package com.example.shopping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController @Autowired constructor(
    val productRepo: ProductRepo,
    val stockRepo: StockRepo,
    val config: ShoppingConfig
) {
    @GetMapping("/product")
    fun getProduct(model: Model): String {
        model["title"] = config.productTitle
        model["products"] = productRepo.findAll().mapNotNull { it.render() }
        return "products"
    }

    @GetMapping("/stock")
    fun getStock(model: Model): String {
        model["title"] = config.stockTitle
        model["stocks"] = stockRepo.findAll().mapNotNull { it.render() }
        return "stocks"
    }
}

private fun Product.render() = RenderProduct(id, name)

data class RenderProduct(val id: Long?, val name: String)

private fun Stock.render() = RenderStock(
    id, product.name, price, quantity, expiryDate.format(), createdDate.format()
)

data class RenderStock(
    val id: Long?,
    val productName: String,
    val price: Double,
    val quantity: Float,
    val expiryDate: String,
    val createdDate: String
)