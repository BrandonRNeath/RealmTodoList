package com.example.realmtodolist.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Todo(
    @PrimaryKey var todoID: String = "",
    var todoName: String = ""
) : RealmObject()
