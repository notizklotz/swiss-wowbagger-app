package com.github.notizklotz.swisswowbagger.app

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

const val CONFIG_KEY_WOWBAGGER_API_URL = "wowbagger_api_url"
const val CONFIG_KEY_WOWBAGGER_WEBSITE_URL = "wowbagger_website_url"

fun getApiBaseUrl(): String = Firebase.remoteConfig.getString(CONFIG_KEY_WOWBAGGER_API_URL)
fun getWebsiteBaseUrl(): String = Firebase.remoteConfig.getString(CONFIG_KEY_WOWBAGGER_WEBSITE_URL)