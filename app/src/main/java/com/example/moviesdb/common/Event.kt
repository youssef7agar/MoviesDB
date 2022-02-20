package com.example.moviesdb.common

open class Event<out T>(private val content: T) {

    fun peekContent(): T = content
}
