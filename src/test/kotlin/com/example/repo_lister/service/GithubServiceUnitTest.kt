package com.example.repo_lister.service

import com.example.repo_lister.exception.UserNotFoundException
import com.example.repo_lister.model.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import kotlin.test.assertEquals

@RestClientTest(GithubService::class)
class GithubServiceUnitTest {

    @Autowired
    private val server: MockRestServiceServer? = null

    @Autowired
    private val service: GithubService? = null

    @Autowired
    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `getNonForkRepos() when user is valid returns list of GithubRepo`() {
        // given
        val data: List<GithubRepo> = listOf(
            GithubRepo("lenstra", Owner("olafmyszak"), false),
            GithubRepo("repo-lister", Owner("olafmyszak"), false)
        )

        // when
        this.server!!
            .expect(requestTo("https://api.github.com/users/olafmyszak/repos"))
            .andRespond(withSuccess(objectMapper.writeValueAsString(data), MediaType.APPLICATION_JSON))

        //then
        val response: List<GithubRepo> = service!!.getNonForkRepos("olafmyszak")
        assertEquals(expected = data, actual = response)
    }

    @Test
    fun `getNonForkRepos() when user is invalid throws UserNotFoundException`() {
        assertThrows<UserNotFoundException> {
            // given
            val notExistingUser = "sjdfijdsfkosfjoidskf"

            // when
            this.server!!
                .expect(requestTo("https://api.github.com/users/${notExistingUser}/repos"))
                .andRespond(withResourceNotFound())

            //then
            service!!.getNonForkRepos(notExistingUser)  //throws
        }
    }

    @Test
    fun `getBranches() when owner and repo are valid returns list of Branch`() {
        //given
        val data: List<Branch> = listOf(
            Branch("main", Commit("2423rdsdsadadssa")),
            Branch("v23", Commit("sdsdadqwws123tedffds"))
        )
        val owner = "sraka"
        val repo = "dupa"

        //when
        this.server!!
            .expect(requestTo("https://api.github.com/repos/${owner}/${repo}/branches"))
            .andRespond(withSuccess(objectMapper.writeValueAsString(data), MediaType.APPLICATION_JSON))

        //then
        val response = service!!.getBranches(owner = owner, repoName = repo)
        assertEquals(expected = data, actual = response)
    }

    @Test
    fun `getBranches() when owner or repo is invalid throws UserNotFoundException`() {
        assertThrows<UserNotFoundException> {
            //given
            val owner = "invalid"
            val repo = "dupa"

            //when
            this.server!!
                .expect(requestTo("https://api.github.com/repos/${owner}/${repo}/branches"))
                .andRespond(withResourceNotFound())

            //then
            service!!.getBranches(owner = owner, repoName = repo)
        }
    }

    @Test
    fun `getNonForkReposWithBranches() when user is valid returns list of GithubRepoWithBranches`() {
        // given
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

        val data1: List<GithubRepo> = listOf(
            GithubRepo("lenstra", Owner("olafmyszak"), false),
            GithubRepo("repo-lister", Owner("olafmyszak"), false)
        )

        val data2: List<Branch> = listOf(
            Branch("main", Commit("2423rdsdsadadssa")),
            Branch("v23", Commit("sdsdadqwws123tedffds"))
        )

        val owner = "olafmyszak"

        // when
        this.server!!
            .expect(requestTo("https://api.github.com/users/${owner}/repos"))
            .andRespond(withSuccess(objectMapper.writeValueAsString(data1), MediaType.APPLICATION_JSON))

        this.server
            .expect(requestTo("https://api.github.com/repos/${owner}/lenstra/branches"))
            .andRespond(withSuccess(objectMapper.writeValueAsString(data2), MediaType.APPLICATION_JSON))

        this.server
            .expect(requestTo("https://api.github.com/repos/${owner}/repo-lister/branches"))
            .andRespond(withSuccess(objectMapper.writeValueAsString(data2), MediaType.APPLICATION_JSON))

        //then
        val response: List<GithubRepoWithBranches> = service!!.getNonForkReposWithBranches("olafmyszak")
        assertEquals(expected = data, actual = response)
    }

    @Test
    fun `getNonForkReposWithBranches() when user is invalid throws UserNotFoundException`() {
        assertThrows<UserNotFoundException> {
            // given
            val username = "invalid"

            // when
            this.server!!
                .expect(
                    requestTo(
                        matchesPattern(
                            "https://api\\.github\\.com/users/${username}/repos"
                        )
                    )
                )
                .andRespond(withResourceNotFound())

            this.server
                .expect(
                    requestTo(
                        matchesPattern(
                            "https://api\\.github\\.com/repos/${username}/[A-Za-z0-9]+/branches"
                        )
                    )
                )
                .andRespond(withResourceNotFound())

            //then
            service!!.getNonForkReposWithBranches(username)
        }
    }
}