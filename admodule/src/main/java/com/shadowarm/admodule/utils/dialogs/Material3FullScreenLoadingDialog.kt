package com.shadowarm.admodule.utils.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

class Material3FullScreenLoadingDialog(
    private val activity: Activity
) : Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCancelable(false)
        setCanceledOnTouchOutside(false)

        WindowCompat.setDecorFitsSystemWindows(window!!, false)

        setContentView(
            ComposeView(context).apply {
                setContent {
                    MaterialTheme {
                        LoadingContent()
                    }
                }
            }
        )
    }

    @Composable
    private fun LoadingContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.45f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(56.dp),
                strokeWidth = 5.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
