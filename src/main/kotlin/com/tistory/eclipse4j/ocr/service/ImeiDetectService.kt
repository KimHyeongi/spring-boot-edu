package com.tistory.eclipse4j.ocr.service

import com.google.cloud.vision.v1p4beta1.*
import com.tistory.eclipse4j.ocr.response.DetectedImei
import com.tistory.eclipse4j.vision.StreamToAnnotationService
import mu.two.KotlinLogging
import org.springframework.stereotype.Service
import java.io.InputStream

/** https://cloud.google.com/vision/docs/ocr?hl=ko */
@Service
class ImeiDetectService(
    private val provider: StreamToAnnotationService
) {
    private val log = KotlinLogging.logger {}
    fun read(inputStream: InputStream): DetectedImei {
        val imeiSet = mutableSetOf<String>()
        val ediSet = mutableSetOf<String>()
        val requests = provider.annotateImageRequests(inputStream, Feature.Type.TEXT_DETECTION)
        ImageAnnotatorClient.create().use { client ->
            val responses = client.batchAnnotateImages(requests).responsesList
            responses.forEach{
                if (it.hasError()) {
                    throw RuntimeException("이미지를 분석할 수 없습니다.")
                }
                val ts = it.textAnnotationsList
                ts.forEach skip@{ e ->
                    val description = e.description.trim()
                    if( OcrImeiRegex.number(description) ) {
                        return@skip
                    }
                    if (OcrImeiRegex.edi(description)) {
                        ediSet.add(OcrImeiRegex.findImeiByDescription(description))
                    } else if (OcrImeiRegex.imei(description)) {
                        imeiSet.add(OcrImeiRegex.findImeiByDescription(description))
                    }
                }
            }
        }
        log.info{"IMEI SET : $imeiSet"}
        log.info{"EDI SET : $ediSet"}
        return DetectedImei(
            imeiSet = imeiSet,
            ediSet = ediSet
        )
    }
}

internal class OcrImeiRegex {
    companion object {
        private val regexImei = "[0-9]{14,15}".toRegex()
        private val regexEid = "[0-9]{31,32}".toRegex()
        private val numberRegex = "^[0-9]".toRegex()

        fun imei(desc: String) = regexImei.containsMatchIn(desc)
        fun findImeiByDescription(desc: String) = regexImei.find(desc)?.value!!
        fun edi(desc: String) = regexEid.containsMatchIn(desc)
        fun findEdiByDescription(desc: String) = regexEid.find(desc)?.value!!
        fun number(desc: String) = numberRegex.containsMatchIn(desc)
    }
}
