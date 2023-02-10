package com.stackbuffers.myguardianangels.manager

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream


object LocalManager {

    fun encode(imageUri: Uri,activity:Activity): String {
        val input = activity.contentResolver.openInputStream(imageUri)
        val image = BitmapFactory.decodeStream(input , null, null)

        // Encode image to base64 string
        val baos = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }

    fun decode(imageString: String): Bitmap {

        // Decode base64 string to image
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

}