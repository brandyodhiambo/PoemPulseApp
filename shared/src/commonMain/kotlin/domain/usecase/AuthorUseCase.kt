package domain.usecase

import domain.repository.PoemPulseRepository

class AuthorUseCase(private val poemPulseRepository: PoemPulseRepository) {

    suspend operator fun invoke() = poemPulseRepository.getAuthor()
}
