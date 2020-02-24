package com.book.gallery.Extension

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val Context.ContextImages: ArrayList<Uri>
get() {
    val type = object : TypeToken<ArrayList<Uri>>(){}.type
    val sharedPreferences = this.getSharedPreferences("Cache", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("Images", null) ?: return arrayListOf()
    return Gson().fromJson(json, type)
}

fun Context.saveContextImages(images: ArrayList<Uri>){
    val json = Gson().toJson(images)
    val sharedPreferences = this.getSharedPreferences("Cache", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("Images", json)
    editor.apply()
}