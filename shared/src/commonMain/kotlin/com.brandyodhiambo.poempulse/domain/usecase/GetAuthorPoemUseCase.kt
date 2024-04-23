package com.brandyodhiambo.poempulse.domain.usecase

import com.brandyodhiambo.poempulse.domain.repository.PoemPulseRepository

class GetAuthorPoemUseCase(
    private val poemPulseRepository: PoemPulseRepository,
) {
    suspend operator fun invoke(authorName: String) = poemPulseRepository.getAuthorPoem(authorName)
}
