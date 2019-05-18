package development.dreamcatcher.newslightapp.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import development.dreamcatcher.newslightapp.data.database.ArticlesDatabaseInteractor
import development.dreamcatcher.newslightapp.data.network.ArticlesNetworkInteractor
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class ArticlesRepository @Inject constructor(private val articlesNetworkInteractor:  ArticlesNetworkInteractor,
                                             private val databaseInteractor: ArticlesDatabaseInteractor) {

    fun getSingleSavedArticleById(id: String): LiveData<ArticleEntity>? {
        return databaseInteractor.getSingleSavedArticleById(id)
    }

    fun getAllArticles(): LiveData<List<ArticleEntity>>? {
        updateDataFromBackEnd()
        return databaseInteractor.getAllArticles()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return articlesNetworkInteractor.networkError
    }

    @SuppressLint("CheckResult")
    private fun updateDataFromBackEnd() {
        articlesNetworkInteractor.getAllArticles().subscribe {
            if (it.isSuccess && it.getOrDefault(null)?.size!! > 0) {

                // Clear database not to store outdated articles
                databaseInteractor.clearDatabase()

                // Save freshly fetched articles
                it.getOrNull()?.forEach {
                    databaseInteractor.addNewArticle(it)
                }
            }
        }
    }
}