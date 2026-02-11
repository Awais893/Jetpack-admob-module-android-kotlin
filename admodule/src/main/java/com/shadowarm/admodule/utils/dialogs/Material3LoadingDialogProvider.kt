package com.shadowarm.admodule.utils.dialogs

import android.app.Activity
import android.app.Dialog

class Material3LoadingDialogProvider : LoadingDialogProvider {

    override fun show(activity: Activity): Dialog {
        val dialog = Material3FullScreenLoadingDialog(activity)
        if (!activity.isFinishing) {
            dialog.show()
        }
        return dialog
    }
}
