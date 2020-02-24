package com.book.gallery.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.book.gallery.Extension.saveContextImages
import com.book.gallery.R
import com.book.gallery.ui.SelectedImageActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_thumbnail.view.*

class ThumbnailAdapter(private val context: Context,
                       private val images: ArrayList<Uri>):
    RecyclerView.Adapter<ThumbnailAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var thumbnailView: ImageView = view.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.adapter_thumbnail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        Picasso.get().load(image).resize(75, 75).centerCrop().into(holder.thumbnailView)
        holder.thumbnailView.setOnClickListener {
            (context as SelectedImageActivity).showImagePreview(image)
        }
    }

    fun removeImage(image: Uri){
        images.remove(image)
        this.notifyDataSetChanged()
        context.saveContextImages(images)
    }
}