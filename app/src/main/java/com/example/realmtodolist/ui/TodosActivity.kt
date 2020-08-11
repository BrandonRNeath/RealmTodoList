package com.example.realmtodolist.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmtodolist.R
import com.example.realmtodolist.adapters.TodosAdapter
import com.example.realmtodolist.model.Todo
import com.example.realmtodolist.model.TodoList
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.realm.Realm
import io.realm.kotlin.where
import java.util.UUID
import kotlinx.android.synthetic.main.activity_todos.*
import kotlinx.android.synthetic.main.bottom_sheet_todo.*

class TodosActivity : AppCompatActivity() {

    companion object {
        lateinit var listID: String
    }

    private lateinit var realm: Realm
    private lateinit var todoList: TodoList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos)

        setupUI()
        setupAddNewTodo()
    }

    private fun setupUI() {
        // Set todolist title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("listName")

        listID = intent.getStringExtra("listID")!!

        realm = Realm.getDefaultInstance()

        // Perform query to find todoList from the listID that was passed through from previous
        // previous activity
        todoList =
            realm.where<TodoList>().equalTo("listID", listID).findFirst()!!

        val todos = todoList.todos

        // Todos contained within the todolist selected is displayed on the recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.todos_recycler_view)
        val adapter = TodosAdapter(this, todos, true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    /**
     *  Sets up functionality of floating action button for adding new to-do list
     */
    private fun setupAddNewTodo() {
        add_todo_fab.setOnClickListener {

            // Set Bottom Sheet view
            val bottomSheetView = View.inflate(this, R.layout.bottom_sheet_todo, null)
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(bottomSheetView)

            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            bottomSheetDialog.setCanceledOnTouchOutside(false)
            bottomSheetDialog.et_add_todo.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    imm.hideSoftInputFromWindow(bottomSheetDialog.et_add_todo.windowToken, 0)
                    true
                } else {
                    false
                }
            }

            bottomSheetDialog.add_todo_btn.setOnClickListener {
                // Creating new to-do with name entered from user within Edit Text view
                val todo = Todo()
                todo.todoID = UUID.randomUUID().toString()
                todo.todoName = bottomSheetDialog.et_add_todo.text.toString()
                TodoListActivity.todoListService.addTodo(TodoListActivity.realm, todoList, todo)

                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.show()
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
