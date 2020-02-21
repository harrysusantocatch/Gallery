package com.book.gallery.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.book.gallery.R
import com.book.gallery.adapter.ImageAdapter
import com.book.gallery.adapter.ListFolderAdapter
import com.book.gallery.model.Folder
import com.book.gallery.model.SelectedImages
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : Activity(),
View.OnClickListener{

    private var images = arrayListOf<Uri>()
    private var folders = arrayListOf<Folder>()
    private lateinit var folderAdapter: ListFolderAdapter
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var selectedImages: SelectedImages

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        folderAdapter = ListFolderAdapter(this, folders)
        listFolder.layoutManager = LinearLayoutManager(this)
        listFolder.adapter = folderAdapter

        imageAdapter = ImageAdapter(this, images)
        gridView.adapter = imageAdapter

        btnBack.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        folderNameView.setOnClickListener(this)

        loadFolder()
        showImageByFolder(folders[0])
    }

    fun showFooter(positions: HashMap<Int, Int> ){
        if(positions.size > 0){
            btnSave.visibility = View.VISIBLE
            footer.visibility = View.VISIBLE
            totalSelectedView.text = "(${positions.size}/10)"
            val imagesUri = arrayListOf<Uri>()
            for (position in positions.keys){
                imagesUri.add(images[position])
            }
            selectedImages = SelectedImages(imagesUri)
        }else{
            footer.visibility = View.GONE
            btnSave.visibility = View.GONE
        }
    }

    fun showImageByFolder(folder: Folder) {
        images.clear()
        images.addAll(folder.imagesUri)
        imageAdapter.counterSelected.clear()
        imageAdapter.counter = 0
        imageAdapter.notifyDataSetChanged()
        this.gridView.visibility = View.VISIBLE
        this.listFolder.visibility = View.GONE
        this.folderNameView.text = folder.name
        this.footer.visibility = View.GONE
    }

    private fun loadFolder() {
        val allImages = arrayListOf<Uri>()
        val folders = arrayListOf<Folder>()
        val listFolder = getListFolderFromDevice()
        for (folderName in listFolder){
            val imagesFolder = getImagesByFolderDevice(folderName)
            val folder = Folder(folderName, imagesFolder)
            folders.add(folder)
            allImages.addAll(imagesFolder)
        }
        folders.sortWith(compareBy {it.name})
        folders.add(0, Folder("All Images", allImages))
        this.folders.addAll(folders)
    }

    private fun showFoldersView() {
        folderAdapter.notifyDataSetChanged()
        this.gridView.visibility = View.GONE
        this.listFolder.visibility = View.VISIBLE
        this.folderNameView.text = "Folder"
        this.btnSave.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBack -> onBackPressed()
            R.id.btnSave -> saveSelectedImage()
            R.id.folderNameView -> {showFoldersView()}
        }
    }

    private fun saveSelectedImage() {
        val intent = Intent(this, SelectedImageActivity::class.java)
        intent.putExtra("imagesUri", selectedImages)
        startActivity(intent)
    }

    @SuppressLint("Recycle")
    private fun getListFolderFromDevice(): HashSet<String> {
        val folders = hashSetOf<String>()
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID
        )
        val cursor: Cursor? = this.contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val folderName: String =
                    it.getString(it.getColumnIndex(projection[0]))
                folders.add(folderName)
            }
            it.close()
        }
        return folders
    }

    @SuppressLint("Recycle")
    private fun getImagesByFolderDevice(bucketPath: String): ArrayList<Uri> {
        val listOfAllImages = arrayListOf<Uri>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val columnIndexID: Int
        var imageId: Long
        val projection =
            arrayOf(MediaStore.Images.Media._ID)
        val selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " =?"
        val orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC"
        val cursor: Cursor? = this.contentResolver
            .query(uri, projection, selection, arrayOf(bucketPath), orderBy)
        cursor?.let {
            columnIndexID = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                imageId = it.getLong(columnIndexID)
                val uriImage = Uri.withAppendedPath(uri, "" + imageId)
                listOfAllImages.add(uriImage)
            }
            it.close()
        }
        return listOfAllImages
    }
}
