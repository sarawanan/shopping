package com.example.shopping

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.util.*

@WebMvcTest
internal class HttpControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockBean
    lateinit var productRepo: ProductRepo

    @MockBean
    lateinit var stockRepo: StockRepo

    @Test
    fun `test get all products`() {
        val rice = Product(name = "Rice")
        val wheat = Product(name = "Wheat")
        Mockito.`when`(productRepo.findAll()).thenReturn(listOf(rice, wheat))
        mockMvc.perform(
            get("/api/product").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.[0].name").value("Rice"))
            .andReturn()
    }

    @Test
    fun `test create product`() {
        val sugar = Product(name = "Sugar")
        val dto = sugar.toDto()
        Mockito.`when`(productRepo.save(Mockito.any())).thenReturn(sugar)
        val json = jacksonObjectMapper().registerModule(JavaTimeModule()).writeValueAsString(dto)
        mockMvc.perform(
            post("/api/product")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Sugar"))
            .andReturn()
    }

    @Test
    fun `test update product`() {
        val sugar = Product(id = 1, name = "Sugar")
        val dto = ProductDTO(id = 1, name = "Salt")
        Mockito.`when`(productRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(sugar))
        Mockito.`when`(productRepo.save(Mockito.any())).thenReturn(sugar)
        val json = jacksonObjectMapper().registerModule(JavaTimeModule()).writeValueAsString(dto)
        mockMvc.perform(
            put("/api/product")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Salt"))
            .andReturn()
    }

    @Test
    fun `get all stock`() {
        val rice = Product(name = "Rice")
        val riceStock = Stock(
            product = rice,
            quantity = 100.25f,
            price = 6.75,
            expiryDate = LocalDateTime.of(2021, 12, 31, 12, 30)
        )
        Mockito.`when`(stockRepo.findAll()).thenReturn(listOf(riceStock))
        mockMvc.perform(get("/api/stock").accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.[0].price").value(6.75))
            .andReturn()
    }
}