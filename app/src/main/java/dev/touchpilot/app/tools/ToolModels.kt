package dev.touchpilot.app.tools

enum class ToolRisk {
    LOW,
    MEDIUM,
    HIGH,
    BLOCKED
}

data class ToolSpec(
    val name: String,
    val description: String,
    val risk: ToolRisk,
    val arguments: Map<String, String>
)

data class ToolResult(
    val ok: Boolean,
    val message: String,
    val data: Map<String, String> = emptyMap()
)

object AndroidToolCatalog {
    val initialTools = listOf(
        ToolSpec(
            name = "observe_screen",
            description = "Serialize the current Android accessibility tree.",
            risk = ToolRisk.LOW,
            arguments = emptyMap()
        ),
        ToolSpec(
            name = "tap",
            description = "Tap a visible UI target by semantic text.",
            risk = ToolRisk.MEDIUM,
            arguments = mapOf("text" to "Visible text or content description to tap.")
        ),
        ToolSpec(
            name = "type_text",
            description = "Type text into the currently focused input field.",
            risk = ToolRisk.MEDIUM,
            arguments = mapOf("text" to "Text to enter.")
        ),
        ToolSpec(
            name = "press_back",
            description = "Send Android back.",
            risk = ToolRisk.MEDIUM,
            arguments = emptyMap()
        ),
        ToolSpec(
            name = "press_home",
            description = "Return to the Android launcher.",
            risk = ToolRisk.MEDIUM,
            arguments = emptyMap()
        )
    )
}
