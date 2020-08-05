package com.example.realmtodolist.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class List(
    @PrimaryKey var listID: String = "",
    var listName: String = "",
    var todos: RealmList<Todo> = RealmList()
): RealmObject()