package com.aveine.project

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() , ClickEventHandler {

    var callController : CallController? = null
    var wineListFragment : WineListFragment = WineListFragment.newInstance()
    var wineDetailsFragment : WineDetailsFragment = WineDetailsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        loadFragment(wineListFragment)
    }

    private fun init() {
        findViewById<ImageButton>(R.id.change_lang)?.setOnClickListener{
            changeLanguageRestart()
        }
        findViewById<ImageButton>(R.id.favorite_btn)?.setOnClickListener {
            getFavorite()
        }
        val label_text : EditText = findViewById(R.id.label_search)
        label_text.setOnEditorActionListener { v, keyCode, event ->
            val handled = false
//            Toast.makeText(this, event.keyCode, Toast.LENGTH_SHORT).show()
            if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                    keyCode == EditorInfo.IME_ACTION_DONE ||
                    event.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER) {
                        wineListFragment.callForData(label_text.text.toString())
                label_text.setText("")
                label_text.clearFocus()
                hideKeyboard(this)
                //Perform your Actions here.
            }
            handled
        }
        auth()
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun auth() {
        callController = CallController()
        callController?.start()
    }

    fun getFavorite() {
        val call: Call<FavoriteWineListClass>? = callController?. aveineService?.getFavorites("Bearer " + callController?.token)
        call?.enqueue(object : Callback<FavoriteWineListClass> {
            override fun onResponse(call: Call<FavoriteWineListClass>, response: Response<FavoriteWineListClass>) {
                val allCourse = response.body()
            }

            override fun onFailure(call: Call<FavoriteWineListClass>, t: Throwable) {
                val ouoi = 1
            }
        })
    }

    override fun forwardClick(element: WineClass?) {
        val test = element
        val oui = 1

        val bundle = Bundle()
        bundle.putParcelable("wine", element)

        val transaction = this.supportFragmentManager.beginTransaction()
        wineDetailsFragment.arguments = bundle

        transaction.replace(R.id.fragment_holder, wineDetailsFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    private fun loadFragment(fragment: Fragment) {
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