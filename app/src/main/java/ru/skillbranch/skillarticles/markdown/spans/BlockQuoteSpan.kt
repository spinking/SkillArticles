package ru.skillbranch.skillarticles.markdown.spans

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan
import androidx.annotation.ColorInt
import androidx.annotation.Px

class BlockQuoteSpan(
    @Px
    private val gapWidth: Float,
    @Px
    private val quoteWidth: Float,
    @ColorInt
    private val lineColor: Int
) : LeadingMarginSpan {

    override fun drawLeadingMargin(
        canvas: Canvas,
        paint: Paint,
        currentMarginLocation: Int,
        paragraphDerection: Int,
        lineTop: Int,
        lineBaseline: Int,
        lineBottom: Int,
        text: CharSequence?,
        LineStart: Int,
        lineEnd: Int,
        isFirstLine: Boolean,
        layout: Layout?
    ) {
        paint.withCustomColor {
            canvas.drawLine(
                quoteWidth / 2f,
                lineTop.toFloat(),
                quoteWidth / 2f,
                lineBottom.toFloat(),
                paint
            )
        }
    }

    override fun getLeadingMargin(first: Boolean): Int {
        return (quoteWidth + gapWidth).toInt()
    }

    private inline fun Paint.withCustomColor(block: () -> Unit) {
        val oldColor = color
        val oldStyle = style
        val oldWidth = strokeWidth

        color = lineColor
        style = Paint.Style.STROKE
        strokeWidth = quoteWidth

        block()

        color = oldColor
        style = oldStyle
        strokeWidth = oldWidth
    }
}