package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.*
import androidx.navigation.NavDestination
import com.google.android.material.bottomnavigation.BottomNavigationView


fun View.setMarginOptionally(top: Int = 0, right: Int = 0, bottom: Int = 0, left: Int = 0) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.leftMargin = left
    params.rightMargin = right
    params.topMargin = top
    params.bottomMargin = bottom
    layoutParams = params
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