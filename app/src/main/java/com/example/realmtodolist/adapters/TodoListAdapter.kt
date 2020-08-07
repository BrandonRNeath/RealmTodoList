package com.example.realmtodolist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmtodolist.R
import com.example.realmtodolist.model.TodoList
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.todo_list.view.*

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.todo_list, parent, false)
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

    class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
