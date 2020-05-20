package app.web.drjackycv.presentation.products.entity

import android.os.Parcelable
import app.web.drjackycv.domain.base.RecyclerItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeerUI(
    override val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val imageUrl: String,
    val abv: Double
) : RecyclerItem, Parcelable