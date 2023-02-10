package com.stackbuffers.myguardianangels.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Manager {

    public static String encodeToBase64(Uri uri, Activity activity) {
        String base64Image = "";
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);

            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            Log.d("TAG", "encodeToBase64: ");
        }

        return base64Image;
    }
}
