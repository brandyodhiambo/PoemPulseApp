package domain.model.title

data class TitleLine(
    val author: String,
    val linecount: String,
    val lines: List<String>,
    val title: String,
)
