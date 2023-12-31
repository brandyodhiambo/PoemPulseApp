package domain.usecase

import domain.repository.PoemPulseRepository

class GetTitleLineUseCase(private val poemPulseRepository: PoemPulseRepository) {
    suspend operator fun invoke(title:String) = poemPulseRepository.getTitleLine(title)
}