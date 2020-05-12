package ru.skillbranch.skillarticles.listeners

interface OnArticleListener {
    fun bookmarksClick(itemId: String, isChecked: Boolean)
}