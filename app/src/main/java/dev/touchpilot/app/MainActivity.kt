package dev.touchpilot.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import dev.touchpilot.app.androidcontrol.AccessibilityBridge

class MainActivity : Activity() {
    private lateinit var statusView: TextView
    private lateinit var logView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 56, 40, 40)
        }

        val titleView = TextView(this).apply {
            text = "TouchPilot"
            textSize = 30f
        }

        statusView = TextView(this).apply {
            textSize = 16f
            setPadding(0, 24, 0, 24)
        }

        val enableButton = Button(this).apply {
            text = "Open Accessibility Settings"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }

        val observeButton = Button(this).apply {
            text = "Observe Current Screen"
            setOnClickListener {
                refreshStatus()
                logView.text = AccessibilityBridge.observeScreen()
            }
        }

        val targetInput = EditText(this).apply {
            hint = "Visible text to tap"
            setSingleLine(true)
        }

        val tapButton = Button(this).apply {
            text = "Tap Text"
            setOnClickListener {
                val target = targetInput.text.toString()
                val ok = AccessibilityBridge.tapByText(target)
                refreshStatus()
                logView.text = "tapByText(\"$target\") -> $ok"
            }
        }

        val typeInput = EditText(this).apply {
            hint = "Text to type into focused field"
            setSingleLine(true)
        }

        val typeButton = Button(this).apply {
            text = "Type Into Focused Field"
            setOnClickListener {
                val value = typeInput.text.toString()
                val ok = AccessibilityBridge.typeIntoFocusedField(value)
                refreshStatus()
                logView.text = "typeIntoFocusedField(...) -> $ok"
            }
        }

        val actionRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        val backButton = Button(this).apply {
            text = "Back"
            setOnClickListener {
                val ok = AccessibilityBridge.pressBack()
                refreshStatus()
                logView.text = "pressBack() -> $ok"
            }
        }

        val homeButton = Button(this).apply {
            text = "Home"
            setOnClickListener {
                val ok = AccessibilityBridge.pressHome()
                refreshStatus()
                logView.text = "pressHome() -> $ok"
            }
        }

        actionRow.addView(backButton, rowButtonParams())
        actionRow.addView(homeButton, rowButtonParams())

        logView = TextView(this).apply {
            text = "Enable TouchPilot Control, then observe a screen."
            textSize = 13f
            setPadding(0, 24, 0, 0)
        }

        root.addView(titleView)
        root.addView(statusView)
        root.addView(enableButton)
        root.addView(observeButton)
        root.addView(targetInput)
        root.addView(tapButton)
        root.addView(typeInput)
        root.addView(typeButton)
        root.addView(actionRow)
        root.addView(logView)

        setContentView(ScrollView(this).apply {
            addView(root)
        })

        refreshStatus()
    }

    override fun onResume() {
        super.onResume()
        refreshStatus()
    }

    private fun refreshStatus() {
        statusView.text = if (AccessibilityBridge.isConnected()) {
            "Accessibility service: connected"
        } else {
            "Accessibility service: not connected"
        }
    }

    private fun rowButtonParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1f
        )
    }
}
