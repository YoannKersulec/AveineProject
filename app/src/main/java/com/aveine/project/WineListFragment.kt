package com.aveine.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.algolia.search.saas.AlgoliaException
import com.algolia.search.saas.Client
import com.algolia.search.saas.CompletionHandler
import com.algolia.search.saas.Query
import org.json.JSONObject


class WineListFragment : Fragment() {

    var adapter: WineListAdapter? = null
    var listener: FragmentActivity? = null
    var dataWine : ArrayList<WineClass> = ArrayList()

    companion object {
        fun newInstance(): WineListFragment = WineListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_wine_list, parent, false)
    }

    private fun init() {
        callForData("")
    }

    fun updateList(newList : ArrayList<WineClass>) {
        if (newList.size == 0) {
            this.activity?.findViewById<TextView>(R.id.no_wine_text)?.visibility = View.VISIBLE
        }
        dataWine = newList
        adapter?.updateData(dataWine)
        adapter?.notifyDataSetChanged()
    }

    fun callForData(designation: String) {
        val client = Client("K2D0OP514W", "b60053f6aaa8af7907113361b96ca52b")
        val index = client.getIndex("prod_wines")
        val completionHandler: CompletionHandler = object : CompletionHandler {
            override fun requestCompleted(content: JSONObject?, error: AlgoliaException?) {
                val result = content
                val hits = result?.getJSONArray("hits")
                val arrObj = arrayListOf<WineClass>()
                if (hits == null)
                    return
                for (i in 0 until hits.length()) {
                    arrObj.add(WineClass().createFromJson(hits.getString(i)))
                }
                updateList(arrObj)
            }
        }
        val query = Query(designation)
            .setPage(0)
            .setHitsPerPage(50);
        index.searchAsync(query, completionHandler);
    }


    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        init()
        val wineList = view.findViewById<RecyclerView>(R.id.list_wine)
        adapter = WineListAdapter(dataWine, requireContext())
        wineList?.adapter = adapter
        wineList?.layoutManager = LinearLayoutManager(listener)
    }

}