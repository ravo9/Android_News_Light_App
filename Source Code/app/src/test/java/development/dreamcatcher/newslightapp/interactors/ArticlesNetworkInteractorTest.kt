package development.dreamcatcher.newslightapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import development.dreamcatcher.newslightapp.data.network.ApiClient
import development.dreamcatcher.newslightapp.data.network.ApiResponse
import development.dreamcatcher.newslightapp.data.network.ArticlesNetworkInteractor
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ArticlesNetworkInteractorTest {

    private var articlesNetworkInteractor: ArticlesNetworkInteractor? = null
    private val fakeApiResponse: ApiResponse = ApiResponse()

    @Mock
    private val apiClient: ApiClient? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        articlesNetworkInteractor = ArticlesNetworkInteractor(apiClient!!)

        // Prepare fake data
        val contentId = "fake/Article/Id"
        val title = "Fake Article Title"
        val summary = "Sport"
        val contentUrl = "http://google.com"
        val thumbnailUrl = "http://google.com/picture.jpg"

        // Prepare fake sub-object
        val mainImageThumbnailSubObject = ApiResponse.MainImageThumbnail(thumbnailUrl)
        val imagesObjectSubObject = ApiResponse.Images(mainImageThumbnailSubObject)
        val fakeArticle = ApiResponse.Article(contentId, title, summary, contentUrl, imagesObjectSubObject)
        val fakeArticlesList = ArrayList<ApiResponse.Article>()
        fakeArticlesList.add(fakeArticle)

        // Prepare fake ApiResponse
        fakeApiResponse.content = fakeArticlesList
    }
}
