package com.example.realmtodolist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realmtodolist.R
import com.example.realmtodolist.model.Todo
import com.example.realmtodolist.ui.MainActivity
import com.example.realmtodolist.ui.TodosActivity
import io.realm.RealmList
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.todo_view.view.*

/**
 * The adapter for the todos within todo lists
 *
 * @property context Context is the context of the activity
 * @property todos RealmResults<Todo> the list of todos from selected todolist
 * @constructor
 */
class TodosAdapter(
    private val context: Context,
    private val todos: RealmList<Todo>,
    autoUpdate: Boolean
) : RealmRecyclerViewAdapter<Todo, TodosAdapter.TodosViewHolder>(todos, autoUpdate) {

    inner class TodosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.todo_view, parent, false)
        return TodosViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        // Setting name of the to-do
        val todo = todos[position]
        holder.itemView.todo_name_tv.text = todo!!.todoName

        // Complete to-do and remove from todoList
        holder.itemView.todo_checkbox.setOnClickListener {
                holder.itemView.todo_checkbox.isChecked = true
                removeTodo(todo)
        }

        // Remove to-do
        holder.itemView.btn_delete_todo.setOnClickListener {
            removeTodo(todo)
        }
    }

    private fun removeTodo(todo: Todo) {
        // TodoList the to-do belongs to is fetched
        val todoList =
            MainActivity.todoListService.fetchTodoList(MainActivity.realm, TodosActivity.listID)

        // Deletes to-do from the realm database
        MainActivity.todoListService.deleteTodo(MainActivity.realm, todoList, todo)
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}
