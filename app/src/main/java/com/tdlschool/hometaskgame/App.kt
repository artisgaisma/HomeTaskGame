package com.tdlschool.hometaskgame

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.tdlschool.hometaskgame.common.LineNumberDebugThree
import com.tdlschool.hometaskgame.injection.DaggerInjectionComponent
import com.tdlschool.hometaskgame.injection.InjectionComponent
import com.tdlschool.hometaskgame.injection.InjectionModule
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(LineNumberDebugThree())
        }
        Timber.d("App created")
        component = DaggerInjectionComponent
            .builder()
            .injectionModule(InjectionModule(this))
            .build()
    }

    companion object {
        lateinit var component: InjectionComponent
            private set
    }
}
