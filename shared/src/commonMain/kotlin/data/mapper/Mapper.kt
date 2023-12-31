import data.network.dto.author.AuthorPoemDto
import data.network.dto.givenwordpoem.GivenWordPoemDto
import data.network.dto.title.TitleLineDto
import data.network.dto.todaypoem.TodayPoemDto
import domain.model.author.AuthorPoem
import domain.model.givenwordpoem.GivenWordPoem
import domain.model.title.TitleLine
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

fun GivenWordPoemDto.toDomain() = GivenWordPoem(
    author = author,
    linecount = linecount,
    lines = lines,
    title = title
)

fun TitleLineDto.toDomain() = TitleLine(
    author = author,
    linecount = linecount,
    lines = lines,
    title = title
)
