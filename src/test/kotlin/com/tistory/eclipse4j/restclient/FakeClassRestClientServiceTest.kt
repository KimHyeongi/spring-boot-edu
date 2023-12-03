package com.tistory.eclipse4j.restclient

import com.tistory.eclipse4j.SpringBootEduApplication
import com.tistory.eclipse4j.restclient.service.FakeClassRestClientService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldNotBe
import mu.two.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [SpringBootEduApplication::class])
class FakeClassRestClientServiceTest(
    sut: FakeClassRestClientService
) : StringSpec(
    {
        val log = KotlinLogging.logger {}

        "RestClient 목록 조회" {
            val posts = sut.posts()
            log.info{posts}
            posts.size shouldBeGreaterThan 0
        }

        "RestClient 단건 조회" {
            val post = sut.post()
            log.info{post}
            post shouldNotBe null
        }
    }
)