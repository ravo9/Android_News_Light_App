package development.dreamcatcher.newslightapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import development.dreamcatcher.newslightapp.data.database.ArticlesDao
import development.dreamcatcher.newslightapp.data.database.ArticlesDatabase
import development.dreamcatcher.newslightapp.data.database.ArticlesDatabaseInteractor
import development.dreamcatcher.newslightapp.data.network.ApiResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ArticlesDatabaseInteractorTest {

    private var articlesDatabaseInteractor: ArticlesDatabaseInteractor? = null
    private var fakeArticle: ApiResponse.Article? = null
    private var fakeArticleEntity: ArticleEntity? = null

    @Mock
    private val articlesDatabase: ArticlesDatabase? = null

    @Mock
    private val articlesDao: ArticlesDao? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        articlesDatabaseInteractor = ArticlesDatabaseInteractor(articlesDatabase!!)

        // Prepare fake data
        val contentId = "fake/Article/Id"
        val title = "Fake Article Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake sub-objects
        val mainImageThumbnailSubObject = ApiResponse.MainImageThumbnail(thumbnailUrl)
        val imagesObjectSubObject = ApiResponse.Images(mainImageThumbnailSubObject)

        // Prepare fake Article (API object)
        fakeArticle = ApiResponse.Article(contentId, title, summary, contentUrl, imagesObjectSubObject)

        // Prepare fake Article Entity (DB object)
        fakeArticleEntity = ArticleEntity(contentId, title, summary, contentUrl, thumbnailUrl)
    }

    @Test
    fun saveArticleByDatabaseInteractor() {

        // Perform the action
        val resultStatus = articlesDatabaseInteractor!!.addNewArticle(fakeArticle).value

        // Check results
        Assert.assertTrue(resultStatus!!)
    }

    @Test
    fun fetchArticleByDatabaseInteractor() {

        // Prepare LiveData structure
        val articleEntityLiveData = MutableLiveData<ArticleEntity>()
        articleEntityLiveData.setValue(fakeArticleEntity);

        // Set testing conditions
        Mockito.`when`(articlesDatabase?.getArticlesDao()).thenReturn(articlesDao)
        Mockito.`when`(articlesDao?.getSingleSavedArticleById(anyString())).thenReturn(articleEntityLiveData)

        // Perform the action
        val storedArticle = articlesDatabaseInteractor?.getSingleSavedArticleById("fake-article-id")

        // Check results
        Assert.assertSame(articleEntityLiveData, storedArticle);
    }
}