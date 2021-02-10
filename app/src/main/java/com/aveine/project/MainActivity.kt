package com.aveine.project

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        var btn = findViewById<Button>(R.id.button_test)
        btn.setOnClickListener{
            Toast.makeText(this, resources.configuration.locale.toString(), Toast.LENGTH_LONG).show()
            setLocale(this, "fr")
            restartActivity()
        }
    }

    fun setLocale(activity: Activity, languageCode: String) {
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