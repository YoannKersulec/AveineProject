package com.aveine.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class WineListAdapter(private var wineList : ArrayList<WineClass>, context: Context) : RecyclerView.Adapter<WineListAdapter.ViewHolder>() {

    private val clickHandler : ClickEventHandler = context as ClickEventHandler

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
        }
        fun bind(element : WineClass?) {
            itemView.setOnClickListener {
                clickHandler.forwardClick(element)
            }
            itemView.findViewById<TextView>(R.id.vintage_wine_element).text = element?.vintage.toString()
            itemView.findViewById<TextView>(R.id.designation_wine_element).text = element?.designation
            Picasso.get().load(element?.wine_label).into(itemView.findViewById<ImageView>(R.id.label_wine_element))
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_element_list, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(wineList[i])

    }

    override fun getItemCount(): Int {
        return wineList.size
    }

    fun updateData(list : ArrayList<WineClass>) {
        wineList = list
    }
}