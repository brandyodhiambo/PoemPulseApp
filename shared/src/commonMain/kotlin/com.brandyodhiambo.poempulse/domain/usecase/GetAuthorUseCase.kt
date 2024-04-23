package com.brandyodhiambo.poempulse.domain.usecase

import com.brandyodhiambo.poempulse.domain.repository.PoemPulseRepository

class GetAuthorUseCase(private val poemPulseRepository: PoemPulseRepository) {

    suspend operator fun invoke() = poemPulseRepository.getAuthor()
}
