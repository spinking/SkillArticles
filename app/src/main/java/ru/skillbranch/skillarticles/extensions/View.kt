package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.*
import androidx.navigation.NavDestination
import com.google.android.material.bottomnavigation.BottomNavigationView


fun View.setMarginOptionally(left:Int = marginStart, top : Int = marginTop, right : Int = marginEnd, bottom : Int = marginBottom) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.apply {
        leftMargin = left
        topMargin = top
        rightMargin = right
        bottomMargin = bottom
    }
    requestLayout()
}

fun View.setPaddingOptionally(left: Int = paddingStart, top: Int = paddingTop, right: Int = paddingEnd, bottom: Int = paddingBottom) {
    setPadding(left, top, right, bottom)
}

private fun matchDestination(destination: NavDestination, @IdRes destId: Int) : Boolean{
    var currentDestination: NavDestination? = destination
    while (currentDestination!!.id != destId && currentDestination.parent != null) {
        currentDestination = currentDestination.parent
    }
    return currentDestination.id == destId
}