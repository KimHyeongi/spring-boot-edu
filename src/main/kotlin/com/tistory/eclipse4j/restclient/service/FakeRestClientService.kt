package com.tistory.eclipse4j.restclient.service

import com.tistory.eclipse4j.restclient.response.Post
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange

/** Interface 기반으로 Client를 구성한다 */
interface FakeRestClientService {
    @GetExchange("/posts")
    fun posts(): List<Post>

    @GetExchange("/posts/1")
    fun post(@RequestParam id: Long): Post
}