    package com.example.minbeercellarapp

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Button
    import android.widget.TextView
    import androidx.navigation.findNavController
    import androidx.recyclerview.widget.DiffUtil
    import androidx.recyclerview.widget.ListAdapter
    import androidx.recyclerview.widget.RecyclerView

    class BeerAdapter(
        private val onDeleteClick: (Int) -> Unit,
        private val onEditClick: (Beer) -> Unit  // Adding an edit callback
    ) : ListAdapter<Beer, BeerAdapter.BeerViewHolder>(BeerDiffCallback()) {

        inner class BeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
            private val userTextView: TextView = itemView.findViewById(R.id.userTextView)
            private val breweryTextView: TextView = itemView.findViewById(R.id.breweryTextView)
            private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
            private val styleTextView: TextView = itemView.findViewById(R.id.styleTextView)
            private val abvTextView: TextView = itemView.findViewById(R.id.abvTextView)
            private val volumeTextView: TextView = itemView.findViewById(R.id.volumeTextView)
            private val pictureUrlTextView: TextView = itemView.findViewById(R.id.pictureUrlTextView)
            private val howManyTextView: TextView = itemView.findViewById(R.id.howManyTextView)
            private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
            private val editButton: Button = itemView.findViewById(R.id.editButton)

            init {
                deleteButton.setOnClickListener {
                    val beer = getItem(adapterPosition)
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onDeleteClick(beer.id)  // Using the beer's actual ID for deletion
                    }
                }
                editButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onEditClick(getItem(position))
                    }
                }
            }

            fun bind(beer: Beer) {
                idTextView.text = "ID: ${beer.id}"
                userTextView.text = "User: ${beer.user}"
                breweryTextView.text = "Brewery: ${beer.brewery}"
                nameTextView.text = "Name: ${beer.name}"
                styleTextView.text = "Style: ${beer.style}"
                abvTextView.text = "ABV: ${beer.abv}"
                volumeTextView.text = beer.volume?.toString() ?: "N/A"
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
        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean = oldItem == newItem
    }