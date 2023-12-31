package domain.model.author


data class AuthorPoem(
    val author: String,
    val linecount: String,
    val lines: List<String>,
    val title: String,
)
