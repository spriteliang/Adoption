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
import com.leo.adoption.ui.activities.AdoptionActivity
import com.leo.adoption.ui.activities.AdoptionStepActivity
import com.leo.adoption.ui.activities.MainActivity
import com.leo.adoption.viewmodel.HomeViewModel
import kotlin.random.Random


class HomeFragment : Fragment() {
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adoptionItemAdapter: AdoptionAdapter

    companion object {
        const val ADOPTION_ID = "com.leo.adoption.ui.fragments.animal_id"
        const val ADOPTION_VARIETY = "com.leo.adoption.ui.fragments.animal_variety"
        const val ADOPTION_IMAGE = "com.leo.adoption.ui.fragments.album_file"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        homeMvvm = (activity as MainActivity).viewModel
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
        adoptionStepClick()
        prepareAdoptionItemsRecyclerView()
        homeMvvm.getAdoption()
        observeAdoptionItemsLiveData()
        adoptionClick()


    }

    private fun adoptionClick() {
        adoptionItemAdapter.onItemClick = {
            val intent = Intent(activity, AdoptionActivity::class.java)
            intent.putExtra(ADOPTION_ID, it.animal_id.toString())
            intent.putExtra(ADOPTION_VARIETY, it.animal_Variety)
            intent.putExtra(ADOPTION_IMAGE, it.album_file)
            startActivity(intent)
        }
    }

    private fun adoptionStepClick() {
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
            var random = Random.nextInt(100)
            Glide.with(this@HomeFragment)
                .load(adoptionList[random].album_file)
                .into(binding.imgRandomMeal)
            var ado = adoptionList.filter { it.animal_kind == "貓" }
            adoptionItemAdapter.setAdoptions(ado as ArrayList<Adoption>)
        }
    }
}
