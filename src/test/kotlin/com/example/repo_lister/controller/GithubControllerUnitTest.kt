package com.example.repo_lister.controller

import com.example.repo_lister.exception.UserNotFoundException
import com.example.repo_lister.model.Branch
import com.example.repo_lister.model.Commit
import com.example.repo_lister.model.GithubRepoWithBranches
import com.example.repo_lister.service.GithubService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import kotlin.test.assertEquals


class GithubControllerUnitTest {

    @Test
    fun `listNonForkRepos() when user is valid returns list of GithubRepoWithBranches`() {
        //given
        val githubServiceMock: GithubService = mockk<GithubService>()

        val data: List<GithubRepoWithBranches> = listOf(
            GithubRepoWithBranches(
                "lenstra",
                "olafmyszak",
                listOf(
                    Branch("main", Commit("2423rdsdsadadssa")),
                    Branch("v23", Commit("sdsdadqwws123tedffds"))
                )
            ),
            GithubRepoWithBranches(
                "repo-lister",
                "olafmyszak",
                listOf(
                    Branch("main", Commit("2423rdsdsadadssa")),
                    Branch("v23", Commit("sdsdadqwws123tedffds"))
                )
            ),
        )

        val username = "olafmyszak"
        every { githubServiceMock.getNonForkReposWithBranches(username = username) } returns data

        //when
        val githubController = GithubController(githubServiceMock)
        val result = githubController.listNonForkRepos(username = username, accept = "application/json")

        //then
        assertEquals(expected = data, actual = result)
    }

    @Test
    fun `listNonForkRepos() when user is invalid throws UserNotFoundException`()
    {
        //then
        assertThrows<UserNotFoundException> {
            //given
            val githubServiceMock: GithubService = mockk<GithubService>()

            val username = "invalid"
            every { githubServiceMock.getNonForkReposWithBranches(username = username) } throws UserNotFoundException(
                HttpStatusCode.valueOf(404),
                HttpHeaders()
            )

            //when
            val githubController = GithubController(githubServiceMock)
            githubController.listNonForkRepos(username = username, accept = "application/json")
        }
    }
}