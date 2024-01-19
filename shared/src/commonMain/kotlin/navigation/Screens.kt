package navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class Screens:ScreenProvider{
    object GivenWordPoemScreen:Screens()
    data class AuthorPoemScreen(val authorName:String):Screens()
}