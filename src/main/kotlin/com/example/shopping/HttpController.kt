package com.example.shopping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class HttpController @Autowired constructor(
    val productRepo: ProductRepo,
    val stockRepo: StockRepo,
) {
    @GetMapping("/product")
    fun getProduct(): List<ProductDTO> = productRepo.findAll().mapNotNull { it.toDto() }

    @GetMapping("/stock")
    fun getStock(): List<StockDTO> = stockRepo.findAll().mapNotNull { it.toDto() }

    @PostMapping("/product")
    fun createProduct(@RequestBody dto: ProductDTO): ProductDTO {
        return productRepo.save(Product.fromDto(dto)).toDto()
    }

    @PutMapping("/product")
    fun updateProduct(@RequestBody dto: ProductDTO): ProductDTO {
        val product = productRepo.findById(dto.id!!).get()
        product.name = dto.name
        return productRepo.save(product).toDto()
    }
}