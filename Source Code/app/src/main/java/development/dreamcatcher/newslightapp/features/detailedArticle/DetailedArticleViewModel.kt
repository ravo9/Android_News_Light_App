package development.dreamcatcher.newslightapp.features.detailedArticle

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import development.dreamcatcher.newslightapp.data.repositories.ArticlesRepository
import javax.inject.Inject

class DetailedArticleViewModel @Inject constructor(private val articlesRepository: ArticlesRepository)
    : ViewModel(), LifecycleObserver {

    fun getSingleSavedArticleById(articleId: String): LiveData<ArticleEntity>? {
        return articlesRepository.getSingleSavedArticleById(articleId)
    }
}