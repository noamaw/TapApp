package com.noam.tapapp.utils

import android.util.Log

fun detectYoutubeUrls(message: String): MutableList<String> {
    val youtubeUrls = mutableListOf<String>()
    val listOfUrls = Regex("\"commandMetadata\":*\"webCommandMetadata\":*\"url\":\"").findAll(message).map { it.range.last }
    var charI = '.'
    var urlResult = ""
    var indexChar = 0
    for (urlStart in listOfUrls) {
        indexChar = urlStart
        charI = message[indexChar]
        while (charI != '"') {
            urlResult += charI
            indexChar++
            charI = message[indexChar]
        }
        youtubeUrls.add(urlResult)
        Log.d("TAG", "detectYoutubeUrls: $urlResult")
        urlResult = ""
    }
    return youtubeUrls
}

fun getYoutubeUrls(message: String): String {
    val listOfUrls = Regex("\"\"url\":\"/.*\",\"webPageType\"").findAll(message).map { it.range.last }
    var charI = '.'
    var urlResult = ""
    var indexChar = 0
    for (urlStart in listOfUrls) {
        indexChar = urlStart
        charI = message[indexChar]
        while (charI != '"') {
            urlResult += charI
            indexChar++
            charI = message[indexChar]
        }
        urlResult += ','
        urlResult += ' '
        Log.d("TAG", "detectYoutubeUrls: $urlResult")
    }
    return urlResult
}
