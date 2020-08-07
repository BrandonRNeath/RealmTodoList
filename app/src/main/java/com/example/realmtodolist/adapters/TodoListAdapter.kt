package com.example.realmtodolist.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmtodolist.R
import com.example.realmtodolist.model.TodoList
import com.example.realmtodolist.ui.MainActivity
import com.example.realmtodolist.ui.TodosActivity
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.todo_list_view.view.*

/**
 * The adapter for the todo list recycler view
 *
 * @property context Context is the context of the activity
 * @property todoList RealmResults<TodoList> the results of the todo lists stored in realm database
 * @constructor
 */
class TodoListAdapter(
    private val context: Context,
    private val todoList: RealmResults<TodoList>,
    autoUpdate: Boolean
) : RealmRecyclerViewAdapter<TodoList, TodoListAdapter.TodoListViewHolder>(todoList, autoUpdate) {

    // Setting up item click
    private var onItemClick: ((TodoList) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.todo_list_view, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        // Setting name of the todo list
        val todoList = todoList[position]
        holder.itemView.todo_list_name.text = todoList!!.listName
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    inner class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                todoList[adapterPosition]?.let { selectedTodoList ->
                    onItemClick?.invoke(selectedTodoList)
                    val intent = Intent(MainActivity.context, TodosActivity::class.java).apply {
                        putExtra("listID", todoList[adapterPosition]?.listID)
                    }
                    MainActivity.context.startActivity(intent)
                }
            }
        }
    }
}
