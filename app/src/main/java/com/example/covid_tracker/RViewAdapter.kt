package com.example.covid_tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stateitems.view.*

class RViewAdapter(val list:List<StateModel>) : RecyclerView.Adapter<RViewAdapter.RViewHolder>() {
     class RViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.stateitems,parent,false)
        return RViewHolder(view)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.itemView.apply {
            val stateData=list[position]

            stateName.text = stateData.state
            Statecases.text = stateData.cases.toString()
            StateRecovered.text = stateData.recovered.toString()
            StatedeathsWorld.text = stateData.deaths.toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}