package development.dreamcatcher.newslightapp.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

// Interactor used for communication between data repository and external API
class ArticlesNetworkInteractor @Inject constructor(var apiClient: ApiClient) {

    val networkError: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllArticles(): Observable<Result<List<ApiResponse.Article>>> {
        val allArticlesSubject = SingleSubject.create<Result<List<ApiResponse.Article>>>()

        apiClient.getArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    allArticlesSubject.onSuccess(Result.success(it?.content!!))
                },
                {
                    networkError.postValue(true)
                    Log.e("getArticles() error: ", it.message)
                })

        return allArticlesSubject.toObservable()
    }
}