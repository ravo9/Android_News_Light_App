package development.dreamcatcher.newslightapp.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.newslightapp.data.network.ApiResponse
import kotlinx.coroutines.launch

// Interactor used for communication between data repository and internal database
class ArticlesDatabaseInteractor(private val articlesDatabase: ArticlesDatabase) {

    fun addNewArticle(article: ApiResponse.Article?): LiveData<Boolean> {

        val saveArticleLiveData = MutableLiveData<Boolean>()

        article?.let {
            val articleEntity = ArticleEntity(
                id = it.contentId,
                title = it.title,
                summary = it.summary,
                contentUrl = it.contentUrl,
                thumbnailUrl = it.images.mainImageThumbnail.shortUrl)
            launch {
                articlesDatabase.getArticlesDao().insertNewArticle(articleEntity)
            }
        }
        saveArticleLiveData.postValue(true)
        return saveArticleLiveData
    }

    fun getSingleSavedArticleById(id: String): LiveData<ArticleEntity>? {
        return articlesDatabase.getArticlesDao().getSingleSavedArticleById(id)
    }

    fun getAllArticles(): LiveData<List<ArticleEntity>>? {
        return articlesDatabase.getArticlesDao().getAllSavedArticles()
    }

    fun clearDatabase() {
        launch {
            articlesDatabase.getArticlesDao().clearDatabase()
        }
    }
}



