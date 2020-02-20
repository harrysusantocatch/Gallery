package com.book.gallery

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity(),
View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGallery.setOnClickListener(this)
        btnCamera.setOnClickListener(this)

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            10
        )
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnGallery -> {startActivity(Intent(this, GalleryActivity::class.java))}
            R.id.btnCamera ->{}
        }
    }
}
