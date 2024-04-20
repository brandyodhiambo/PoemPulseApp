package domain.usecase

import domain.repository.PoemPulseRepository

class GetTodayPoemUseCase(private val poemPulseRepository: PoemPulseRepository) {
    suspend operator fun invoke(randomPoemCount: Int) = poemPulseRepository.getTodayPoem(randomPoemCount)
}
