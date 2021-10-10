package development.dreamcatcher.newslightapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
        @PrimaryKey val id: String,
        val title: String,
        val summary: String?,
        val contentUrl: String,
        val thumbnailUrl: String)

