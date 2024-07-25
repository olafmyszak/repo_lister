package com.example.repo_lister.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubRepo(
    @JsonProperty("name") val name: String,
    @JsonProperty("owner") val owner: Owner,
    @JsonProperty("fork") val isFork: Boolean
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Owner(
    @JsonProperty("login") val login: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Branch(
    @JsonProperty("name") val name: String,
    @JsonProperty("commit") val commit: Commit
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Commit(
    @JsonProperty("sha") val sha: String
)