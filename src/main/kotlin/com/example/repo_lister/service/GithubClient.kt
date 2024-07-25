package com.example.repo_lister.service

import com.example.repo_lister.model.Branch
import com.example.repo_lister.model.GithubRepo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "githubClient", url = "https://api.github.com")
interface GithubClient {
    @GetMapping("/users/{username}/repos")
    fun getRepos(
        @PathVariable username: String,
        @RequestHeader("Accept") accept: String = "application/json"
    ): List<GithubRepo>

    @GetMapping("/repos/{owner}/{repo}/branches")
    fun getBranches(
        @PathVariable owner: String,
        @PathVariable repo: String,
        @RequestHeader("Accept") accept: String = "application/json"
    ): List<Branch>
}