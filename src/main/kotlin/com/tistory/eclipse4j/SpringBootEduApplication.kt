package com.tistory.eclipse4j

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["com.tistory.eclipse4j"]
)
class SpringBootEduApplication

fun main(args: Array<String>) {
    runApplication<SpringBootEduApplication>(*args)
}
