package com.example.realmtodolist.application

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Realm database is initialised
        Realm.init(this)
        // Configuration is set
        val realmConfiguration: RealmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}
