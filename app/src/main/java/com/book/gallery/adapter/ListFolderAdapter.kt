package com.book.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.book.gallery.ui.GalleryActivity
import com.book.gallery.R
import com.book.gallery.model.Folder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_list_folder.view.*

class ListFolderAdapter(private val context: Context,
                        private val folders: ArrayList<Folder>):
    RecyclerView.Adapter<ListFolderAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var rootView: ConstraintLayout = view.layoutView
        var thumbnailView: ImageView = view.thumbnailView
        var folderNameView: TextView = view.folderNameView
        var totalView: TextView = view.totalView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.adapter_list_folder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val folder = folders[position]
        if(folder.imagesUri.size > 0){
            Picasso.get().load(folder.imagesUri[0]).resize(65, 65).into(holder.thumbnailView)
        }
        holder.folderNameView.text = folder.name
        holder.totalView.text = folder.imagesUri.size.toString()
        holder.rootView.setOnClickListener {
            (context as GalleryActivity).showImageByFolder(folder)
        }
    }
}