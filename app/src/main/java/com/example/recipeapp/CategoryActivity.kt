package com.example.recipeapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryList: ArrayList<Recipe>
    private val binding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.titleTv.text = intent.getStringExtra("TITTLE")
        setUpRecyclerView()

        binding.goBackImg.setOnClickListener {
            finish()
        }

    }

    private fun setUpRecyclerView() {
        categoryList = ArrayList()

        binding.rvCategory.layoutManager =
            LinearLayoutManager(this)
        var db = Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObj = db.getDao()
        var recipes = daoObj.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)) {
                categoryList.add(recipes[i]!!)
            }

            categoryAdapter = CategoryAdapter(categoryList,this)
            binding.rvCategory.adapter = categoryAdapter
        }

    }
}