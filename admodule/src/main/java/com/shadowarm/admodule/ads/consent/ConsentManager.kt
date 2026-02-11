package com.shadowarm.admodule.ads.consent

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

class ConsentManager(
    private val context: Context,
    private val config: ConsentConfig
) {

    private val consentInformation: ConsentInformation =
        UserMessagingPlatform.getConsentInformation(context)

    fun requestConsent(
        activity: Activity,
        onResult: (ConsentStatus) -> Unit
    ) {
        val params = buildConsentParams()

        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            {
                if (consentInformation.isConsentFormAvailable) {
                    loadAndShowConsentForm(activity, onResult)
                } else {
                    onResult(resolveConsentStatus())
                }
            },
            { formError ->
                onResult(ConsentStatus.Error(formError.message))
            }
        )
    }

    private fun loadAndShowConsentForm(
        activity: Activity,
        onResult: (ConsentStatus) -> Unit
    ) {
        UserMessagingPlatform.loadConsentForm(
            context,
            { consentForm ->
                if (consentInformation.consentStatus ==
                    ConsentInformation.ConsentStatus.REQUIRED
                ) {
                    consentForm.show(activity) {
                        onResult(resolveConsentStatus())
                    }
                } else {
                    onResult(resolveConsentStatus())
                }
            },
            { formError ->
                onResult(ConsentStatus.Error(formError.message))
            }
        )
    }

    private fun resolveConsentStatus(): ConsentStatus {
        return when (consentInformation.consentStatus) {
            ConsentInformation.ConsentStatus.REQUIRED ->
                ConsentStatus.Required

            ConsentInformation.ConsentStatus.OBTAINED ->
                ConsentStatus.Obtained

            ConsentInformation.ConsentStatus.NOT_REQUIRED ->
                ConsentStatus.NotRequired

            else ->
                ConsentStatus.Error("Unknown consent state")
        }
    }

    fun canRequestAds(): Boolean {
        return consentInformation.canRequestAds()
    }

    private fun buildConsentParams(): ConsentRequestParameters {
        val builder = ConsentRequestParameters.Builder()

        if (config.isDebug) {
            val debugSettings = ConsentDebugSettings.Builder(context)
                .setDebugGeography(
                    ConsentDebugSettings
                        .DebugGeography
                        .DEBUG_GEOGRAPHY_EEA
                )
                .apply {
                    config.testDeviceHashedIds.forEach {
                        addTestDeviceHashedId(it)
                    }
                }
                .build()

            builder.setConsentDebugSettings(debugSettings)
        }

        return builder.build()
    }
}
