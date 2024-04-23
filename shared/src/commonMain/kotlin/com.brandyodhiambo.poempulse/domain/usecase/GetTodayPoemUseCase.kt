package com.brandyodhiambo.poempulse.domain.usecase

import com.brandyodhiambo.poempulse.domain.repository.PoemPulseRepository

class GetTodayPoemUseCase(private val poemPulseRepository: PoemPulseRepository) {
    suspend operator fun invoke(randomPoemCount: Int) = poemPulseRepository.getTodayPoem(randomPoemCount)
}
