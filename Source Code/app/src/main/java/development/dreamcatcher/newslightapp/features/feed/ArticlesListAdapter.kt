package development.dreamcatcher.newslightapp.features.feed

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import development.dreamcatcher.newslightapp.R
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import kotlinx.android.synthetic.main.articles_column_triplet.view.*
import kotlinx.android.synthetic.main.articles_list_row.view.*
import java.lang.Exception
import kotlinx.android.synthetic.main.articles_viewpager_triplet.view.*

// Main adapter used for managing articles list within the main Recycler (List) View
class ArticlesListAdapter (val clickListener: (String) -> Unit) : RecyclerView.Adapter<ArticlesListAdapter.ViewHolder>() {

    private var articlesList: List<List<ArticleEntity>> = ArrayList()
    private var context: Context? = null

    companion object {
        internal val VIEW_TYPE_COLUMN = 0
        internal val VIEW_TYPE_VIEWPAGER = 1
    }

    fun setArticles(articles: List<ArticleEntity>) {
        this.articlesList = convertArticlesListIntoTripletsList(articles)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    override fun getItemViewType(position: Int): Int {
        // Return 0 or 1 per each position to indicate viewtype.
        return position % 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        var viewHolder: ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {

            VIEW_TYPE_COLUMN -> {
                val columnView = inflater
                    .inflate(R.layout.articles_column_triplet, parent, false)
                viewHolder = ColumnViewHolder(columnView)
            }

            VIEW_TYPE_VIEWPAGER -> {
                val viewPagerView = inflater
                    .inflate(R.layout.articles_viewpager_triplet, parent, false)
                viewHolder = ViewPagerViewHolder(viewPagerView)
            }
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (holder.itemViewType) {

            VIEW_TYPE_COLUMN -> {
                val columnViewHolder = holder as ColumnViewHolder
                configureColumnTriplet(columnViewHolder, position)
            }

            VIEW_TYPE_VIEWPAGER -> {
                val viewPagerViewHolder = holder as ViewPagerViewHolder
                configureViewPagerTriplet(viewPagerViewHolder, position)
            }
        }

        /*// Set onClickListener
        holder.itemView.setOnClickListener{
            val articleId = articlesList[position].id
            clickListener(articleId)
        }*/
    }

    private fun configureColumnTriplet(holder: ColumnViewHolder, position: Int) {
        for ((index, article) in holder.articleViews.withIndex()) {
            try {
                // Prepare fetched data
                val title = articlesList[position][index].title
                val summary = articlesList[position][index].summary
                val thumbnailUrl = articlesList[position][index].thumbnailUrl

                // Set data within the holder
                article.textView_title.text = title
                article.textView_summary.text = summary

                // Load thumbnail
                Picasso.with(context).load(thumbnailUrl).into(article.thumbnail)

                // Set onClickListener
                article.setOnClickListener{
                    val articleId = articlesList[position][index].id
                    clickListener(articleId)
                }

            } catch (e: Exception) {
                Log.e("Exception", e.message)
            }
        }
    }

    private fun configureViewPagerTriplet(holder: ViewPagerViewHolder, position: Int) {

        // Prepare fetched data
        val articlesTriplet = articlesList[position]

        // Set adapter to the viewpager
        val adapter = ViewPagerAdapter(clickListener, articlesTriplet, context!!)
        holder.viewPager.setAdapter(adapter)
    }

    abstract class ViewHolder (view: View) : RecyclerView.ViewHolder(view)

    inner class ColumnViewHolder (view: View) : ViewHolder(view) {
        val articleViews = listOf(view.article01, view.article02, view.article03)
    }

    inner class ViewPagerViewHolder (view: View) : ViewHolder(view) {
        val viewPager = view.viewpager_triplet_container
    }

    // Converter splitting articles together into 3-items clusters
    private fun convertArticlesListIntoTripletsList(articlesList: List<ArticleEntity>)
            : List<List<ArticleEntity>> {
        val tripletsList = ArrayList<List<ArticleEntity>>()
        articlesList.chunked(3).forEach {
            tripletsList.add(it)
        }
        return tripletsList
    }
}