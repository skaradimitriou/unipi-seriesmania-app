package com.stathis.seriesmania.ui.forum.navigator

interface ForumNavigator {

    fun init(mode: ForumAction)

    fun navigateTo(screenKey: ForumAction)

    fun goBack()
}