package com.leo.adoption.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.leo.adoption.adapter.AdoptionAdapter
import com.leo.adoption.databinding.FragmentHomeBinding
import com.leo.adoption.pojo.Adoption
import com.leo.adoption.ui.activities.AdoptionStepActivity
import com.leo.adoption.viewmodel.HomeViewModel
import kotlin.random.Random


class HomeFragment : Fragment() {
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adoptionItemAdapter: AdoptionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        adoptionItemAdapter = AdoptionAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsClick()
        adoptionStetClick()
        prepareAdoptionItemsRecyclerView()
        homeMvvm.getAdoption()
        observeAdoptionItemsLiveData()


    }

    private fun adoptionStetClick() {
        binding.imgRandomMeal.setOnClickListener {
            val intent = Intent(activity, AdoptionStepActivity::class.java)
            startActivity(intent)
        }
    }

    private fun itemsClick() {
        binding.categoryNew.setOnClickListener {
            homeMvvm.observeAdoptionItemLivedata().observe(
                viewLifecycleOwner
            ) { adoptionList ->
                adoptionItemAdapter.setAdoptions(adoptionList as ArrayList<Adoption>)
            }
            prepareAdoptionItemsRecyclerView()
            prepareAdoptionItemsRecyclerView()
        }
        binding.categoryDogs.setOnClickListener {
            homeMvvm.observeAdoptionItemLivedata().observe(
                viewLifecycleOwner
            ) { adoptionList ->
                var adoptionListDog = adoptionList.filter { it.animal_kind == "狗" }
                adoptionItemAdapter.setAdoptions(adoptionListDog as ArrayList<Adoption>)
            }
            prepareAdoptionItemsRecyclerView()
        }
        binding.categoryCat.setOnClickListener {
            homeMvvm.observeAdoptionItemLivedata().observe(
                viewLifecycleOwner
            ) { adoptionList ->
                var adoptionListCat = adoptionList.filter { it.animal_kind == "貓" }
                adoptionItemAdapter.setAdoptions(adoptionListCat as ArrayList<Adoption>)
            }
            prepareAdoptionItemsRecyclerView()
        }


    }


    private fun prepareAdoptionItemsRecyclerView() {
        binding.recyAdoption.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = adoptionItemAdapter
        }
    }
    private fun observeAdoptionItemsLiveData() {
        homeMvvm.observeAdoptionItemLivedata().observe(
            viewLifecycleOwner
        ) { adoptionList ->
            var random= Random.nextInt(100)
            Glide.with(this@HomeFragment)
                .load(adoptionList[random].album_file)
                .into(binding.imgRandomMeal)
            var ado = adoptionList.filter { it.animal_kind == "貓" }
            adoptionItemAdapter.setAdoptions(ado as ArrayList<Adoption>)
        }
    }
}
