package com.sample.simpsonsviewer.util.extensions

import android.widget.EditText

fun EditText.asString(): String {
    return if (this.text.isNullOrBlank()) {
        ""
    } else this.text.toString()
}