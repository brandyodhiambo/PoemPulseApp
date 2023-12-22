import data.network.dto.todaypoem.TodayPoemDto
import domain.model.todaypoem.TodayPoem

fun TodayPoemDto.toDomain() = TodayPoem(
    author = author,
    linecount = linecount ,
    lines = lines,
    title = title
)