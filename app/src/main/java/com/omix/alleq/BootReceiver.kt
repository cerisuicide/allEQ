package com.omix.alleq

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.omix.alleq.service.AudioProcessorService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val serviceIntent = Intent(context, AudioProcessorService::class.java).apply {
            action = AudioProcessorService.ACTION_START
        }
        ContextCompat.startForegroundService(context, serviceIntent)
    }
}
