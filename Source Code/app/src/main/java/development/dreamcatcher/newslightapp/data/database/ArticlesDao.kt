package development.dreamcatcher.newslightapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewArticle(articleEntity: ArticleEntity)

    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    fun getSingleSavedArticleById(id: String): LiveData<ArticleEntity>?

    @Query("SELECT * FROM articles")
    fun getAllSavedArticles(): LiveData<List<ArticleEntity>>?

    @Query("DELETE FROM articles")
    fun clearDatabase()
}