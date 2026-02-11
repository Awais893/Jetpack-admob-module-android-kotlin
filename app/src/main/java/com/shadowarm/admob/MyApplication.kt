package com.shadowarm.admob

import android.app.Application
import com.google.firebase.FirebaseApp
import com.shadowarm.admodule.di.adsModule
import com.shadowarm.admodule.di.remoteConfigModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@MyApplication)

            modules(
                adsModule(
                    context = this@MyApplication,
                    isDebug = BuildConfig.DEBUG,
                    testDeviceHashedIds = listOf(
                        "TEST_DEVICE_HASH_ID"
                    )
                ),
                remoteConfigModule
            )
        }
    }
}
