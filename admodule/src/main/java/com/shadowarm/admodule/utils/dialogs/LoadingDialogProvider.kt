package com.shadowarm.admodule.utils.dialogs

import android.app.Activity
import android.app.Dialog

interface LoadingDialogProvider {
    fun show(activity: Activity): Dialog
}
