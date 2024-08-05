package com.example.repo_lister.controller

import com.example.repo_lister.model.GithubRepoWithBranches
import com.example.repo_lister.service.GithubService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class GithubController(private val githubService: GithubService) {
    @GetMapping("/users/{username}/repos")
    fun listNonForkRepos(
        @PathVariable username: String,
        @RequestHeader("Accept") accept: String
    ): List<GithubRepoWithBranches> {
        return githubService.getNonForkReposWithBranches(username = username)
    }
}