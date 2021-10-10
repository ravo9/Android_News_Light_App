package development.dreamcatcher.newslightapp.features.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import development.dreamcatcher.newslightapp.R
import development.dreamcatcher.newslightapp.data.database.ArticleEntity
import kotlinx.android.synthetic.main.articles_viewpager_item.view.*
import kotlinx.android.synthetic.main.articles_viewpager_item.view.textView_title

// Internal adapter providing functionality of viewpagers nested within main articles list (recycler view)
class ViewPagerAdapter(val clickListener: (String) -> Unit, list: List<ArticleEntity>?, private val context: Context) : PagerAdapter() {

    private val inflater: LayoutInflater
    private val articlesList = ArrayList<ArticleEntity>()

    init {
        if (list != null && list.isNotEmpty()) {
            articlesList.addAll(list)
        }
        this.inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val rootView = inflater.inflate(R.layout.articles_viewpager_item, container, false)
        val holder = ViewHolder(rootView)

        // Prepare fetched data
        val title = articlesList[position].title
        val thumbnailUrl = articlesList[position].thumbnailUrl

        // Set data within the holder
        holder.title.text = title

        // Load thumbnail
        Picasso.with(context).load(thumbnailUrl).into(holder.thumbnail)

        // Set onClickListener
        rootView.setOnClickListener{
            val articleId = articlesList[position].id
            clickListener(articleId)
        }

        // Insert article view into the ViewPager
        container.addView(rootView);

        return rootView
    }

    override fun getCount(): Int {
        return articlesList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    internal inner class ViewHolder(view: View) {
        val title = view.textView_title
        val thumbnail = view.thumbnail
    }
}