package com.example.repo_lister

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class RepoListerApplication

fun main(args: Array<String>) {
	runApplication<RepoListerApplication>(*args)
}
