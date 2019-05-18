package development.dreamcatcher.newslightapp.features.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import development.dreamcatcher.newslightapp.R
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import development.dreamcatcher.newslightapp.features.detailedArticle.DetailedArticleFragment
import development.dreamcatcher.newslightapp.injection.NewsLightApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.loading_badge.*
import javax.inject.Inject

// Main (news feed) view
class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FeedViewModel
    private lateinit var articlesListAdapter: ArticlesListAdapter

    init {
        NewsLightApp.feedComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)

        // Initialize RecyclerView (Articles List)
        setupRecyclerView()

        // Fetch articles from the back-end and load them into the view
        subscribeForArticles()

        // Catch and handle potential network issues
        subscribeForNetworkError()

        // Setup refresh button
        btn_refresh.setOnClickListener{
            refreshArticlesSubscription()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView_articles.layoutManager = layoutManager
        articlesListAdapter = ArticlesListAdapter{ articleId: String -> displayDetailedView(articleId) }
        recyclerView_articles.adapter = articlesListAdapter
    }

    private fun subscribeForArticles() {
        viewModel.getAllArticles()?.observe(this, Observer<List<ArticleEntity>> {

            if (!it.isNullOrEmpty()) {

                // Hide the loading view
                showLoadingView(false)

                // Display fetched articles
                articlesListAdapter.setArticles(it)
            }
        })
    }

    private fun subscribeForNetworkError() {
        viewModel.getNetworkError()?.observe(this, Observer<Boolean> {

            // In case of Network Error...
            // If no articles have been cached
            if (articlesListAdapter.itemCount == 0) {

                // Display "Try Again" button
                tryagain_button.visibility = View.VISIBLE

                // Setup onClick listener that reset articles data subscription
                tryagain_button.setOnClickListener {
                    refreshArticlesSubscription()
                }
            }

            // Display error message to the user
            Toast.makeText(this, R.string.network_problem_check_internet_connection,
                Toast.LENGTH_LONG) .show()
        })
    }

    private fun refreshArticlesSubscription() {
        viewModel.getAllArticles()?.removeObservers(this)
        subscribeForArticles()
    }

    private fun displayDetailedView(articleId: String) {

        val fragment = DetailedArticleFragment()
        val bundle = Bundle()
        bundle.putString("articleId", articleId)
        fragment.arguments = bundle

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showLoadingView(loadingState: Boolean) {
        if (loadingState) {
            loading_container.visibility = View.VISIBLE
            appbar_container.visibility = View.GONE
        } else {
            loading_container.visibility = View.GONE
            appbar_container.visibility = View.VISIBLE
        }
    }
}
