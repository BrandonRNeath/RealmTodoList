package com.example.realmtodolist.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmtodolist.R
import com.example.realmtodolist.adapters.TodoListAdapter
import com.example.realmtodolist.model.TodoList
import com.example.realmtodolist.service.TodoListService
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.realm.Realm
import io.realm.Realm.getDefaultInstance
import io.realm.RealmList
import io.realm.kotlin.where
import java.util.UUID
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.bottom_sheet_todo_list.*

class TodoListActivity : AppCompatActivity() {

    companion object {
        lateinit var context: Context
        lateinit var realm: Realm
        lateinit var todoListService: TodoListService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        supportActionBar?.hide()

        context = this

        realm = getDefaultInstance()
        todoListService = TodoListService()

        setupRecyclerView()
        setupAddNewTodoList()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.todo_lists_recycler_view)
        val result = realm.where<TodoList>().findAll()
        val adapter = TodoListAdapter(this, result, true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            ))
    }

    /**
     *  Sets up functionality of floating action button for adding new to-do list
     */
    private fun setupAddNewTodoList() {
        add_todo_list_fab.setOnClickListener {

            // Set Bottom Sheet view
            val bottomSheetView = View.inflate(this, R.layout.bottom_sheet_todo_list, null)
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(bottomSheetView)

            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            bottomSheetDialog.setCanceledOnTouchOutside(false)
            bottomSheetDialog.et_add_todo_list.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    imm.hideSoftInputFromWindow(bottomSheetDialog.et_add_todo_list.windowToken, 0)
                    true
                } else {
                    false
                }
            }

            bottomSheetDialog.add_todo_list_btn.setOnClickListener {
                // Creating new to-do list with name entered from user within Edit Text view
                val todoList = TodoList()
                todoList.listID = UUID.randomUUID().toString()
                todoList.listName = bottomSheetDialog.et_add_todo_list.text.toString()
                todoList.todos = RealmList()
                todoListService.addList(realm, todoList)

                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
