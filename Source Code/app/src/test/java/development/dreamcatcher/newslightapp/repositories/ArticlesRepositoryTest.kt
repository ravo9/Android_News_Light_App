package development.dreamcatcher.newslightapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import development.dreamcatcher.newslightapp.data.database.ArticlesDatabaseInteractor
import development.dreamcatcher.newslightapp.data.network.ApiResponse
import development.dreamcatcher.newslightapp.data.network.ArticlesNetworkInteractor
import development.dreamcatcher.newslightapp.data.repositories.ArticlesRepository
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class ArticlesRepositoryTest {

    private var articlesRepository: ArticlesRepository? = null
    private var fakeArticleEntity: ArticleEntity? = null
    private var fakeArticleEntitiesList = ArrayList<ArticleEntity>()

    @Mock
    private val articlesDatabaseInteractor: ArticlesDatabaseInteractor? = null

    @Mock
    private val articlesNetworkInteractor: ArticlesNetworkInteractor? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        articlesRepository = ArticlesRepository(articlesNetworkInteractor!!, articlesDatabaseInteractor!!)

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
    fun fetchAllArticlesByArticlesRepository() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<List<ArticleEntity>>()
        articleEntityLiveData.setValue(fakeArticleEntitiesList);

        // Prepare fake empty list for backend call
        val fakeList = ArrayList<ApiResponse.Article>()
        val fakeNetworkCallResult = Result.success(fakeList)

        // Set testing conditions
        Mockito.`when`(articlesDatabaseInteractor?.getAllArticles()).thenReturn(articleEntityLiveData)
        Mockito.`when`(articlesNetworkInteractor?.getAllArticles()).thenReturn(Observable.just(fakeNetworkCallResult))

        // Perform the action
        val storedArticles = articlesRepository?.getAllArticles()

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticles);
    }

    @Test
    fun fetchArticleByArticlesRepository() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<ArticleEntity>()
        articleEntityLiveData.setValue(fakeArticleEntity);

        // Prepare fake article id
        val fakeArticleId = "fake/article/id"

        // Set testing conditions
        Mockito.`when`(articlesDatabaseInteractor?.getSingleSavedArticleById(fakeArticleId))
            .thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticle = articlesRepository?.getSingleSavedArticleById(fakeArticleId)

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticle);
    }
}