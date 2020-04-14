package ru.skillbranch.skillarticles.ui.custom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.annotation.VisibleForTesting
import com.bumptech.glide.Glide
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.data.ArticleItemData
import ru.skillbranch.skillarticles.extensions.attrValue
import ru.skillbranch.skillarticles.extensions.dpToIntPx
import ru.skillbranch.skillarticles.extensions.format

class ArticleItemView(
    context: Context
) : ViewGroup(context) {

    //views
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var tv_date: TextView? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var tv_author: TextView? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var tv_title: TextView? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val iv_poster: ImageView

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val iv_category: ImageView

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var tv_description: TextView? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val iv_likes: ImageView

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var tv_likes_count: TextView? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val iv_comments: ImageView

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var tv_comments_count: TextView? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var tv_read_duration: TextView? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val iv_bookmarks: ImageView

    private val likeDrawable = context.resources.getDrawable(R.drawable.ic_favorite_black_24dp, context.theme)
    private val commentsDrawable = context.resources.getDrawable(R.drawable.ic_insert_comment_black_24dp, context.theme)
    private val bookmarkDrawable = context.resources.getDrawable(R.drawable.bookmark_states)

    @Px
    private val posterSize: Int = context.dpToIntPx(64)
    @Px
    private val categorySize: Int = context.dpToIntPx(40)

    @ColorInt
    private val colorGray: Int = context.getColor(R.color.color_gray)
    @ColorInt
    private val colorPrimary: Int = context.attrValue(R.attr.colorPrimary)

    private val textMiniSize: Float = 12f
    private val textStandartSize: Float = 14f
    private val textMaxSize: Float = 18f

    init {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        iv_poster = ImageView(context).apply {

        }
        addView(iv_poster)
        iv_category = ImageView(context)
        addView(iv_category)
        iv_likes = ImageView(context).apply {
            background = likeDrawable
        }
        addView(iv_likes)
        iv_comments = ImageView(context).apply {
            background = commentsDrawable
        }
        addView(iv_comments)
        iv_bookmarks = ImageView(context).apply {
            background = bookmarkDrawable
        }
        addView(iv_bookmarks)


        tv_date = TextView(context).apply {
            setTextColor(colorGray)
            textSize = textMiniSize
        }
        addView(tv_date)

        tv_author = TextView(context).apply {
            setTextColor(colorPrimary)
            textSize = textMiniSize
        }
        addView(tv_author)

        tv_title = TextView(context).apply {
            setTextColor(colorPrimary)
            textSize = textMaxSize
        }
        addView(tv_title)

        tv_description = TextView(context).apply {
            setTextColor(colorGray)
            textSize = textStandartSize
        }
        addView(tv_description)

        tv_likes_count = TextView(context).apply {
            setTextColor(colorGray)
            textSize = textMiniSize
        }
        addView(tv_likes_count)

        tv_comments_count = TextView(context).apply {
            setTextColor(colorGray)
            textSize = textMiniSize
        }
        addView(tv_comments_count)

        tv_read_duration = TextView(context).apply {
            setTextColor(colorGray)
            textSize = textMiniSize
        }
        addView(tv_read_duration)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var usedHeight = 0
        val width = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)

        iv_poster.measure(posterSize, posterSize)
        iv_category.measure(categorySize, categorySize)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    fun bind(data : ArticleItemData) {
        tv_date?.text = data.date.format()
        tv_author?.text = data.author
        tv_title?.text = data.title
        tv_description?.text = data.description
        tv_likes_count?.text = data.likeCount.toString()
        tv_comments_count?.text = data.commentCount.toString()
        tv_read_duration?.text = data.readDuration.toString()

        Glide.with(context)
            .load(data.authorAvatar)
            .centerCrop()
            .into(iv_poster)

        Glide.with(context)
            .load(data.categoryIcon)
            .centerCrop()
            .into(iv_poster)
    }
}