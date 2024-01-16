package com.leo.adoption.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.leo.adoption.adapter.FavoriteAdapter
import com.leo.adoption.databinding.FragmentSearchBinding
import com.leo.adoption.ui.activities.AdoptionActivity
import com.leo.adoption.ui.activities.MainActivity
import com.leo.adoption.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerviewAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        binding.imgSearchArrow.setOnClickListener {
            searchAdoptions()
        }
        observeSearchedMealsLiveData()
        adoptionClick()

        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(200)
                viewModel.searchAdoption(searchQuery.toString())
            }
        }

    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData()
            .observe(viewLifecycleOwner, Observer { adoptionList ->
                searchRecyclerviewAdapter.differ.submitList(adoptionList)
            })
    }

    private fun searchAdoptions() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.searchAdoption(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerviewAdapter = FavoriteAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchRecyclerviewAdapter
        }
    }

    private fun adoptionClick() {
        searchRecyclerviewAdapter.onItemClick = {
            val intent = Intent(activity, AdoptionActivity::class.java)
            intent.putExtra("ADOPTION_ID", it.animal_id.toString())
            startActivity(intent)
        }
    }
}