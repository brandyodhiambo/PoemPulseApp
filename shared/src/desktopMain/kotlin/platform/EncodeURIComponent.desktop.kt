package platform

actual fun String.encodeURIComponent(): String {
    val encodedString = buildString {
        for (char in this) {
            when (char) {
                ' ', '&', '+', '%', '\r', '\n', '\t' -> {
                    append('%')
                    append(char.code.toString(16).padStart(2, '0'))
                }
                else -> append(char)
            }
        }
    }
    return encodedString
}