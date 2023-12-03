package com.tistory.eclipse4j.restclient.conf

import com.tistory.eclipse4j.restclient.service.FakeRestClientService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory


@Configuration
class EduRestClientConfiguration {
    @Bean
    fun fakeClassRestClient(): RestClient {
        return RestClient.builder().baseUrl("https://dummy-json.mock.beeceptor.com")
            .defaultHeaders { headers: HttpHeaders ->
                headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                headers.set("header-custom", "9999-9999-1234")
            }
            .build()
    }

    @Bean
    fun fakeRestClientService(): FakeRestClientService {
        val client = RestClient.builder().baseUrl("https://dummy-json.mock.beeceptor.com")
            .defaultHeaders { headers: HttpHeaders ->
                headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                headers.set("header-custom", "9999-9999-1234")
            }
            .build()
        val factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(client))
            .build()
        return factory.createClient(FakeRestClientService::class.java)
    }
}