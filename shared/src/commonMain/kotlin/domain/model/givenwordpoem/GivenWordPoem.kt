package domain.model.givenwordpoem

data class GivenWordPoem(
    val author: String,
    val linecount: String,
    val lines: List<String>,
    val title: String,
)
