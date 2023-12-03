package com.tistory.eclipse4j.ocr


import com.google.cloud.vision.v1p4beta1.AnnotateImageRequest
import com.google.cloud.vision.v1p4beta1.AnnotateImageResponse
import com.google.cloud.vision.v1p4beta1.BatchAnnotateImagesResponse
import com.google.cloud.vision.v1p4beta1.Feature
import com.google.cloud.vision.v1p4beta1.Image
import com.google.cloud.vision.v1p4beta1.ImageAnnotatorClient
import com.google.protobuf.ByteString
import java.io.FileInputStream
import java.io.IOException


@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val visionApi = GoogleVisionApiClient()
    visionApi.detectLabels()
}

class GoogleVisionApiClient() {

    @Throws(IOException::class)
    fun detectLabels() {
        val filePath = "KakaoTalk_Photo_2023-11-17-00-30-59.jpeg"
        detectLabels(filePath)
    }

    @Throws(IOException::class)
    fun detectLabels(filePath: String?) {
        val requests: MutableList<AnnotateImageRequest> = ArrayList<AnnotateImageRequest>()
        val imgBytes = ByteString.readFrom(FileInputStream(filePath))
        val img: Image = Image.newBuilder().setContent(imgBytes).build()
        val feat: Feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build()
        val request: AnnotateImageRequest = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build()
        requests.add(request)
        ImageAnnotatorClient.create().use { client ->
            val response: BatchAnnotateImagesResponse = client.batchAnnotateImages(requests)
            val responses: List<AnnotateImageResponse> = response.responsesList
            val resultSet = mutableSetOf<String>()
            responses.forEach{
                if (it.hasError()) {
                    return
                }
                val ts = it.textAnnotationsList
                ts.forEach{
                    e ->
                    val regexImei = "[0-9]{14,15}".toRegex()
                    val regexEid = "[0-9]{31,32}".toRegex()

                    val description = e.description.trim()
                    val numberRegex = "^[0-9]".toRegex()
                    if( numberRegex.containsMatchIn(description) ){
                        if( regexEid.containsMatchIn(description) ){
                            resultSet.add(regexEid.find(description)?.value!!)
                        }else if(regexImei.containsMatchIn(description)){
                            resultSet.add(regexImei.find(description)?.value!!)
                        }
                    }
                }
            }
            resultSet.forEach { println(it) }
        }
    }
}