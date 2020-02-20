package com.book.gallery.Extension

import android.app.Activity
import android.util.DisplayMetrics

val Activity.widthScreen: Int
    get() {
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }