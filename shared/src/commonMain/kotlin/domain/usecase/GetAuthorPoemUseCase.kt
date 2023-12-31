package domain.usecase

import domain.repository.PoemPulseRepository

class GetAuthorPoemUseCase(
    private val poemPulseRepository: PoemPulseRepository,
) {
    suspend operator fun invoke(authorName: String) = poemPulseRepository.getAuthorPoem(authorName)
}
