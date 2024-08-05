package com.example.repo_lister.service

import com.example.repo_lister.exception.UserNotFoundException
import com.example.repo_lister.model.Branch
import com.example.repo_lister.model.GithubRepo
import com.example.repo_lister.model.GithubRepoWithBranches
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body


@Service
class GithubService(restClientBuilder: RestClient.Builder) {

    private final val restClient: RestClient = restClientBuilder.baseUrl("https://api.github.com").build()

    fun getNonForkReposWithBranches(username: String): List<GithubRepoWithBranches> {
        val repos: List<GithubRepo> = getNonForkRepos(username = username)

        val result: MutableList<GithubRepoWithBranches> = mutableListOf()

        for (repo in repos) {
            val githubRepoWithBranches = GithubRepoWithBranches(
                repoName = repo.name,
                ownerLogin = repo.owner.login,
                branches = getBranches(owner = username, repoName = repo.name)
            )

            result.add(githubRepoWithBranches)
        }

        return result
    }

    fun getNonForkRepos(username: String): List<GithubRepo> {
        return restClient.get()
            .uri("/users/{username}/repos", username)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw UserNotFoundException(response.statusCode, response.headers)
            }
            .body<List<GithubRepo>>()!!
            .filter { !it.isFork }
    }

    fun getBranches(owner: String, repoName: String): List<Branch> {
        return restClient.get()
            .uri("/repos/{owner}/{repo}/branches", owner, repoName)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw UserNotFoundException(response.statusCode, response.headers)
            }
            .body<List<Branch>>()!!
    }
}