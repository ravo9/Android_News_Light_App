package development.dreamcatcher.newslightapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import development.dreamcatcher.newslightapp.data.repositories.ArticlesRepository
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val articlesRepository: ArticlesRepository)
    : ViewModel(), LifecycleObserver {

    fun getAllArticles(): LiveData<List<ArticleEntity>>? {
        return articlesRepository.getAllArticles()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return articlesRepository.getNetworkError()
    }
}