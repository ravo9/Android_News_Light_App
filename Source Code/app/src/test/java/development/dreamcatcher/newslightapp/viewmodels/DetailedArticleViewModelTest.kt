package development.dreamcatcher.newslightapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import development.dreamcatcher.newslightapp.data.repositories.ArticlesRepository
import development.dreamcatcher.newslightapp.features.detailedArticle.DetailedArticleViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailedArticleViewModelTest {

    private var viewModel: DetailedArticleViewModel? = null
    private var fakeArticleEntity: ArticleEntity? = null

    @Mock
    private val articlesRepository: ArticlesRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        viewModel = DetailedArticleViewModel(articlesRepository!!)

        // Prepare fake data
        val contentId = "fake/Article/Id"
        val title = "Fake Article Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake Article Entity (DB object)
        fakeArticleEntity = ArticleEntity(contentId, title, summary, contentUrl, thumbnailUrl)
    }

    @Test
    fun fetchArticleByFeedViewModel() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<ArticleEntity>()
        articleEntityLiveData.setValue(fakeArticleEntity);

        // Prepare fake article id
        val fakeArticleId = "fake/article/id"

        // Set testing conditions
        Mockito.`when`(articlesRepository?.getSingleSavedArticleById(fakeArticleId)).thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticles = viewModel?.getSingleSavedArticleById(fakeArticleId)

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticles);
    }
}