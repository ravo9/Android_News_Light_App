package development.dreamcatcher.newslightapp.data.network

import io.reactivex.Observable
import retrofit2.http.GET

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET("/309PryD")
    fun getArticles(): Observable<ApiResponse>
}