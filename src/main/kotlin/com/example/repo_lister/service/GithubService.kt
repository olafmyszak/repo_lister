package com.example.repo_lister.service

import com.example.repo_lister.model.Branch
import com.example.repo_lister.model.GithubRepo
import feign.FeignException
import org.springframework.stereotype.Service

@Service
class GithubService(private val githubClient: GithubClient) {
    fun getNonForkRepos(username: String): List<GithubRepo> {
        return try {
            githubClient.getRepos(username).filter { !it.isFork }
        } catch (e: FeignException.NotFound) {
            throw e
        }
    }

    fun getBranches(owner: String, repo: String): List<Branch> {
        return try {
            githubClient.getBranches(owner, repo)
        } catch (e: FeignException.NotFound) {
            throw e
        }
    }
}