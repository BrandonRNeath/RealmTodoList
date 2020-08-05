package com.example.realmtodolist.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.realmtodolist.R
import com.example.realmtodolist.model.Todo
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

        // Testing add list function
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

        Log.d("Test", "---------------------------------------------------------")
        // Testing addTodo function
        val todo1 = Todo()
        todo1.todoID = UUID.randomUUID().toString()
        todo1.todoName = "9 am meeting"
        todoListService.addTodo(realm, todoList, todo1)

        val todo2 = Todo()
        todo2.todoID = UUID.randomUUID().toString()
        todo2.todoName = "Buy Apples"
        todoListService.addTodo(realm, todoList2, todo2)

        Log.d("Test", realm.where<TodoList>().findAll().toString())

        for (x in realm.where<TodoList>().findAll()) {
            Log.d("Test", x.todos[0]?.todoName.toString())
        }

        Log.d("Test", "---------------------------------------------------------")
        Log.d("Test", "Delete Todo")
        todoListService.deleteTodo(realm, todoList, todo1)
        Log.d("Test", realm.where<TodoList>().findAll().toString())
        Log.d("Test", "---------------------------------------------------------")

        // Testing delete list function
        todoListService.deleteList(realm, todoList)
        todoListService.deleteList(realm, todoList2)

        Log.d("Test", realm.where<TodoList>().findAll().toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
