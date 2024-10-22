package com.example.recipeapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.CategoryRecyclerItemBinding


class CategoryAdapter(var categoryList: ArrayList<Recipe>, var context: Context) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    inner class MyViewHolder(var binding: CategoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = CategoryRecyclerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(categoryList[position].img).into(holder.binding.img)
        holder.binding.titleTv.text = categoryList[position].tittle
        var time = categoryList[position].ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        holder.binding.timeTv.text = time[0]

        holder.binding.next.setOnClickListener {
            var intent  = Intent(context, RecipeActivity::class.java)
            intent.putExtra("img", categoryList[position].img)
            intent.putExtra("tittle", categoryList[position].tittle)
            intent.putExtra("des", categoryList[position].des)
            intent.putExtra("ing", categoryList[position].ing)
            intent.flags =Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}