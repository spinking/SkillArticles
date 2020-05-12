package ru.skillbranch.skillarticles.listeners

import ru.skillbranch.skillarticles.data.models.ArticleItemData

interface OnArticleListener {
    fun bookmarksClick(item: ArticleItemData, position: Int)
}