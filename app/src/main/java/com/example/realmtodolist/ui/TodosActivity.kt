package com.example.realmtodolist.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmtodolist.R
import com.example.realmtodolist.adapters.TodosAdapter
import com.example.realmtodolist.model.TodoList
import io.realm.Realm
import io.realm.kotlin.where

class TodosActivity : AppCompatActivity() {

    companion object {
        lateinit var listID: String
    }

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos)

        setupUI()
    }

    private fun setupUI() {
        // Set todolist title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("listName")

        listID = intent.getStringExtra("listID")!!

        realm = Realm.getDefaultInstance()

        // Perform query to find todoList from the listID that was passed through from previous
        // previous activity
        val todoList =
            realm.where<TodoList>().equalTo("listID", listID).findFirst()

        val todos = todoList?.todos

        // Todos contained within the todolist selected is displayed on the recycler view
        if (todos != null) {
            val recyclerView = findViewById<RecyclerView>(R.id.todos_recycler_view)
            val adapter = TodosAdapter(this, todos, true)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Return back to contact screen
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
