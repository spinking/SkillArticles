package ru.skillbranch.skillarticles.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.data.ArticleItemData
import ru.skillbranch.skillarticles.extensions.*

@SuppressLint("ViewConstructor")
class ArticleItemView(
    context: Context
) : ViewGroup(context) {

    constructor(context: Context, attributeSet: AttributeSet) : this(context) {

    }
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
    private val posterWithCategorySize: Int = context.dpToIntPx(84)
    @Px
    private val standartMargin: Int = context.dpToIntPx(16)

    @Px
    private val cornerRadius: Int = context.dpToIntPx(8)
    @Px
    private val miniMargin: Int = context.dpToIntPx(8)
    @ColorInt
    private val colorGray: Int = context.getColor(R.color.color_gray)

    @ColorInt
    private val colorPrimary: Int = context.attrValue(R.attr.colorPrimary)

    private var isTextBigger = false

    private val textMiniSize: Float = 12f
    private val textStandartSize: Float = 14.2f
    private val textMaxSize: Float = 18.2f

    private val likeDrawable = context.resources.getDrawable(R.drawable.ic_favorite_black_24dp, context.theme).apply { setTint(colorGray) }
    private val commentsDrawable = context.resources.getDrawable(R.drawable.ic_insert_comment_black_24dp, context.theme).apply { setTint(colorGray) }
    private val bookmarkDrawable = context.resources.getDrawable(R.drawable.bookmark_states, context.theme).apply {  setTint(colorGray) }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        this.setPadding(standartMargin)
        iv_poster = ImageView(context).apply {
            id = R.id.iv_poster
        }
        addView(iv_poster)
        iv_category = ImageView(context).apply {
        }
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
            id = R.id.tv_title
            setTypeface(this.typeface, Typeface.BOLD)
            setTextColor(colorPrimary)
            textSize = textMaxSize
            gravity = Gravity.CENTER_VERTICAL
        }
        addView(tv_title)

        tv_description = TextView(context).apply {
            id = R.id.tv_description
            compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.color_gray))
            textSize = textStandartSize
            //setPaddingOptionally(right = paddingRight)
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
            id = R.id.tv_read_duration
            setTextColor(colorGray)
            textSize = textMiniSize
        }
        addView(tv_read_duration)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var usedHeight = paddingTop
        val width = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val ms = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST)
        val msTitle = MeasureSpec.makeMeasureSpec(width - posterSize - categorySize / 2 - context.dpToIntPx(8), MeasureSpec.AT_MOST)
        val msDescription = MeasureSpec.makeMeasureSpec(width - paddingLeft - paddingRight, MeasureSpec.AT_MOST)

        tv_date?.measure(ms, heightMeasureSpec)
        usedHeight += (tv_date?.measuredHeight!!) + miniMargin

        tv_author?.measure(ms, heightMeasureSpec)

        iv_poster.measure(ms, heightMeasureSpec)
        iv_category.measure(ms, heightMeasureSpec)
        tv_title?.measure(msTitle, heightMeasureSpec)

        if(tv_title?.measuredHeight!! > posterWithCategorySize) {
            isTextBigger = true
            usedHeight += tv_title?.measuredHeight!!
        } else {
            usedHeight += posterSize
            usedHeight += categorySize / 2
        }
        usedHeight += miniMargin


        tv_description?.measure(msDescription, heightMeasureSpec)
        usedHeight += (tv_description?.measuredHeight!!)
        usedHeight += miniMargin

        iv_likes.measure(ms, heightMeasureSpec)
        tv_likes_count?.measure(ms, heightMeasureSpec)
        tv_comments_count?.measure(ms, heightMeasureSpec)
        tv_read_duration?.measure(ms, heightMeasureSpec)
        iv_comments.measure(iconSize, iconSize)
        iv_bookmarks.measure(iconSize, iconSize)

        usedHeight += iconSize
        usedHeight += standartMargin

        setMeasuredDimension(width, usedHeight)
    }



    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var usedHeight = paddingTop
        var usedWidth = 0
        val bodyWidth = r
        val left = paddingLeft
        val right = paddingLeft + bodyWidth

        usedWidth += tv_date?.measuredWidth!!

        tv_date?.layout(
            left,
            usedHeight,
            left + usedWidth,
            usedHeight + (tv_date?.measuredHeight!!)
        )

        tv_author?.layout(
            usedWidth + left + standartMargin,
            usedHeight,
            right,
            usedHeight + (tv_author?.measuredHeight!!)
        )

        usedHeight += if(usedWidth == 0) (tv_author?.measuredHeight!!) else (tv_date?.measuredHeight!!)
        usedHeight += miniMargin

        if(isTextBigger) {
            tv_title?.layout(
                left,
                usedHeight,
                right - posterSize - categorySize / 2 - context.dpToIntPx(4),
                usedHeight + (tv_title?.measuredHeight!!)
            )

            iv_poster.layout(
                right - posterSize - paddingLeft - paddingRight,
                usedHeight + tv_title?.measuredHeight!! / 2 - posterSize / 2,
                right - paddingLeft - paddingRight,
                usedHeight + tv_title?.measuredHeight!! / 2 + posterSize / 2
            )

            iv_category.layout(
                right - posterSize - categorySize / 2  - paddingLeft - paddingRight,
                usedHeight + tv_title?.measuredHeight!! / 2 + posterSize / 2 - categorySize / 2,
                right - posterSize + categorySize / 2  - paddingLeft - paddingRight,
                usedHeight + tv_title?.measuredHeight!! / 2 + posterSize / 2 + categorySize / 2
            )

            usedHeight += (tv_title?.measuredHeight!!)

        } else {
            iv_poster.layout(
                right - posterSize  - paddingLeft - paddingRight,
                usedHeight,
                right  - paddingLeft - paddingRight,
                usedHeight + posterSize
            )

            iv_category.layout(
                right - posterSize - categorySize / 2  - paddingLeft - paddingRight,
                usedHeight + posterSize - categorySize / 2,
                right - posterSize + categorySize / 2  - paddingLeft - paddingRight,
                usedHeight + posterSize + categorySize / 2
            )

            tv_title?.layout(
                left,
                usedHeight + posterWithCategorySize / 2 - (tv_title?.measuredHeight!!) / 2 - context.dpToIntPx(4),
                right - posterSize - categorySize / 2 - context.dpToIntPx(4) - paddingLeft - paddingRight,
                usedHeight + posterWithCategorySize / 2 + (tv_title?.measuredHeight!!) / 2 + context.dpToIntPx(4)
            )

            usedHeight += posterSize
            usedHeight += categorySize / 2
        }

        usedHeight += miniMargin

        tv_description?.layout(
            left,
            usedHeight,
            right - paddingRight - paddingLeft,
            usedHeight + (tv_description?.measuredHeight!!)
        )

        usedHeight += (tv_description?.measuredHeight!!)
        usedHeight += miniMargin

        iv_likes.layout(
            left,
            usedHeight,
            left + iconSize,
            usedHeight + iconSize
        )

        usedWidth = paddingLeft
        usedWidth += iconSize
        usedWidth += miniMargin

        tv_likes_count?.layout(
            usedWidth,
            usedHeight,
            usedWidth + (tv_likes_count?.measuredWidth!!),
            usedHeight + iconSize
        )

        usedWidth += (tv_likes_count?.measuredWidth!!)
        usedWidth += standartMargin

        iv_comments.layout(
            usedWidth,
            usedHeight,
            usedWidth + iconSize,
            usedHeight + iconSize
        )
        usedWidth += iconSize
        usedWidth += miniMargin

        tv_comments_count?.layout(
            usedWidth,
            usedHeight,
            usedWidth + (tv_comments_count?.measuredWidth!!),
            usedHeight + iconSize
        )

        usedWidth += (tv_comments_count?.measuredWidth!!)
        usedWidth += standartMargin

        tv_read_duration?.layout(
            usedWidth,
            usedHeight,
            usedWidth + (tv_read_duration?.measuredWidth!!),
            (tv_read_duration?.measuredHeight!!) + usedHeight
        )

        iv_bookmarks.layout(
            right - iconSize  - paddingLeft - paddingRight,
            usedHeight,
            right  - paddingLeft - paddingRight,
            usedHeight + iconSize
        )

        usedHeight += iconSize
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