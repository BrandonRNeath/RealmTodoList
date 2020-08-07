package com.example.realmtodolist.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmtodolist.R
import com.example.realmtodolist.adapters.TodoListAdapter
import com.example.realmtodolist.model.Todo
import com.example.realmtodolist.model.TodoList
import com.example.realmtodolist.service.TodoListService
import io.realm.Realm
import io.realm.Realm.getDefaultInstance
import io.realm.RealmList
import io.realm.kotlin.where
import java.util.UUID

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var context: Context
    }

    private lateinit var realm: Realm
    private lateinit var todoListService: TodoListService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

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

        val todo3 = Todo()
        todo3.todoID = UUID.randomUUID().toString()
        todo3.todoName = "Buy Coffee"
        todoListService.addTodo(realm, todoList2, todo3)

        Log.d("Test", realm.where<TodoList>().findAll().toString())

        val recyclerView = findViewById<RecyclerView>(R.id.todo_lists_recycler_view)
        val result = realm.where<TodoList>().findAll()
        val adapter = TodoListAdapter(this, result, true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Test recycler view auto update
        val todoList3 = TodoList()
        todoList3.listID = UUID.randomUUID().toString()
        todoList3.listName = "Uni Work"
        todoList3.todos = RealmList()
        todoListService.addList(realm, todoList3)

//        // Testing deleteTodo function
// //        Log.d("Test", "---------------------------------------------------------")
// // //        Log.d("Test", "Delete Todo")
// // //        todoListService.deleteTodo(realm, todoList, todo1)
// // //        Log.d("Test", realm.where<TodoList>().findAll().toString())
// // //        Log.d("Test", "---------------------------------------------------------")
//
//        // Testing deleteAllTodos
//        Log.d("Test", "Add more Todos")
//        Log.d("Test", "---------------------------------------------------------")
//        val todo3 = Todo()
//        todo3.todoID = UUID.randomUUID().toString()
//        todo3.todoName = "Buy Chocolates"
//        todoListService.addTodo(realm, todoList2, todo3)
//        val todo4 = Todo()
//        todo4.todoID = UUID.randomUUID().toString()
//        todo4.todoName = "Buy Coffee"
//        todoListService.addTodo(realm, todoList2, todo4)
//        Log.d("Test", realm.where<TodoList>().findAll().toString())
//        Log.d("Test", "Delete all Todos")
//        todoListService.deleteAllTodos(realm, todoList2)
//        Log.d("Test", realm.where<TodoList>().findAll().toString())
//        Log.d("Test", "---------------------------------------------------------")
//        Log.d("Test", "Add new todo")
//        val todo5 = Todo()
//        todo5.todoID = UUID.randomUUID().toString()
//        todo5.todoName = "Buy Orange Juice"
//        todoListService.addTodo(realm, todoList2, todo5)
//        Log.d("Test", realm.where<TodoList>().findAll().toString())
//
//        Log.d("Test", "Delete Lists")
//        // Testing delete list function
//        todoListService.deleteList(realm, todoList)
//        todoListService.deleteList(realm, todoList2)
//        Log.d("Test", realm.where<TodoList>().findAll().toString())
//        Log.d("Test", "---------------------------------------------------------")
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
