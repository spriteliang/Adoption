package com.leo.adoption.ui.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.leo.adoption.R
import com.leo.adoption.adapter.AdoptionAdapter
import com.leo.adoption.adapter.CategoryAdapter
import com.leo.adoption.databinding.FragmentHomeBinding
import com.leo.adoption.pojo.Adoption
import com.leo.adoption.pojo.Category
import com.leo.adoption.ui.activities.AdoptionActivity
import com.leo.adoption.ui.activities.AdoptionStepActivity
import com.leo.adoption.ui.activities.MainActivity
import com.leo.adoption.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private val TAG: String? = HomeFragment::class.java.simpleName
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adoptionAdapter: AdoptionAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var animalKind: String = "New"

//    companion object {
//        const val ADOPTION_ID = "com.leo.adoption.ui.fragments.animal_id"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        homeMvvm = (activity as MainActivity).viewModel
        adoptionAdapter = AdoptionAdapter()
        categoryAdapter = CategoryAdapter()
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

        prepareAdoptionRecyclerView()
        prepareCategoryRecyclerView()
        observeAdoptionItemsLiveData(animalKind)
        adoptionStepClick()
        homeMvvm.getAdoption()
        adoptionClick()
        categoryClick()
    }

    //點選adoption會傳id至AdoptionActivity
    private fun adoptionClick() {
        adoptionAdapter.onItemClick = {
            val intent = Intent(activity, AdoptionActivity::class.java)
            intent.putExtra("ADOPTION_ID", it.animal_id.toString())
            startActivity(intent)
        }
    }
    private fun categoryClick(){
        categoryAdapter.onItemClick={
            observeAdoptionItemsLiveData(it.name)
        }
    }

    private fun prepareCategoryRecyclerView() {
        var categoryList = ArrayList<Category>()
        categoryList.add(Category((R.drawable.ic_new), "New"))
        categoryList.add(Category((R.drawable.ic_dog), "狗"))
        categoryList.add(Category((R.drawable.ic_cat), "貓"))
        categoryAdapter.setCategoryList(categoryList)
        binding.recyCategory.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun adoptionStepClick() {
        Glide.with(this@HomeFragment)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7tybD0QZK4ewrAD6ZJz0lUTtNxXjU0Fp9Ag&usqp=CAU")
            .into(binding.imgRandomAdoption)
        binding.imgRandomAdoption.setOnClickListener {
            startActivity(Intent(activity, AdoptionStepActivity::class.java))
        }
    }


    private fun prepareAdoptionRecyclerView() {
        binding.recyAdoption.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = adoptionAdapter
        }
    }

    //把adoptionList傳給adoptionAdapter讓recycler讀
    private fun observeAdoptionItemsLiveData(animalKind: String) {
        homeMvvm.observeAdoptionItemLivedata().observe(
            viewLifecycleOwner
        ) { adoptionList ->
            if (animalKind == "New") {
                adoptionAdapter.setAdoptions(adoptionsList = adoptionList as ArrayList<Adoption>)
            } else {
                var adoptionListDog = adoptionList.filter { it.animal_kind == animalKind }
                adoptionAdapter.setAdoptions(adoptionsList = adoptionListDog as ArrayList<Adoption>)
            }
        }
    }
}
