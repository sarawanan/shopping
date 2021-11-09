package com.example.shopping

import org.springframework.data.repository.CrudRepository

interface ProductRepo : CrudRepository<Product, Long>

interface StockRepo : CrudRepository<Stock, Long>