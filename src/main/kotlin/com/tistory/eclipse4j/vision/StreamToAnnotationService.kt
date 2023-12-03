package com.tistory.eclipse4j.vision

import com.google.cloud.vision.v1p4beta1.AnnotateImageRequest
import com.google.cloud.vision.v1p4beta1.Feature
import com.google.cloud.vision.v1p4beta1.Image
import com.google.protobuf.ByteString
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class StreamToAnnotationService {

    /**
     * Feature.Type.SAFE_SEARCH_DETECTION
     * Feature.Type.TE
     */
    fun annotateImageRequests(inputStream: InputStream, type: Feature.Type): List<AnnotateImageRequest> {
        val imgBytes = ByteString.readFrom(inputStream)
        val img = Image.newBuilder().setContent(imgBytes).build()
        val feat = Feature.newBuilder().setType(type).build()
        val request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build()
        return listOf(request)
    }
}
