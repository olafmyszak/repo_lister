package com.example.repo_lister

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RepoListerApplication

fun main(args: Array<String>) {
	runApplication<RepoListerApplication>(*args)
}
