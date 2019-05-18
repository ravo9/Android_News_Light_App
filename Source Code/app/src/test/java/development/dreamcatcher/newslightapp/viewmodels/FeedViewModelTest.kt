package development.dreamcatcher.newslightapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import development.dreamcatcher.newslightapp.data.repositories.ArticlesRepository
import development.dreamcatcher.newslightapp.features.feed.FeedViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class FeedViewModelTest {

    private var viewModel: FeedViewModel? = null
    private var fakeArticleEntity: ArticleEntity? = null
    private var fakeArticleEntitiesList = ArrayList<ArticleEntity>()

    @Mock
    private val articlesRepository: ArticlesRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        viewModel = FeedViewModel(articlesRepository!!)

        // Prepare fake data
        val contentId = "fake/Article/Id"
        val title = "Fake Article Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Article Entity (DB object)
        fakeArticleEntity = ArticleEntity(contentId, title, summary, contentUrl, thumbnailUrl)

        // Prepare fake Articles Entities List
        fakeArticleEntitiesList.add(fakeArticleEntity!!)
    }

    @Test
    fun fetchAllArticlesByFeedViewModel() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<List<ArticleEntity>>()
        articleEntityLiveData.setValue(fakeArticleEntitiesList);

        // Set testing conditions
        Mockito.`when`(articlesRepository?.getAllArticles()).thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticles = viewModel?.getAllArticles()

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticles);
    }
}