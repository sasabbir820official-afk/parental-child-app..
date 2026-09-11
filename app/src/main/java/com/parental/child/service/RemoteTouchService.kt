package com.parental.child.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent

class RemoteTouchService : AccessibilityService() {
    companion object {
        var instance: RemoteTouchService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() {}

    fun executeTap(normX: Float, normY: Float) {
        val metrics = resources.displayMetrics
        val path = Path().apply {
            moveTo(normX * metrics.widthPixels, normY * metrics.heightPixels)
        }
        val stroke = GestureDescription.StrokeDescription(path, 0, 50)
        val gesture = GestureDescription.Builder().addStroke(stroke).build()
        dispatchGesture(gesture, null, null)
    }
}
