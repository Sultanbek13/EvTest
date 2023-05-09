package com.sultandev.evtest.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

fun toast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun String.toPublishDate() = this.substring(0, 10)

fun getDate(): String {
    return SimpleDateFormat("MM", Locale.getDefault()).format(Date())
}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

fun <T> MutableLiveData<T>.set(newValue: T) = apply { setValue(newValue) }