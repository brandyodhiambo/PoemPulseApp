package domain.usecase

import domain.repository.PoemPulseRepository

class GetGivenWordPoemUseCase(private val poemPulseRepository: PoemPulseRepository) {

    suspend operator fun invoke(word:String) = poemPulseRepository.getGivenWordPoem(word)
}