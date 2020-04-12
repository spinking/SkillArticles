package ru.skillbranch.skillarticles.ui.articles

import androidx.fragment.app.viewModels
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.data.ArticleItemData
import ru.skillbranch.skillarticles.ui.base.BaseFragment
import ru.skillbranch.skillarticles.ui.base.Binding
import ru.skillbranch.skillarticles.ui.base.BottombarBuilder
import ru.skillbranch.skillarticles.ui.base.ToolbarBuilder
import ru.skillbranch.skillarticles.ui.delegates.RenderProp
import ru.skillbranch.skillarticles.viewmodels.articles.ArticlesViewModel
import ru.skillbranch.skillarticles.viewmodels.base.IViewModelState

class ArticlesFragment : BaseFragment<ArticlesViewModel>() {
    override val binding: ArticlesBinding by lazy { ArticlesBinding() }
    override val viewModel: ArticlesViewModel by viewModels()
    override val layout: Int = R.layout.fragment_articles

    private val articlesAdapter = ArticlesAdapter{ item ->
        //val action = ArticlesFragment
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)?
        get() = super.prepareToolbar
    override val prepareBottombar: (BottombarBuilder.() -> Unit)?
        get() = super.prepareBottombar

    override fun setupViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ArticlesBinding : Binding() {
        private var articles: List<ArticleItemData> by RenderProp(emptyList()) {
            articlesAdapter.submitList(it)
        }

        override fun bind(data: IViewModelState) {

        }
    }
}