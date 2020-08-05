package com.example.realmtodolist.api

import com.example.realmtodolist.model.Todo
import com.example.realmtodolist.model.TodoList
import io.realm.Realm

interface TodoListInterface {
    fun addList(realmInstance: Realm, todoList: TodoList)
    fun deleteList(realmInstance: Realm, todoList: TodoList)
    fun deleteTodo(realmInstance: Realm, todoList: TodoList, todo: Todo)
    fun addTodo(realmInstance: Realm, todoList: TodoList, todo: Todo)
    fun deleteAllTodos(realmInstance: Realm, todoList: TodoList, todo: Todo)
}
