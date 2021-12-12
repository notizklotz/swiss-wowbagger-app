package com.github.notizklotz.swisswowbagger.app.widget

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class InsultWidgetConfigureActivityViewModel: ViewModel() {
    val insultTargetName = mutableStateOf("")
}