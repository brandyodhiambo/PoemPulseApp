import data.network.dto.author.AuthorPoemDto
import data.network.dto.author.AuthorsDto
import data.network.dto.givenwordpoem.GivenWordPoemDto
import data.network.dto.title.GivenWordTitleDto
import data.network.dto.title.TitleLineDto
import data.network.dto.todaypoem.TodayPoemDto
import database.AuthorEntity
import domain.model.author.Author
import domain.model.author.AuthorPoem
import domain.model.givenwordpoem.GivenWordPoem
import domain.model.title.GivenWordTitle
import domain.model.title.TitleLine
import domain.model.todaypoem.TodayPoem
import kotlin.random.Random

/*
* Author
* */

fun Author.toAuthorEntity() = AuthorEntity(
    id = (1..1000).random(),
    name = name
)
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

fun GivenWordTitleDto.toDomain() = GivenWordTitle(
    title = title
)
