package com.grayimediary.testmania

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//aliases
typealias AnyMap = Map<String, Any>

//net
suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine {
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.body() != null) {
                    it.resume(response.body()!!)
                } else {
                    it.resumeWithException(NullPointerException())
                }
            }

        })
    }
}

//Ui
fun View.gone() { visibility = View.GONE }
fun View.visible() { visibility = View.VISIBLE }
fun View.invisible() { visibility = View.INVISIBLE }

fun ViewGroup.inflate(resId: Int, attachToRoot: Boolean = false) =
    LayoutInflater.from(context).inflate(resId, this, attachToRoot)

fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
fun Context.toast(textId: Int) = Toast.makeText(this, textId, Toast.LENGTH_SHORT).show()