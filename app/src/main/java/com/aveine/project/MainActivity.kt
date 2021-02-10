package com.aveine.project

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.algolia.search.saas.AlgoliaException
import com.algolia.search.saas.Client
import com.algolia.search.saas.CompletionHandler
import com.algolia.search.saas.Query
import org.json.JSONObject
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
/*        val client = ClientSearch(
                applicationID = ApplicationID("K2D0OP514W"),
                apiKey = APIKey("b60053f6aaa8af7907113361b96ca52b")
        )*/



/*        GlobalScope.launch {
            val indices = client.listIndices()
            println(indices)
            val oui = indices
        }*/
//        backgroundTest(index)






        val client = Client("K2D0OP514W", "b60053f6aaa8af7907113361b96ca52b")
        val index = client.getIndex("prod_wines")
        val completionHandler: CompletionHandler = object : CompletionHandler {
            override fun requestCompleted(content: JSONObject?, error: AlgoliaException?) {
                // [...]
                val result = content
            }
            val out = "out"
        }
//        val response = index.search()

//        val contacts: List<WineClass> = response.hits.deserialize(WineClass.serializer())
//        index.getObject(ObjectID("vine"))
        val query = Query("")
//            .setAttributesToRetrieve("first", "lastname")
            .setHitsPerPage(50);
        index.searchAsync(query, completionHandler);
//        index.getSettingsAsync(completionHandler)*/

//        val indexName = IndexName("prod_wines")
 //       val index: ResponseFields.Index = client.getIndex("your_index_name")

//        response.hits.deserialize(Contact.serializer())
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