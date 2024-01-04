package com.leo.adoption.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.leo.adoption.R
import com.leo.adoption.adapter.CategoryAdapter
import com.leo.adoption.data.DataCategories
import com.leo.adoption.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private var dataList = mutableListOf<DataCategories>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryAdapter=CategoryAdapter()
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            dataList.add(DataCategories("New", R.drawable.ic_home))
            dataList.add(DataCategories("Dogs", R.drawable.ic_home))
            dataList.add(DataCategories("Cats", R.drawable.ic_home))
            categoryAdapter.setDataList(dataList)
            adapter = categoryAdapter

        }

    }
}