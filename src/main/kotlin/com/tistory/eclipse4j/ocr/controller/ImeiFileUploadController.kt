package com.tistory.eclipse4j.ocr.controller

import com.tistory.eclipse4j.controller.response.ApiResponse
import com.tistory.eclipse4j.ocr.response.DetectedImei
import com.tistory.eclipse4j.ocr.service.ImeiDetectService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class ImeiFileUploadController(
    private val imeiDetectService: ImeiDetectService
) {


    @PostMapping("/ocr/simple-file-upload")
    fun upload(
        @RequestParam("image") multipart: MultipartFile
    ): ApiResponse<DetectedImei> {
        val uploadedResponse = imeiDetectService.read(multipart.inputStream)
        return ApiResponse.ok(
            uploadedResponse
        )
    }
}

