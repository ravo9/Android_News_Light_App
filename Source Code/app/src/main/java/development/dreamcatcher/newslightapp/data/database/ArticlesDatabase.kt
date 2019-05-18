package development.dreamcatcher.newslightapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(ArticleEntity::class)], version = 1)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun getArticlesDao(): ArticlesDao
}