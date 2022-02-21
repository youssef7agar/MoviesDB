package com.example.moviesdb.common

import android.app.Activity
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    // Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = currentFocus
    // If no view currently has focus, create a new one, just so we can grab a window token from it.
    if (view == null) {
        view = View(this)
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun SharedPreferences.stringSet(
    defaultValue: Set<String> = setOf(),
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, Set<String>> =
    object : ReadWriteProperty<Any, Set<String>> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = getStringSet(key(property), defaultValue)!!

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Set<String>
        ) = edit().putStringSet(key(property), value).apply()
    }