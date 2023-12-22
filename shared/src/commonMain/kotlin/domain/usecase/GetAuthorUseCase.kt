package domain.usecase

import domain.repository.PoemPulseRepository

class GetAuthorUseCase(private val poemPulseRepository: PoemPulseRepository) {

    suspend operator fun invoke() = poemPulseRepository.getAuthor()
}
