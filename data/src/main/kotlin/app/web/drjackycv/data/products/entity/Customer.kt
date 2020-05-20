package app.web.drjackycv.data.products.entity

data class Customer(
    val preferences: Set<Beer>,
    var happy: Boolean = false
)