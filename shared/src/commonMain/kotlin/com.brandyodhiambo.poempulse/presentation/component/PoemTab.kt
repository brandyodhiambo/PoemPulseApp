package com.brandyodhiambo.poempulse.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import com.brandyodhiambo.poempulse.presentation.author.AuthorScreen
import com.brandyodhiambo.poempulse.presentation.title.TitleScreen
import com.brandyodhiambo.poempulse.presentation.todaypoem.TodayPoemScreen

internal sealed class PoemTab{

    internal object HomeTab: Tab {
        @Composable
        override fun Content() {
            TodayPoemScreen()
        }

        @OptIn(ExperimentalResourceApi::class)
        override val options: TabOptions
            @Composable
            get() {
                val title = "Home"
                val icon = painterResource("home_outlined.xml")

                return remember{
                    TabOptions(
                        index = 0u,
                        title = title,
                        icon = icon
                    )
                }
            }

    }

    internal object AuthorTab: Tab {
        @Composable
        override fun Content() {
            AuthorScreen()
        }

        @OptIn(ExperimentalResourceApi::class)
        override val options: TabOptions
            @Composable
            get() {
                val title = "Author"
                val icon = painterResource("author_outlined.xml")

                return remember{
                    TabOptions(
                        index = 1u,
                        title = title,
                        icon = icon
                    )
                }
            }

    }

    internal object TitleTab: Tab {
        @Composable
        override fun Content() {
            TitleScreen()
        }

        @OptIn(ExperimentalResourceApi::class)
        override val options: TabOptions
            @Composable
            get() {
                val title = "Poem Titles"
                val icon = painterResource("title_outlined.xml")

                return remember{
                    TabOptions(
                        index = 2u,
                        title = title,
                        icon = icon
                    )
                }
            }

    }
}