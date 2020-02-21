package com.book.gallery.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SelectedImages(val imagesUri: ArrayList<Uri>): Parcelable