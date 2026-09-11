package com.parental.child.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.parental.child.NetworkManager
import org.json.JSONObject

class ChildNotificationService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let {
            val title = it.notification.extras.getString(Notification.EXTRA_TITLE) ?: return
            val text = it.notification.extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: return

            val payload = JSONObject().apply {
                put("title", title)
                put("body", text)
            }
            NetworkManager.sendNotification(payload)
        }
    }
}
