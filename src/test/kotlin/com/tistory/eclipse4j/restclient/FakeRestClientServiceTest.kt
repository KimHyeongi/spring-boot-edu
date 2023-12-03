package com.tistory.eclipse4j.restclient

import com.tistory.eclipse4j.SpringBootEduApplication
import com.tistory.eclipse4j.restclient.service.FakeRestClientService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import mu.two.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [SpringBootEduApplication::class])
class FakeRestClientServiceTest(
    sut: FakeRestClientService
) : StringSpec(
    {
        val log = KotlinLogging.logger {}

        "RestClient 목록 조회" {
            val posts = sut.posts()
            log.info{posts}
            posts.size shouldBeGreaterThan 0
        }

        "RestClient 단건 조회" {
            val post = sut.post(1)
            log.info{post}
            post.id shouldBe 1
        }
    }
)