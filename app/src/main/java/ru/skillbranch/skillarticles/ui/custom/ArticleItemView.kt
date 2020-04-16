package ru.skillbranch.skillarticles.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.data.ArticleItemData
import ru.skillbranch.skillarticles.extensions.attrValue
import ru.skillbranch.skillarticles.extensions.dpToIntPx
import ru.skillbranch.skillarticles.extensions.format
import ru.skillbranch.skillarticles.extensions.setPaddingOptionally

@SuppressLint("ViewConstructor")
class ArticleItemView(
    context: Context,
    attributeSet: AttributeSet
) : ViewGroup(context, null, 0) {

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

    @Px
    private val posterSize: Int = context.dpToIntPx(64)

    @Px
    private val categorySize: Int = context.dpToIntPx(40)
    @Px
    private val iconSize: Int = context.dpToIntPx(16)
    @Px
    private val standartPadding: Int = context.dpToIntPx(16)

    @Px
    private val cornerRadius: Int = context.dpToIntPx(8)
    @Px
    private val miniMargin: Int = context.dpToIntPx(8)
    @ColorInt
    private val colorGray: Int = context.getColor(R.color.color_gray)

    @ColorInt
    private val colorPrimary: Int = context.attrValue(R.attr.colorPrimary)
    private val textMiniSize: Float = 12f

    private val textStandartSize: Float = 14f
    private val textMaxSize: Float = 18f

    private val likeDrawable = context.resources.getDrawable(R.drawable.ic_favorite_black_24dp, context.theme).apply { setTint(colorGray) }
    private val commentsDrawable = context.resources.getDrawable(R.drawable.ic_insert_comment_black_24dp, context.theme).apply { setTint(colorGray) }
    private val bookmarkDrawable = context.resources.getDrawable(R.drawable.bookmark_states).apply {  setTint(colorGray) }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
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
            setPaddingOptionally(right = standartPadding + posterSize + categorySize)
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

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var usedHeight = 0
        val width = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)

        val ms = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST)

        tv_date?.measure(ms, heightMeasureSpec)
        usedHeight += tv_date?.measuredHeight ?: 0

        tv_author?.measure(ms, heightMeasureSpec)

        iv_poster.measure(posterSize, posterSize)
        usedHeight += posterSize

        iv_category.measure(categorySize, categorySize)
        usedHeight += categorySize + context.dpToIntPx(8)

        tv_title?.measure(ms, heightMeasureSpec)
        //usedHeight += tv_description?.measuredHeight ?: 0

        iv_likes.measure(iconSize, iconSize)
        usedHeight += iconSize

        tv_comments_count?.measure(ms, heightMeasureSpec)
        tv_likes_count?.measure(ms, heightMeasureSpec)
        tv_description?.measure(ms, heightMeasureSpec)
        usedHeight += tv_description?.measuredHeight ?: 0

        tv_read_duration?.measure(ms, heightMeasureSpec)

        iv_bookmarks.measure(iconSize, iconSize)
        iv_comments.measure(iconSize, iconSize)

        setMeasuredDimension(width, usedHeight)
    }



    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var usedHeight = 0
        val bodyWidth = r - 1- paddingLeft - paddingRight
        val left = paddingLeft
        val right = paddingLeft + bodyWidth

        tv_date?.layout(
            left,
            usedHeight,
            tv_date?.measuredWidth ?: 0,
            usedHeight + (tv_date?.measuredHeight ?: 0)
        )

        usedHeight + (tv_date?.measuredHeight ?: 0)

        tv_author?.layout(
            left + (tv_date?.width ?: 0) + context.dpToIntPx(16),
            usedHeight,
            right - (tv_date?.measuredWidth ?: 0),
            usedHeight + (tv_author?.measuredHeight ?: 0)
        )

        usedHeight += tv_date?.measuredHeight ?: 0

        iv_poster.layout(
            right - posterSize - standartPadding,
            usedHeight,
            right - standartPadding,
            usedHeight + posterSize
        )

        usedHeight += posterSize

        iv_category.layout(
            right - posterSize - standartPadding - categorySize,
            usedHeight - categorySize / 2,
            right - standartPadding - categorySize,
            usedHeight + categorySize
        )

        usedHeight += categorySize

        tv_title?.layout(
            left,
            tv_author?.measuredHeight ?: 0,
            right,
            (tv_author?.measuredHeight ?: 0) + posterSize + categorySize
        )


        tv_description?.layout(
            left,
            usedHeight,
            right,
            usedHeight + (tv_description?.measuredHeight ?: 0)
        )

        usedHeight += tv_description?.measuredHeight ?: 0

        iv_likes.layout(
            left,
            usedHeight,
            left + iconSize,
            usedHeight + iconSize
        )

        var usedWidth = left + iconSize

        usedWidth += miniMargin
        tv_likes_count?.layout(
            usedWidth,
            usedHeight,
            usedWidth + (tv_likes_count?.measuredWidth ?: 0),
            usedHeight + (tv_likes_count?.measuredHeight ?: 0)
        )

        usedWidth += standartPadding + standartPadding

        iv_comments.layout(
            usedWidth,
            usedHeight,
            usedWidth + iconSize,
            usedHeight + iconSize
        )
        usedWidth += iconSize + miniMargin

        tv_comments_count?.layout(
            usedWidth,
            usedHeight,
            usedWidth + (tv_comments_count?.measuredWidth ?: 0),
            usedHeight + (tv_comments_count?.measuredHeight ?: 0)
        )

        usedWidth += (tv_comments_count?.measuredWidth ?: 0) + standartPadding

        tv_read_duration?.layout(
            usedWidth,
            usedHeight,
            usedWidth + (tv_read_duration?.measuredWidth ?: 0),
            (tv_read_duration?.measuredHeight ?: 0) + usedHeight
        )

        iv_bookmarks.layout(
            right - standartPadding - iconSize,
            usedHeight,
            right - standartPadding,
            usedHeight + iconSize
        )

        usedHeight += iconSize
        usedHeight +=  (tv_likes_count?.measuredHeight ?: 0)
    }

    fun bind(data : ArticleItemData) {
        tv_date?.text = data.date.format()
        tv_author?.text = data.author
        tv_title?.text = data.title
        tv_description?.text = data.description
        tv_likes_count?.text = data.likeCount.toString()
        tv_comments_count?.text = data.commentCount.toString()
        tv_read_duration?.text = "${data.readDuration} min read"

        Glide.with(context)
            .load(data.poster)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .override(posterSize)
            .into(iv_poster)

        Glide.with(context)
            .load(data.categoryIcon)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .override(categorySize)
            .into(iv_category)
    }
}