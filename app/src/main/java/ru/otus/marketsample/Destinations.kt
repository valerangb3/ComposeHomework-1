package ru.otus.marketsample

import kotlinx.serialization.Serializable

@Serializable
object ProductList

@Serializable
data class ProductDetail(
    val productId: String
)

@Serializable
object Promo