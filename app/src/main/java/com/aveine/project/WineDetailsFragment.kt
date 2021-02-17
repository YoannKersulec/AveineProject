package com.aveine.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.squareup.picasso.Picasso
import kotlin.reflect.KProperty1

class WineDetailsFragment : Fragment() {

    var listener: FragmentActivity? = null

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
        val data: WineClass? by lazy { arguments?.getParcelable("wine") as WineClass? }
        val wine = arguments?.getParcelable("wine") as WineClass?
        init(view, wine)
        val oui = 1
    }

    private fun init(view: View, wine: WineClass?) {
        if (wine == null)
            return
        val designation = view.findViewById<TextView>(R.id.designation_text)
        designation.text = wine.designation
        val color = view.findViewById<TextView>(R.id.color_text)
        val lang = resources.configuration.locale.toString()
        val country = view.findViewById<TextView>(R.id.country_text)
        val region = view.findViewById<TextView>(R.id.region_text)
        if (lang.contains("en")) {
            color.text = wine.color?.en
            country.text = wine.country?.name?.en
            region.text = wine.region?.name?.en
        } else {
            color.text = wine.color?.fr
            country.text = wine.country?.name?.fr
            region.text = wine.region?.name?.fr
        }
        val label = view.findViewById<ImageView>(R.id.label_detail)
        Picasso.get().load(wine.wine_label).into(label)
        val aeration = view.findViewById<TextView>(R.id.aeration_text)
        aeration.text = wine.recommended_aeration.toString()
        view.findViewById<ImageButton>(R.id.return_btn).setOnClickListener {
            (activity as MainActivity?)?.changeFragment()
        }
        val serv_text = "${this.context?.getString(R.string.serv_temp)} ${wine.serving_temperature_min} - ${wine.serving_temperature_max} degree"
        view.findViewById<TextView>(R.id.serv_temp_text).text = serv_text


    }
}