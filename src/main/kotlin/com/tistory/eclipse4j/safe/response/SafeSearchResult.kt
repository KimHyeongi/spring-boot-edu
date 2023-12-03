package com.tistory.eclipse4j.safe.response

import com.google.cloud.vision.v1p4beta1.Likelihood

data class SafeSearchResult (
    val adult: Likelihood,
    val adultValue: Int,
    val medical: Likelihood,
    val medicalValue: Int,
    val spoof: Likelihood,
    val spoofValue: Int,
    val violence: Likelihood,
    val violenceValue: Int,
    val racy: Likelihood,
    val racyValue: Int
)
