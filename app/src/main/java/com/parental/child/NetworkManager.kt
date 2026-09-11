package com.parental.child

import com.parental.child.service.RemoteTouchService
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

object NetworkManager {
    private var socket: Socket? = null
    // Replace YOUR_RENDER_URL with your live Render URL (e.g., "https://parental-server-xxxx.onrender.com")
    private const val SERVER_URL = "YOUR_RENDER_URL"

    fun init() {
        if (socket == null) {
            socket = IO.socket(SERVER_URL)
            socket?.connect()

            socket?.on(Socket.EVENT_CONNECT) {
                val data = JSONObject().apply {
                    put("roomId", "ROOM_123")
                    put("role", "child")
                }
                socket?.emit("join_room", data)
            }

            socket?.on("touch_event") { args ->
                val json = args[0] as JSONObject
                val normX = json.getDouble("normX").toFloat()
                val normY = json.getDouble("normY").toFloat()
                RemoteTouchService.instance?.executeTap(normX, normY)
            }
        }
    }

    fun sendNotification(json: JSONObject) {
        json.put("roomId", "ROOM_123")
        socket?.emit("child_notification", json)
    }
}
