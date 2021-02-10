package com.aveine.project

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        findViewById<ImageButton>(R.id.change_lang)?.setOnClickListener{
            changeLanguageRestart()
        }
    }

    // Need to fix cause Deprecated but didn't find anything else except : https://stackoverflow.com/a/40704077
    private fun changeLanguageRestart() {
        if (resources.configuration.locale.toString() == "fr")
            setLocale(this, "en")
        else
            setLocale(this, "fr")
        restartActivity()
    }

    private fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun restartActivity() {
        val intent = intent
        finish()
        startActivity(intent)
    }
}