package com.gvetri.kotlin.videolibrary.core.extensions

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hasPipSupport(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)