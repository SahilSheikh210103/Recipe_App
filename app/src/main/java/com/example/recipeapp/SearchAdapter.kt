package com.example.recipeapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.RvPopularRecipeItemBinding
import com.example.recipeapp.databinding.SearchRecyclerItemBinding

class SearchAdapter(var dataList : ArrayList<Recipe>,var context: Context):RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: SearchRecyclerItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = SearchRecyclerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: ArrayList<Recipe>){
        dataList = filterList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].img).into(holder.binding.searchItemImg)

        holder.binding.searchTextView.text = dataList[position].tittle

        holder.itemView.setOnClickListener {
            var intent  = Intent(context, RecipeActivity::class.java)
            intent.putExtra("img", dataList[position].img)
            intent.putExtra("tittle", dataList[position].tittle)
            intent.putExtra("des", dataList[position].des)
            intent.putExtra("ing", dataList[position].ing)
            intent.flags =Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}