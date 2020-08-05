package com.example.realmtodolist.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.realmtodolist.R
import com.example.realmtodolist.model.TodoList
import com.example.realmtodolist.service.TodoListService
import io.realm.Realm
import io.realm.Realm.getDefaultInstance
import io.realm.RealmList
import io.realm.kotlin.where
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var todoListService: TodoListService
    private lateinit var realmList: RealmList<TodoList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.deleteRealm(Realm.getDefaultConfiguration())

        realm = getDefaultInstance()

        todoListService = TodoListService()
        val todoList = TodoList()
        todoList.listID = UUID.randomUUID().toString()
        todoList.listName = "Work"
        todoList.todos = RealmList()
        val todoList2 = TodoList()
        todoList2.listID = UUID.randomUUID().toString()
        todoList2.listName = "Shopping"
        todoList2.todos = RealmList()
        todoListService.addList(realm, todoList)
        todoListService.addList(realm, todoList2)

        Log.d("Test", realm.where<TodoList>().findAll().toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
