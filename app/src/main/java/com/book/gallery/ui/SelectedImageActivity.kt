package com.book.gallery.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.book.gallery.R
import com.book.gallery.model.SelectedImages
import kotlinx.android.synthetic.main.activity_selected_image.*

class SelectedImageActivity : Activity(),
View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_image)
        val images  = intent.getParcelableExtra("imagesUri") as SelectedImages
        val imagesUri = images.imagesUri
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBack -> onBackPressed()
        }
    }
}
