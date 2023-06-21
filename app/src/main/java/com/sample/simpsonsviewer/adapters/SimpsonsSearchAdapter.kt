package com.sample.simpsonsviewer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sample.databinding.ItemSimpsonsSearchBinding
import com.sample.simpsonsviewer.models.RelatedTopic
import com.sample.simpsonsviewer.ui.list.Listener

class SimpsonsSearchAdapter(val listener: Listener): RecyclerView.Adapter<SimpsonsSearchAdapter.ViewHolder>(),
    Filterable {
    var results: List<RelatedTopic> = emptyList()
        set(value) {
            field = value
            resultsFiltered = value
            notifyDataSetChanged()
        }

    var resultsFiltered: List<RelatedTopic> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSimpsonsSearchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(resultsFiltered[position])

    override fun getItemCount() = resultsFiltered.size

    inner class ViewHolder(private val binding: ItemSimpsonsSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: RelatedTopic) {
            binding.topic = topic
            binding.root.setOnClickListener {
                listener.onTopicSelected(topic)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                resultsFiltered = if (charString.isEmpty()) {
                    results
                } else {
                    val filteredList = ArrayList<RelatedTopic>()
                    results
                        .filter {
                            it.text?.contains(constraint!!) == true
                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = resultsFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                resultsFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<RelatedTopic>
                //notifyDataSetChanged()
            }
        }
    }
}