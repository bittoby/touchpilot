package dev.touchpilot.app.workflow

import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DemonstrationModeTest {

    private lateinit var preferences: FakeSharedPreferences
    private lateinit var demonstrationMode: DemonstrationMode

    @Before
    fun setup() {
        preferences = FakeSharedPreferences()
        demonstrationMode = DemonstrationMode(preferences)
    }

    @Test
    fun defaultStateIsDisabled() {
        assertFalse(demonstrationMode.isEnabled())
    }

    @Test
    fun setEnabledTurnsOnDemonstrationMode() {
        demonstrationMode.setEnabled(true)
        assertTrue(demonstrationMode.isEnabled())
    }

    @Test
    fun setEnabledTurnsOffDemonstrationMode() {
        demonstrationMode.setEnabled(true)
        demonstrationMode.setEnabled(false)
        assertFalse(demonstrationMode.isEnabled())
    }

    @Test
    fun statePersistedInSharedPreferences() {
        demonstrationMode.setEnabled(true)
        assertTrue(preferences.getBoolean("demonstration_mode_enabled", false))
    }

    @Test
    fun stateRestoredFromSharedPreferences() {
        preferences.edit().putBoolean("demonstration_mode_enabled", true).apply()
        val restored = DemonstrationMode(preferences)
        assertTrue(restored.isEnabled())
    }

    @Test
    fun multipleToggles() {
        demonstrationMode.setEnabled(true)
        assertTrue(demonstrationMode.isEnabled())
        
        demonstrationMode.setEnabled(false)
        assertFalse(demonstrationMode.isEnabled())
        
        demonstrationMode.setEnabled(true)
        assertTrue(demonstrationMode.isEnabled())
    }

    /**
     * Minimal in-memory SharedPreferences implementation for testing.
     */
    private class FakeSharedPreferences : SharedPreferences {
        private val data = mutableMapOf<String, Any?>()

        override fun getBoolean(key: String, defValue: Boolean): Boolean {
            return data[key] as? Boolean ?: defValue
        }

        override fun edit(): SharedPreferences.Editor {
            return FakeEditor(data)
        }

        // Unused SharedPreferences methods
        override fun getAll(): Map<String, *> = data
        override fun getString(key: String, defValue: String?): String? = data[key] as? String ?: defValue
        override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? = data[key] as? Set<String> ?: defValues
        override fun getInt(key: String, defValue: Int): Int = data[key] as? Int ?: defValue
        override fun getLong(key: String, defValue: Long): Long = data[key] as? Long ?: defValue
        override fun getFloat(key: String, defValue: Float): Float = data[key] as? Float ?: defValue
        override fun contains(key: String): Boolean = data.containsKey(key)
        override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) = Unit
        override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) = Unit

        private class FakeEditor(private val data: MutableMap<String, Any?>) : SharedPreferences.Editor {
            private val pending = mutableMapOf<String, Any?>()

            override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
                pending[key] = value
                return this
            }

            override fun apply() {
                data.putAll(pending)
                pending.clear()
            }

            override fun commit(): Boolean {
                data.putAll(pending)
                pending.clear()
                return true
            }

            // Unused Editor methods
            override fun putString(key: String, value: String?): SharedPreferences.Editor = this
            override fun putStringSet(key: String, values: Set<String>?): SharedPreferences.Editor = this
            override fun putInt(key: String, value: Int): SharedPreferences.Editor = this
            override fun putLong(key: String, value: Long): SharedPreferences.Editor = this
            override fun putFloat(key: String, value: Float): SharedPreferences.Editor = this
            override fun remove(key: String): SharedPreferences.Editor = this
            override fun clear(): SharedPreferences.Editor = this
        }
    }
}
