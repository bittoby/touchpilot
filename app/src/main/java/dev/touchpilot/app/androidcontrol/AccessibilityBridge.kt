package dev.touchpilot.app.androidcontrol

object AccessibilityBridge {
    @Volatile
    private var service: TouchPilotAccessibilityService? = null

    fun attach(service: TouchPilotAccessibilityService) {
        this.service = service
    }

    fun detach(service: TouchPilotAccessibilityService) {
        if (this.service === service) {
            this.service = null
        }
    }

    fun isConnected(): Boolean = service != null

    fun observeScreen(): String {
        return service?.observeScreen() ?: "TouchPilot Control is not enabled."
    }

    fun tapByText(text: String): Boolean {
        if (text.isBlank()) return false
        return service?.tapByText(text) ?: false
    }

    fun typeIntoFocusedField(text: String): Boolean {
        if (text.isBlank()) return false
        return service?.typeIntoFocusedField(text) ?: false
    }

    fun pressBack(): Boolean {
        return service?.pressBack() ?: false
    }

    fun pressHome(): Boolean {
        return service?.pressHome() ?: false
    }
}
