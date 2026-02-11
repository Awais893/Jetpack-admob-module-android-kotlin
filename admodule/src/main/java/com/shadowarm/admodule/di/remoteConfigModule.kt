package com.shadowarm.admodule.di

import com.shadowarm.admodule.ads.remote.FirebaseRemoteConfigProvider
import com.shadowarm.admodule.ads.remote.RemoteConfigProvider
import com.shadowarm.admodule.ads.remote.provideFirebaseRemoteConfig
import org.koin.dsl.module

val remoteConfigModule = module {

    single {
        provideFirebaseRemoteConfig()
    }

    single<RemoteConfigProvider> {
        FirebaseRemoteConfigProvider(get())
    }
}
