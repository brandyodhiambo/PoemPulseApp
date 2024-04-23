package com.brandyodhiambo.poempulse.domain.usecase

import com.brandyodhiambo.poempulse.domain.repository.PoemPulseRepository

class GetTitleLineUseCase(private val poemPulseRepository: PoemPulseRepository) {
    suspend operator fun invoke(title:String) = poemPulseRepository.getTitleLine(title)
}