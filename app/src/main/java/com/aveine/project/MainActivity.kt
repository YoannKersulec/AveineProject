package com.aveine.project

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
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
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() , ClickEventHandler {

    var wineListFragment : WineListFragment = WineListFragment.newInstance()
    var wineDetailsFragment : WineDetailsFragment = WineDetailsFragment.newInstance()
    var current : Fragment? = null
    var callController : CallController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        loadFragment(wineListFragment)
    }

    override fun onRestart() {
        super.onRestart()
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
            if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                    keyCode == EditorInfo.IME_ACTION_DONE ||
                    event.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER) {
                        wineListFragment.callForData(label_text.text.toString())
                label_text.setText("")
                label_text.clearFocus()
                hideKeyboard(this)
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

    fun getInfoWine(id : String?) {
        if (id == null)
            return
        val call: Call<WineGetClass>? = callController?.aveineService?.getInfoWine("Bearer " + callController?.token, id = id)
        call?.enqueue(object : Callback<WineGetClass> {
            override fun onResponse(
                call: Call<WineGetClass>,
                response: Response<WineGetClass>
            ) {
                val data = response.body()?.data
                val attributes = data?.attributes
                val newWine = response.body()?.createWineClass() ?: return
                wineListFragment.addToList(newWine)
            }

            override fun onFailure(call: Call<WineGetClass>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Can't get info", Toast.LENGTH_LONG).show()
            }
        })

    }

    fun getFavorite() {
        val call: Call<FavoriteWineListClass>? = callController?.aveineService?.getFavorites("Bearer " + callController?.token)
        call?.enqueue(object : Callback<FavoriteWineListClass> {
            override fun onResponse(
                call: Call<FavoriteWineListClass>,
                response: Response<FavoriteWineListClass>
            ) {
                val allfav = response.body()?.data
                wineListFragment.dataWine.clear()
                for (i in 0 until (allfav?.size ?: 0)) {
                    getInfoWine(allfav?.get(i)?.relationships?.wine?.data?.id)
                }
            }

            override fun onFailure(call: Call<FavoriteWineListClass>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Can't get favorites", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun addToFavorite(wineClass: WineClass) {
        val favoriteAddWineClass = FavoriteAddWineClass(data =
            FavoriteWineClass(type = "favorites", relationships =
                RelationShipClass(wine = BasicWineClass(data = BasicInfoClass(id = wineClass.id, type = "wines")))
            )
        )
        val call: Call<FavoriteAddWineClass>? =
            callController?.aveineService?.addFavorite("Bearer " + callController?.token, favoriteAddWineClass)
        call?.enqueue(object : Callback<FavoriteAddWineClass> {
            override fun onResponse(
                call: Call<FavoriteAddWineClass>,
                response: Response<FavoriteAddWineClass>
            ) {
                val allfav = response.body()?.data
                Toast.makeText(this@MainActivity, getString(R.string.add_fav_success), Toast.LENGTH_SHORT).show()
                //wineListFragment.addToList(favorites)
            }

            override fun onFailure(call: Call<FavoriteAddWineClass>, t: Throwable) {
                Toast.makeText(this@MainActivity, getString(R.string.add_fav_fail), Toast.LENGTH_SHORT).show()            }
        })
    }

    override fun forwardClick(element: WineClass?) {
        setVisibility(wineDetailsFragment)
        loadFragmentWithArgument(wineDetailsFragment, element)
    }

    fun setVisibility(fragment: Fragment?) {
        if (fragment == wineDetailsFragment) {
            findViewById<ImageButton>(R.id.favorite_btn).visibility = View.GONE
            findViewById<EditText>(R.id.label_search).visibility = View.GONE
        }
        else {
            findViewById<ImageButton>(R.id.favorite_btn).visibility = View.VISIBLE
            findViewById<EditText>(R.id.label_search).visibility = View.VISIBLE
        }

    }

    fun changeFragment() {
        if (current == wineDetailsFragment) {
            setVisibility(wineListFragment)
            loadFragment(wineListFragment)
        }
        else {
            setVisibility(wineDetailsFragment)
            loadFragment(wineDetailsFragment)
        }
    }

    private fun loadFragment(fragment: Fragment) {
// create a FragmentManager
        var fm : FragmentManager = supportFragmentManager
// create a FragmentTransaction to begin the transaction and replace the Fragment
        var fragmentTransaction : FragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fragment_holder, fragment);
        fragmentTransaction.commit(); // save the changes
        current = fragment
    }

    private fun loadFragmentWithArgument(fragment: Fragment, arg: WineClass?) {
        val bundle = Bundle()
        bundle.putParcelable("wine", arg)
        val transaction = this.supportFragmentManager.beginTransaction()
        wineDetailsFragment.arguments = bundle
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
        current = fragment
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