import com.brandyodhiambo.poempulse.data.network.dto.author.AuthorPoemDto
import com.brandyodhiambo.poempulse.data.network.dto.title.TitleLineDto
import database.TodayPoemEntity
import com.brandyodhiambo.poempulse.domain.model.author.AuthorPoem
import com.brandyodhiambo.poempulse.domain.model.title.TitleLine
import com.brandyodhiambo.poempulse.domain.model.todaypoem.TodayPoem


fun TodayPoemEntity.toDomain() = TodayPoem(
    author = author,
    linecount = linecount,
    lines = lines.split(","),
    title = title,
)

fun AuthorPoemDto.toDomain() = AuthorPoem(
    author = author,
    linecount = linecount,
    lines = lines,
    title = title,
)

fun TitleLineDto.toDomain() = TitleLine(
    author = author,
    linecount = linecount,
    lines = lines,
    title = title
)
