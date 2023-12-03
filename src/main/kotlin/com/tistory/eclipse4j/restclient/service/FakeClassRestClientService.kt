package com.tistory.eclipse4j.restclient.service

import com.tistory.eclipse4j.restclient.response.Post
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class FakeClassRestClientService(
    private val fakeRestClient: RestClient
) {
    fun posts(): List<Post> {
        return fakeRestClient.get()
            .uri("/posts")
            .retrieve()
            .body(object : ParameterizedTypeReference<List<Post>>(){})!!
    }

    fun post(): Post {
        return fakeRestClient.get()
            .uri("/posts/1")
            .retrieve()
            .body(object : ParameterizedTypeReference<Post>(){})!!
    }
}