package development.dreamcatcher.newslightapp.injection

import dagger.Component
import development.dreamcatcher.newslightapp.data.database.ArticlesDatabaseInteractor
import development.dreamcatcher.newslightapp.data.network.ArticlesNetworkInteractor
import development.dreamcatcher.newslightapp.features.detailedArticle.DetailedArticleFragment
import development.dreamcatcher.newslightapp.features.detailedArticle.DetailedArticleViewModel
import development.dreamcatcher.newslightapp.features.feed.FeedActivity
import development.dreamcatcher.newslightapp.features.feed.FeedViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, FeedModule::class, ViewModelModule::class))
interface FeedComponent {
    fun inject(feedActivity: FeedActivity)
    fun inject(detailedArticleFragment: DetailedArticleFragment)
    fun inject(feedViewModel: FeedViewModel)
    fun inject(detailedArticleViewModel: DetailedArticleViewModel)
    fun inject(articlesNetworkInteractor: ArticlesNetworkInteractor)
    fun inject(articlesDatabaseInteractor: ArticlesDatabaseInteractor)
}