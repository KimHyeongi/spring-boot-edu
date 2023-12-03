package com.tistory.eclipse4j.ocr.response

data class DetectedImei(
    val imeiSet: MutableSet<String> = mutableSetOf<String>(),
    val ediSet: MutableSet<String> = mutableSetOf<String>()
)