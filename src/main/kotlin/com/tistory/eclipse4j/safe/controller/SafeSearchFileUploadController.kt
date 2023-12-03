package com.tistory.eclipse4j.safe.controller

import com.tistory.eclipse4j.controller.response.ApiResponse
import com.tistory.eclipse4j.safe.response.SafeSearchResult
import com.tistory.eclipse4j.safe.service.SafeSearchService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class SafeSearchFileUploadController(
    private val safeSearchService: SafeSearchService
) {


    @PostMapping("/safe-search/simple-file-upload")
    fun upload(
        @RequestParam("image") multipart: MultipartFile
    ): ApiResponse<List<SafeSearchResult>> {
        val uploadedResponse = safeSearchService.read(multipart.inputStream)
        return ApiResponse.ok(
            uploadedResponse
        )
    }
}
