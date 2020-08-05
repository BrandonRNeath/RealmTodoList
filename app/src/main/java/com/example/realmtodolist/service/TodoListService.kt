package com.example.realmtodolist.service

import com.example.realmtodolist.api.TodoListInterface
import com.example.realmtodolist.model.Todo
import com.example.realmtodolist.model.TodoList
import io.realm.Realm

class TodoListService : TodoListInterface {
    /**
     * Adds new to-do list to realm database
     *
     * @param realmInstance Realm
     * @param todoList TodoList is the list to be added
     */
    override fun addList(realmInstance: Realm, todoList: TodoList) {
        realmInstance.beginTransaction()
        realmInstance.copyToRealm(todoList)
        realmInstance.commitTransaction()
    }

    /**
     * Deletes selected to-do list from realm database
     *
     * @param realmInstance Realm
     * @param todoList TodoList is the list to be deleted
     */
    override fun deleteList(realmInstance: Realm, todoList: TodoList) {
        TODO("Not yet implemented")
    }

    /**
     * Deletes to-do from the selected to-do list
     *
     * @param realmInstance Realm
     * @param todoList TodoList is the to-do list containing the to-do to be deleted
     * @param todo Todo is the to-do to be deleted
     */
    override fun deleteTodo(realmInstance: Realm, todoList: TodoList, todo: Todo) {
        TODO("Not yet implemented")
    }

    /**
     * Adds to-do to the selected to-do list
     *
     * @param realmInstance Realm
     * @param todoList TodoList is the to-do list containing the to-do to be added
     * @param todo Todo is the to-do to be added
     */
    override fun addTodo(realmInstance: Realm, todoList: TodoList, todo: Todo) {
        TODO("Not yet implemented")
    }

    /**
     * Removes all the to-dos contained with the selected to-do list
     *
     * @param realmInstance Realm
     * @param todoList TodoList is the to-do list to have to-dos removed from
     */
    override fun deleteAllTodos(realmInstance: Realm, todoList: TodoList) {
        TODO("Not yet implemented")
    }
}
