package com.tistory.eclipse4j.ocr.conf

import com.google.auth.oauth2.GoogleCredentials
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.FileInputStream
import java.io.IOException


@Configuration
class GoogleConfiguration {

    @Bean
    @Throws(IOException::class)
    fun googleCredential(): GoogleCredentials {
        val serviceAccount = ClassPathResource("my-project-01-218811-2226833857a8.json").getFile()
        return GoogleCredentials.fromStream(FileInputStream(serviceAccount))
    }
}