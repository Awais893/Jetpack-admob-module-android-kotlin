package com.shadowarm.admodule.ads.appopen.policy

object AdDisplayController {

    private var currentScreenTag: String? = null
    private lateinit var policyManager: AppOpenPolicyManager

    private var appOpenBlocked = false

    fun init(policyManager: AppOpenPolicyManager) {
        this.policyManager = policyManager
    }

    fun onScreenVisible(screenTag: String) {
        currentScreenTag = screenTag
    }

    fun onScreenHidden(screenTag: String) {
        if (currentScreenTag == screenTag) {
            currentScreenTag = null
        }
    }


    fun suppressAppOpen() {
        appOpenBlocked = true
    }

    fun resumeAppOpen() {
        appOpenBlocked = false
    }

    fun canShowAppOpen(): Boolean {
        return !appOpenBlocked
    }

    fun canShowAppOpenUsingPolicy(): Boolean {
        if (!policyManager.isAppOpenEnabled()) return false

        val screen = currentScreenTag
        if (screen != null && policyManager.isBlockedForScreen(screen)) {
            return false
        }

        return true
    }
}
