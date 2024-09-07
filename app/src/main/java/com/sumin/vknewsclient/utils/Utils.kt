package com.sumin.vknewsclient.utils

import android.net.Uri

fun String.encodeForUri(): String {
    return Uri.encode(this)
}