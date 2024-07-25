package com.example.repo_lister.controller

import com.example.repo_lister.service.GithubService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class GithubController(private val githubService: GithubService) {
    @GetMapping("/users/{username}/repos")
    fun listNonForkRepos(
        @PathVariable username: String,
        @RequestHeader("Accept") accept: String
    ): ResponseEntity<List<Map<String, Any>>> {
        val repos = githubService.getNonForkRepos(username)
        val response = repos.map { repo ->
            mapOf(
                "repositoryName" to repo.name,
                "ownerLogin" to repo.owner.login,
                "branches" to githubService.getBranches(repo.owner.login, repo.name).map { branch ->
                    mapOf("name" to branch.name, "lastCommitSha" to branch.commit.sha)
                }
            )
        }

        return ResponseEntity.ok(response)
    }
}