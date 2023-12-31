package domain.usecase

import domain.repository.PoemPulseRepository

class GetPoemTitleUseCase(private val poemPulseRepository: PoemPulseRepository) {

    suspend operator fun invoke() = poemPulseRepository.getTitle()
}