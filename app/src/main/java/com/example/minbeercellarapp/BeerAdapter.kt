package com.example.minbeercellarapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BeerAdapter : ListAdapter<Beer, BeerAdapter.BeerViewHolder>(BeerDiffCallback()) {

    class BeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val userTextView: TextView = itemView.findViewById(R.id.userTextView)
        val breweryTextView: TextView = itemView.findViewById(R.id.breweryTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val styleTextView: TextView = itemView.findViewById(R.id.styleTextView)
        val abvTextView: TextView = itemView.findViewById(R.id.abvTextView)
        val volumeTextView: TextView = itemView.findViewById(R.id.volumeTextView)
        val pictureUrlTextView: TextView = itemView.findViewById(R.id.pictureUrlTextView)
        val howManyTextView: TextView = itemView.findViewById(R.id.howManyTextView)

        fun bind(beer: Beer) {
            idTextView.text = "ID: ${beer.id}"
            userTextView.text = "User: ${beer.user}"
            breweryTextView.text = "Brewery: ${beer.brewery}"
            nameTextView.text = "Name: ${beer.name}"
            styleTextView.text = "Style: ${beer.style}"
            abvTextView.text = "ABV: ${beer.abv}"
            volumeTextView.text = "Volume: ${beer.volume?.toString() ?: "N/A"}"
            pictureUrlTextView.text = beer.pictureUrl ?: "No Image"
            howManyTextView.text = "Quantity: ${beer.howMany}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false)
        return BeerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = getItem(position)
        holder.bind(beer)
    }
}

class BeerDiffCallback : DiffUtil.ItemCallback<Beer>() {
    override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem == newItem
    }
}