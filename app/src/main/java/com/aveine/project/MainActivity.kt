package com.aveine.project

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.algolia.search.saas.AlgoliaException
import com.algolia.search.saas.Client
import com.algolia.search.saas.CompletionHandler
import com.algolia.search.saas.Query
import org.json.JSONObject
import retrofit2.Retrofit
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        loadFragment(WineListFragment.newInstance())
    }

    private fun init() {
        findViewById<ImageButton>(R.id.change_lang)?.setOnClickListener{
            changeLanguageRestart()
        }
        auth()
    }

    private fun auth() {
        CallController().start()

    }

    private fun loadFragment(fragment : Fragment) {
// create a FragmentManager
        var fm : FragmentManager = supportFragmentManager
// create a FragmentTransaction to begin the transaction and replace the Fragment
        var fragmentTransaction : FragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fragment_holder, fragment);
        fragmentTransaction.commit(); // save the changes
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