package com.example.repo_lister.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubRepoWithBranches(
    @JsonProperty("repo_name") val repoName: String,
    @JsonProperty("owner_login") val ownerLogin: String,
    @JsonProperty("branches")
    @JsonSerialize(using = BranchSerializer::class) val branches: List<Branch>
)

@JsonIgnoreProperties(ignoreUnknown = true, value = ["fork"])
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

class BranchSerializer : JsonSerializer<List<Branch>>() {
    override fun serialize(branches: List<Branch>, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartArray()
        for (branch in branches) {
            gen.writeStartObject()
            gen.writeStringField("name", branch.name)
            gen.writeStringField("last_commit_sha", branch.commit.sha)
            gen.writeEndObject()
        }
        gen.writeEndArray()
    }
}
