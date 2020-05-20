package app.web.drjackycv.data.products.entity

sealed class Beer {

    open val type: Int = 0

    data class C(override val type: Int) : Beer() {
        override fun toString(): String = "C"
    }

    data class B(override val type: Int) : Beer() {
        override fun toString(): String = "B"
    }

}