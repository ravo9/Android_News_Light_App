package development.dreamcatcher.newslightapp.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import development.dreamcatcher.newslightapp.features.detailedArticle.DetailedArticleViewModel
import development.dreamcatcher.newslightapp.features.feed.FeedViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    internal abstract fun bindFeedViewModel(feedViewModel: FeedViewModel)
            : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailedArticleViewModel::class)
    internal abstract fun bindDetailedArticleViewModel(detailedArticleViewModel: DetailedArticleViewModel)
            : ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory)
            : ViewModelProvider.Factory
}