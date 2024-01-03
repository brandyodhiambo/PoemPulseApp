package domain.usecase

import domain.repository.PoemPulseRepository

class GetGivenWordTitleUseCase(private val poemPulseRepository: PoemPulseRepository) {

    suspend operator fun invoke(word:String) = poemPulseRepository.getGivenWordTitle(word)
}