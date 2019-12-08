package com.grayimediary.testmania

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

//aliases
typealias AnyMap = Map<String, Any>


//Ui
fun View.gone() { visibility = View.GONE }
fun View.visible() { visibility = View.VISIBLE }
fun View.invisible() { visibility = View.INVISIBLE }

fun ViewGroup.inflate(resId: Int, attachToRoot: Boolean = false) =
    LayoutInflater.from(context).inflate(resId, this, attachToRoot)

fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
fun Context.toast(textId: Int) = Toast.makeText(this, textId, Toast.LENGTH_SHORT).show()

fun assertEditInput(input: TextInputLayout, errorText: String) {
    if (input.editText?.text.isNullOrBlank()) {
        input.error = errorText
        throw NullPointerException()
    } else {
        input.error = null
    }
}