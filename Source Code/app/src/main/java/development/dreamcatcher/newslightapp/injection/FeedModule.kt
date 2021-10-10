package development.dreamcatcher.newslightapp.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import development.dreamcatcher.newslightapp.data.database.ArticlesDatabase
import development.dreamcatcher.newslightapp.data.database.ArticlesDatabaseInteractor
import development.dreamcatcher.newslightapp.data.network.ApiClient
import development.dreamcatcher.newslightapp.data.network.ArticlesNetworkInteractor
import development.dreamcatcher.newslightapp.data.network.NetworkAdapter
import development.dreamcatcher.newslightapp.data.repositories.ArticlesRepository
import javax.inject.Singleton

@Module
class FeedModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Context): ArticlesDatabase {
        return Room.databaseBuilder(application, ArticlesDatabase::class.java, "articles_database").build()
    }

    @Provides
    @Singleton
    fun providesArticlesDatabaseInteractor(articlesDatabase: ArticlesDatabase): ArticlesDatabaseInteractor {
        return ArticlesDatabaseInteractor(articlesDatabase)
    }

    @Provides
    @Singleton
    fun providesArticlesNetworkInteractor(apiClient: ApiClient): ArticlesNetworkInteractor {
        return ArticlesNetworkInteractor(apiClient)
    }

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient {
        return NetworkAdapter.apiClient()
    }

    @Provides
    @Singleton
    fun providesArticlesRepository(articlesNetworkInteractor: ArticlesNetworkInteractor,
                                   articlesDatabaseInteractor: ArticlesDatabaseInteractor): ArticlesRepository {
        return ArticlesRepository(articlesNetworkInteractor, articlesDatabaseInteractor)
    }
}