package com.tistory.eclipse4j.restclient.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Post(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("userId")
    val userId: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("body")
    val body: String,
    @JsonProperty("link")
    val link: String,
    @JsonProperty("comment_count")
    val commentCount: Int
)
