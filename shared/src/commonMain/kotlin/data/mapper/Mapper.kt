import data.network.dto.author.AuthorPoemDto
import data.network.dto.todaypoem.TodayPoemDto
import domain.model.author.AuthorPoem
import domain.model.todaypoem.TodayPoem

fun TodayPoemDto.toDomain() = TodayPoem(
    author = author,
    linecount = linecount,
    lines = lines,
    title = title,
)

fun AuthorPoemDto.toDomain() = AuthorPoem(
    author = author,
    linecount = linecount,
    lines = lines,
    title = title,
)
