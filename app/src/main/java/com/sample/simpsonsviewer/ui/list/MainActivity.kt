package com.sample.simpsonsviewer.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.databinding.ActivityMainBinding
import com.sample.simpsonsviewer.adapters.SimpsonsSearchAdapter
import com.sample.simpsonsviewer.models.RelatedTopic
import com.sample.simpsonsviewer.ui.details.DetailsActivity
import com.sample.simpsonsviewer.util.EqualSpacingItemDecorator
import com.sample.simpsonsviewer.util.state.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Listener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val adapter: SimpsonsSearchAdapter by lazy {
        SimpsonsSearchAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.simpsonsSearchLiveData.observe(this) {
            when (it) {
                State.Loading -> {
                    binding.progressBar.show()
                }
                is State.Success -> {
                    binding.progressBar.hide()
                    updateRecyclerView(it.value.relatedTopics)
                }
                is State.Error -> {
                    binding.progressBar.hide()
                }
            }
        }

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        viewModel.search()

        setupBinding()
    }

    private fun setupBinding() {
        binding.progressBar.hide()
        val dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16f, resources.displayMetrics).toInt()
        val recyclerView = binding.simpsonsSearchRecyclerView

        recyclerView.addItemDecoration(EqualSpacingItemDecorator(dp))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            adapter.filter.filter(text)
            recyclerView.post { adapter.notifyDataSetChanged() }
        }
    }

    private fun updateRecyclerView(topics: List<RelatedTopic>) {
        binding.simpsonsSearchRecyclerView.isVisible = topics.isNotEmpty()
        adapter.results = topics
    }

    override fun onTopicSelected(topic: RelatedTopic) {
        startActivity(Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.TOPIC, topic.firstURL)
        })
    }
}

interface Listener {
    fun onTopicSelected(topic: RelatedTopic)
}