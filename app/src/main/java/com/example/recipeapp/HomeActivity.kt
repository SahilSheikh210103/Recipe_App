package com.example.recipeapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()

        binding.searchEditText.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
            finish()
        }
        binding.saladImg.setOnClickListener {
            var intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TITTLE", "Salad")
            intent.putExtra("CATEGORY", "Salad")
            startActivity(intent)
        }

        binding.mainDishImg.setOnClickListener {
            var intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TITTLE", "Main Dish")
            intent.putExtra("CATEGORY", "Dish")
            startActivity(intent)
        }

        binding.drinksImg.setOnClickListener {
            var intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TITTLE", "Drinks")
            intent.putExtra("CATEGORY", "Drinks")
            startActivity(intent)
        }

        binding.dessertsImg.setOnClickListener {
            var intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TITTLE", "Desserts")
            intent.putExtra("CATEGORY", "Desserts")
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.rvPopularRecipeRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var db = Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObj = db.getDao()
        var recipes = daoObj.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains("Popular")) {
                dataList.add(recipes[i]!!)
            }

            popularAdapter = PopularAdapter(dataList, this)
            binding.rvPopularRecipeRecyclerView.adapter = popularAdapter
        }

        binding.more.setOnClickListener {
            var diolog = Dialog(this)
            diolog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            diolog.setContentView(R.layout.bottom_sheet)

            diolog.show()
            diolog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT

            )

            diolog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            diolog.window!!.setGravity(Gravity.BOTTOM)
        }

    }

}