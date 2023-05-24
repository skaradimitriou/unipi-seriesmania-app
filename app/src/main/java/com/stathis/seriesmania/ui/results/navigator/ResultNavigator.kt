package com.stathis.seriesmania.ui.results.navigator

interface ResultNavigator {

    fun init(mode: ResultAction)
    fun navigateTo(screenKey: ResultAction)
    fun goBack()
}