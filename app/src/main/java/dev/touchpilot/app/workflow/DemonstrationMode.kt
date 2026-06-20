package dev.touchpilot.app.workflow

import android.content.SharedPreferences

/**
 * Manages demonstration recording mode state (issue #308).
 *
 * When demonstration mode is enabled, workflow traces are explicitly captured
 * from successful agent runs for the purpose of creating reusable demonstration
 * skills. When disabled, traces may still be captured in the background but are
 * not marked as demonstrations.
 *
 * The mode is stored in SharedPreferences so it persists across app restarts.
 * The default is OFF (disabled) so users must explicitly opt-in to recording.
 */
class DemonstrationMode(private val preferences: SharedPreferences) {
    
    /**
     * Returns true if demonstration recording mode is currently active.
     */
    fun isEnabled(): Boolean {
        return preferences.getBoolean(KEY_DEMONSTRATION_MODE_ENABLED, false)
    }

    /**
     * Enables or disables demonstration recording mode.
     */
    fun setEnabled(enabled: Boolean) {
        preferences.edit()
            .putBoolean(KEY_DEMONSTRATION_MODE_ENABLED, enabled)
            .apply()
    }

    companion object {
        private const val KEY_DEMONSTRATION_MODE_ENABLED = "demonstration_mode_enabled"
    }
}
