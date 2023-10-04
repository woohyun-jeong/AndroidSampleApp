package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.data.api.fake.FakeGithubApi
import com.example.sampleapp.core.data.api.model.ContributorResponse
import com.example.sampleapp.core.model.Contributor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class DefaultContributorRepositoryTest : BehaviorSpec() {

    private val repository: DefaultContributorRepository = DefaultContributorRepository(
        githubApi = FakeGithubApi(contributors)
    )

    init {
        Given("컨트리뷰터가 존재한다") {
            val expected = contributors

            When("컨트리뷰터를 조회한다") {
                val contributors: List<Contributor> = repository.getContributors(
                    owner = "droidknights", name = "app2023"
                )
                Then("컨트리뷰터를 반환한다") {
                    contributors.size shouldBe 1
                    contributors.all {
                        it.name == expected[0].name
                    }
                }
            }
        }
    }

    companion object {
        private val contributors = listOf(
            ContributorResponse(
                name = "test name", imageUrl = "test image url", githubUrl = "test github url"
            )
        )
    }
}
