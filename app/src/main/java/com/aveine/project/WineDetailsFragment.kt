package com.aveine.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WineDetailsFragment : Fragment() {

    companion object {
        fun newInstance(): WineDetailsFragment = WineDetailsFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            parent: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_wine_detail, parent, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        val wineList = view.findViewById<RecyclerView>(R.id.list_wine)
        val data: WineClass? by lazy { arguments?.getParcelable("wine") as WineClass? }
        val wine = arguments?.getParcelable("wine") as WineClass?
        val oui = 1
    }
}