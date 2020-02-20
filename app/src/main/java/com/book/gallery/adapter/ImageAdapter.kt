package com.book.gallery.adapter

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.book.gallery.Extension.widthScreen
import com.book.gallery.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_image.view.*

class ImageAdapter(private val context: Context,
                   private val images: ArrayList<Uri>):
    BaseAdapter(){
    private val counterSelected = hashMapOf<Int, Int>()
    private var counter = 0
    private var width = (context as Activity).widthScreen / 3

    override fun getView(position: Int, converView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        var view = converView
        if(view == null){
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.adapter_image, parent, false)
            viewHolder = ViewHolder()
            viewHolder.imageView = view!!.imageView
            viewHolder.counterView = view.counterView
            view.tag = viewHolder
        }else {
            viewHolder = view.tag as ViewHolder
        }
        if(counterSelected[position] != null){
            viewHolder.counterView?.visibility = View.VISIBLE
            viewHolder.counterView?.text = counterSelected[position]?.toString()
        }else
            viewHolder.counterView?.visibility = View.GONE
        Picasso.get().load(images[position]).resize(width, width).centerCrop().into(viewHolder.imageView)
        viewHolder.imageView?.setOnClickListener {
            if(viewHolder.counterView?.visibility == View.VISIBLE){
                viewHolder.counterView?.visibility = View.GONE
                counter--
                counterSelected.remove(position)
            }else{
                viewHolder.counterView?.visibility = View.VISIBLE
                counter++
                counterSelected[position] = counter
                viewHolder.counterView?.text = counter.toString()
            }
        }
        return view
    }

    override fun getItem(position: Int): Uri? {
        return images[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return images.size
    }
    private class ViewHolder{
        var imageView: ImageView? = null
        var counterView: TextView? = null
    }
}