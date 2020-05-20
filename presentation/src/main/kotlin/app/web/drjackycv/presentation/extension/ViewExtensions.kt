package app.web.drjackycv.presentation.extension

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import app.web.drjackycv.presentation.R
import app.web.drjackycv.presentation.base.util.GlideApp
import com.bumptech.glide.request.RequestOptions

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun ImageView.load(
    url: String,
    @DrawableRes placeholderRes: Int = R.drawable.ic_cloud_download
) {
    val safePlaceholderDrawable = AppCompatResources.getDrawable(context, placeholderRes)
    val requestOptions = RequestOptions().apply {
        placeholder(safePlaceholderDrawable)
        error(safePlaceholderDrawable)
    }
    val glideRequest = GlideApp
        .with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(url)

    glideRequest.into(this)
}
