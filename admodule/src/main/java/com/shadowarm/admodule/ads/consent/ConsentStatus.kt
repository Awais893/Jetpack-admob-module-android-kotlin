package com.shadowarm.admodule.ads.consent

sealed class ConsentStatus {
    object Required : ConsentStatus()
    object NotRequired : ConsentStatus()
    object Obtained : ConsentStatus()
    data class Error(val message: String) : ConsentStatus()
}
