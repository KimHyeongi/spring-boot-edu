package com.tistory.eclipse4j.safe.service

import com.google.cloud.vision.v1p4beta1.Feature
import com.google.cloud.vision.v1p4beta1.ImageAnnotatorClient
import com.tistory.eclipse4j.safe.response.SafeSearchResult
import com.tistory.eclipse4j.vision.StreamToAnnotationService
import mu.two.KotlinLogging
import org.springframework.stereotype.Service
import java.io.InputStream

/** https://cloud.google.com/vision/docs/reference/rpc/google.cloud.vision.v1#google.cloud.vision.v1.SafeSearchAnnotation */
@Service
class SafeSearchService(
    private val provider: StreamToAnnotationService
) {
    private val log = KotlinLogging.logger {}
    fun read(inputStream: InputStream): List<SafeSearchResult> {
        val results = mutableListOf<SafeSearchResult>()
        val requests = provider.annotateImageRequests(inputStream, Feature.Type.SAFE_SEARCH_DETECTION)
        ImageAnnotatorClient.create().use { client ->
            val response = client.batchAnnotateImages(requests)
            val responses = response.responsesList

            responses.forEach skip@{
                if( it.hasError() ) {
                    return@skip
                }
                val annotation = it.safeSearchAnnotation
                results.add(
                    SafeSearchResult(
                        adult = annotation.adult,
                        adultValue = annotation.adultValue,
                        medical = annotation.medical,
                        medicalValue = annotation.medicalValue,
                        spoof = annotation.spoof,
                        spoofValue = annotation.spoofValue,
                        violence = annotation.violence,
                        violenceValue = annotation.violenceValue,
                        racy = annotation.racy,
                        racyValue = annotation.racyValue
                    )
                )
            }
        }
        return results
    }
}