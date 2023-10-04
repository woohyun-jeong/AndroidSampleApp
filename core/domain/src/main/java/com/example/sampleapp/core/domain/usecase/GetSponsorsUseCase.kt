package com.example.sampleapp.core.domain.usecase

import com.example.sampleapp.core.data.repository.SponsorRepository
import com.example.sampleapp.core.model.Sponsor
import javax.inject.Inject

class GetSponsorsUseCase @Inject constructor(
    private val sponsorRepository: SponsorRepository,
) {

    suspend operator fun invoke(): List<Sponsor> {
        return sponsorRepository
            .getSponsors()
            .sortedBy { it.grade.priority }
    }
}
