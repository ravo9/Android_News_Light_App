package development.dreamcatcher.newslightapp.data.network

import com.google.gson.annotations.SerializedName
import java.util.*

// ApiResponse object and its sub-objects used for deserializing data coming from API endpoint
class ApiResponse {

    @SerializedName("content")
    var content: List<Article> = ArrayList()

    data class Article(
        @SerializedName("contentId")
        val contentId: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("summary")
        val summary: String,

        @SerializedName("contentURL")
        val contentUrl: String,

        @SerializedName("images")
        val images: Images)

    // Images sub-object
    data class Images(
        @SerializedName("mainImageThumbnail")
        val mainImageThumbnail: MainImageThumbnail)

    // MainImageThumbnail sub-object
    data class MainImageThumbnail(
        @SerializedName("shortURL")
        val shortUrl: String)
}
