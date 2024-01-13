package com.leo.adoption.ui.activities

import android.R
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getFont
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.leo.adoption.adapter.AdoptionItemAdapter
import com.leo.adoption.databinding.ActivityAdoptionBinding
import com.leo.adoption.db.AdoptionDatabase
import com.leo.adoption.pojo.Adoption
import com.leo.adoption.retrofit.RetrofitInstance
import com.leo.adoption.ui.fragments.HomeFragment
import com.leo.adoption.viewmodel.AdoptionViewModel
import com.leo.adoption.viewmodel.AdoptionViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdoptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdoptionBinding
    private lateinit var adoptionId: String
    private lateinit var adoptionKind: String
    private lateinit var adoptionImage: String
    private var adoptionItemAdapter = AdoptionItemAdapter()
    private var adoptionList = ArrayList<Adoption>()
    private lateinit var adoptionMvvm: AdoptionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdoptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adoptionDatabase = AdoptionDatabase.getInstance(this)
        val viewModelFactory = AdoptionViewModelFactory(adoptionDatabase)
        adoptionMvvm = ViewModelProvider(this, viewModelFactory)[AdoptionViewModel::class.java]

        getAdoptionInformationFromIntent()
        getRetrofitAdoptionList()
        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.btnSave.setOnClickListener {
            adoptionToSave?.let {
                adoptionMvvm.insertAdoption(it)
                Toast.makeText(this, "Adoption Save", Toast.LENGTH_LONG).show()
            }
        }
    }

    private var adoptionToSave: Adoption? = null


    private fun prepareAdoptionItemRecycler(adoptionList: ArrayList<Adoption>) {
        adoptionToSave = adoptionList[0]
        var nameList = arrayListOf("Kind", "Age", "Sex", "Size", "Color")
        val detailList = arrayListOf(
            adoptionList[0].animal_kind,
            adoptionList[0].animal_age,
            adoptionList[0].animal_sex,
            adoptionList[0].animal_bodytype,
            adoptionList[0].animal_colour
        )
        adoptionItemAdapter.setNameList(nameList)
        adoptionItemAdapter.setDetailList(detailList)
        Log.d("Tag", "prepareAdoptionItemRecycler:detailsize ${detailList.size}")
        Log.d("test", "prepareAdoptionItemRecycler:namesize ${nameList.size}")
        binding.recyAdoptionItem.apply {
            layoutManager =
                LinearLayoutManager(this@AdoptionActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = adoptionItemAdapter
        }
    }

    private fun setInformationInViews(adoptionList: ArrayList<Adoption>) {
        Glide.with(applicationContext).load(adoptionImage).into(binding.imgAdoptionDetail)
        binding.collapsingToolbar.apply {
            title = adoptionKind
            val typeface: Typeface? =
                getFont(this@AdoptionActivity, com.leo.adoption.R.font.pingfang)
            setCollapsedTitleTypeface(typeface)
            setExpandedTitleTypeface(typeface)
        }
        binding.collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.apply {
            adoptionDate.text = adoptionList[0].cDate
            adoptionStatus.text = adoptionList[0].animal_status
            adoptionSubid.text = adoptionList[0].animal_subid
            adoptionTel.text = adoptionList[0].shelter_tel
            shelterName.text = adoptionList[0].shelter_name
            shelterAddress.text = adoptionList[0].shelter_address
        }

    }

    private fun getAdoptionInformationFromIntent() {
        adoptionId = intent.getStringExtra(HomeFragment.ADOPTION_ID)!!
        Log.d("TAG", "getAdoptionInformationFromIntent: $adoptionId")
        adoptionKind = intent.getStringExtra(HomeFragment.ADOPTION_VARIETY)!!
        adoptionImage = intent.getStringExtra(HomeFragment.ADOPTION_IMAGE)!!
    }

    private fun getRetrofitAdoptionList() {
        RetrofitInstance.api.getAdoptions("QcbUEzN6E6DL", 1)
            .enqueue(object : Callback<List<Adoption>> {
                override fun onResponse(
                    call: Call<List<Adoption>>,
                    response: Response<List<Adoption>>
                ) {
                    if (response.body() != null) {
                        var adoptionDetailList =
                            response.body()!!.filter { it.animal_id.toString() == adoptionId }
                        adoptionList = adoptionDetailList as ArrayList<Adoption>
                        prepareAdoptionItemRecycler(adoptionList)
                        setInformationInViews(adoptionList)
                    }
                }

                override fun onFailure(call: Call<List<Adoption>>, t: Throwable) {
                    Log.d("TAG", "onFailure: ${t.message.toString()}")
                }
            })
    }

}