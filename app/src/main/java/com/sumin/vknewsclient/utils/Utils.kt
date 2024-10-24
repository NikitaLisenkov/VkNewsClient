package com.sumin.vknewsclient.utils

import android.annotation.SuppressLint
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.cancellation.CancellationException


fun mapTimestampToDate(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    return SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
}


fun String.encode(): String {
    return Uri.encode(this)
}


@SuppressLint("DefaultLocale")
fun formatStatisticCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", (count / 1000))
    } else if (count > 1000) {
        String.format("%.1fK", (count / 1000f))
    } else {
        count.toString()
    }
}


suspend fun <T> runSuspendCatching(
    block: suspend () -> T,
    onSuccess: suspend (T) -> Unit = {},
    onError: suspend (Throwable) -> Unit = {}
): T? {
    return try {
        val result = block()
        onSuccess(result)
        result
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        onError(e)
        null
    }
}