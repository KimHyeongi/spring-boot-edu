package com.tistory.eclipse4j.restclient.conf

import com.tistory.eclipse4j.restclient.service.FakeRestClientService
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory


@Configuration
class EduRestClientConfiguration() {
    @Bean
    fun fakeClassRestClient(): RestClient {
        return RestClient.builder().baseUrl("https://dummy-json.mock.beeceptor.com")
            .requestFactory(requestFactory())
            .defaultHeaders { headers: HttpHeaders ->
                headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                headers.set("header-custom", "9999-9999-1234")
            }
            .build()
    }

    @Bean
    fun fakeRestClientService(): FakeRestClientService {
        val client = RestClient.builder().baseUrl("https://dummy-json.mock.beeceptor.com")
            .requestFactory(requestFactory())
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


    private fun requestFactory(): HttpComponentsClientHttpRequestFactory {
        val requestFactory = HttpComponentsClientHttpRequestFactory(httpClient())
        requestFactory.setConnectionRequestTimeout(3000)// milliseconds
        requestFactory.setConnectTimeout(3000)// milliseconds
        return requestFactory
    }

    private fun httpClient(): CloseableHttpClient {
        return HttpClients.custom()
            .setConnectionManager(poolingConnectionManager())
            .build()
    }

    private fun poolingConnectionManager(): PoolingHttpClientConnectionManager {
        val poolingConnectionManager = PoolingHttpClientConnectionManager()
        poolingConnectionManager.maxTotal = 20 // 전체 연결
        poolingConnectionManager.defaultMaxPerRoute = 10 // 라우터별 최대 연결수 - 기본
        return poolingConnectionManager
    }

}