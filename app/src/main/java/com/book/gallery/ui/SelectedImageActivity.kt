package com.book.gallery.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.book.gallery.Extension.ContextImages
import com.book.gallery.R
import com.book.gallery.adapter.ThumbnailAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_selected_image.*

class SelectedImageActivity : Activity(),
View.OnClickListener{

    private lateinit var adapter: ThumbnailAdapter
    private var selectedImage: Uri? = null
    private var imagesUri = arrayListOf<Uri>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_image)
        imagesUri = this.ContextImages
        adapter = ThumbnailAdapter(this, imagesUri)
        thumbnailView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        thumbnailView.adapter = adapter
        adapter.notifyDataSetChanged()
        btnBack.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
        btnAdd.setOnClickListener(this)
        showImagePreview(imagesUri[0])
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBack -> onBackPressed()
            R.id.btnDelete -> deleteImage()
            R.id.btnAdd -> addNewImage()
        }
    }

    private fun addNewImage() {
        startActivity(Intent(this, GalleryActivity::class.java))
    }

    private fun deleteImage() {
        selectedImage?.let {
            adapter.removeImage(it)
            if(imagesUri.size > 0) showImagePreview(imagesUri[0])
        }
    }

    fun showImagePreview(image: Uri){
        selectedImage = image
        Picasso.get().load(image).into(imageView)
    }
}
