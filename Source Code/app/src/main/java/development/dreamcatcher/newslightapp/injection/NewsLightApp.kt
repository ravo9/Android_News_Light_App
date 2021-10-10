package development.dreamcatcher.newslightapp.injection

import android.app.Application
import android.content.Context

class NewsLightApp : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var feedComponent: FeedComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        feedComponent = DaggerFeedComponent.builder().appModule(AppModule(this)).feedModule(FeedModule()).build()
    }
}