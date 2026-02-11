package com.shadowarm.admodule.ads.appopen.policy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun AppOpenAwareScreen(
    screenTag: String,
    content: @Composable () -> Unit
) {
    DisposableEffect(screenTag) {
        AdDisplayController.onScreenVisible(screenTag)
        onDispose {
            AdDisplayController.onScreenHidden(screenTag)
        }
    }
    content()
}
/*
* this is how you can use
* firstly check either enable or not
AppOpenAwareScreen(ScreenTags.LANGUAGE) {
    LanguageSelectionScreen()
}*/
