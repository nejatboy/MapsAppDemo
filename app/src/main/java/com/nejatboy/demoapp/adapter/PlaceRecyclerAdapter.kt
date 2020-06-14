package com.nejatboy.demoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nejatboy.demoapp.R
import com.nejatboy.demoapp.model.Place
import com.nejatboy.demoapp.util.dowloadFromUrl
import com.nejatboy.demoapp.view.SearchFragmentDirections
import kotlinx.android.synthetic.main.cell_design_place.view.*

class PlaceRecyclerAdapter (val places: ArrayList<Place>) : RecyclerView.Adapter<PlaceRecyclerAdapter.CellDesign>(){


    class CellDesign(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellDesign {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_design_place, parent, false)
        return CellDesign(view)
    }


    override fun getItemCount(): Int {
        return places.count()
    }


    override fun onBindViewHolder(holder: CellDesign, position: Int) {
        val place = places[position]

        holder.itemView.textViewName.text = place.name
        holder.itemView.textViewVicinity.text = place.vicinity
        holder.itemView.ratingBar.rating = place.rating.toFloat()
        holder.itemView.textViewUserRatingsTotal.text = "Oylayan: ${place.user_ratings_total}"
        holder.itemView.imageViewIcon.dowloadFromUrl(place.icon)

        holder.itemView.setOnClickListener {

            val action = SearchFragmentDirections.actionSearchFragmentToMapsFragment(place)
            Navigation.findNavController(it).navigate(action)
        }
    }
}