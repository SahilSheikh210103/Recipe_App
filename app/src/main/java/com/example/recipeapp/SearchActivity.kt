package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var recipes: List<Recipe?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchEditTextSec.requestFocus()
        var db = Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries().fallbackToDestructiveMigration().createFromAsset("recipe.db")
            .build()

        var daoObj = db.getDao()

        recipes = daoObj.getAll()!!

        setUpRecyclerView()

        binding.searchEditTextSec.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (charSequence.toString() != "") {
                    filterData(charSequence.toString())
                }else{
                    setUpRecyclerView()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.goBackImg.setOnClickListener {
            finish()
        }

    }

    private fun filterData(filterText: String) {

        var filterData = ArrayList<Recipe>()
        for (i in recipes.indices) {
            if (recipes[i]!!.tittle.lowercase().contains(filterText.lowercase())) {
                filterData.add(recipes[i]!!)
            }
            searchAdapter.filterList(filterList = filterData)
        }

    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.rvSearch.layoutManager = LinearLayoutManager(this)

        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains("Popular")) {
                dataList.add(recipes[i]!!)
            }

            searchAdapter = SearchAdapter(dataList, this)
            binding.rvSearch.adapter = searchAdapter
        }

    }
}