package com.example.recipeapp

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var imgCrop = true

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.backGroundImg)
        binding.titleTextView.text = intent.getStringExtra("tittle")
        binding.stepData.text = intent.getStringExtra("des")
        var ing =
            intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }
                ?.toTypedArray()
        binding.timeTv.text = ing!![0]

        for (i in 1 until ing!!.size) {
            binding.ingData.text =
                """${binding.ingData.text} 🟢 ${ing[i]}
                
            """.trimIndent()

        }

        binding.stepsBtn.background = null

        binding.stepsBtn.setTextColor(getColor(R.color.black))

        binding.stepsBtn.setOnClickListener{
            binding.stepsBtn.setBackgroundResource(R.drawable.btn_ing)
            binding.stepsBtn.setTextColor(getColor(R.color.white))
            binding.ingredientsBtn.setTextColor(getColor(R.color.black))
            binding.stepsScroll.visibility = View.VISIBLE
            binding.ingScroll.visibility = View.GONE
            binding.ingredientsBtn.background = null
        }

        binding.ingredientsBtn.setOnClickListener{
            binding.ingredientsBtn.setBackgroundResource(R.drawable.btn_ing)
            binding.ingredientsBtn.setTextColor(getColor(R.color.white))
            binding.stepsBtn.setTextColor(getColor(R.color.black))
            binding.stepsScroll.visibility = View.GONE
            binding.ingScroll.visibility = View.VISIBLE
            binding.stepsBtn.background = null
        }



        binding.fullScreenImg.setOnClickListener {
            if (imgCrop) {
                binding.backGroundImg.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.backGroundImg)
                binding.fullScreenImg.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
                binding.blackShaddow.visibility = View.GONE
                imgCrop = !imgCrop
            } else {
                binding.backGroundImg.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.backGroundImg)
                binding.fullScreenImg.colorFilter = null
                binding.blackShaddow.visibility = View.GONE
                imgCrop = !imgCrop
            }
        }

        binding.goBackImg.setOnClickListener {
            finish()
        }

    }
}