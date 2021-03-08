package com.example.unidayz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_list_view.view.*

class MainAdapter: RecyclerView.Adapter<MainAdapter.CustomViewHolder>(){

    private val posts = arrayOf("Anshika Menon", "Sangam Kumar")

    private val details = arrayOf(
        "CSC-402 Class has been cancelled",
        "Hi, Everyone today we are planning to organize a hackathon and need voluntiers for it. Please contact Anshika if interested.\""
    )

    inner class  CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var itemTitle : TextView
        var itemDetail : TextView

        init {
            itemTitle = itemView.findViewById(R.id.msg_pr_name)
            itemDetail = itemView.findViewById(R.id.msg_pr_des)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notif_card_view, parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, i: Int) {
        holder.itemTitle.text = posts[i]
        holder.itemDetail.text = details[i]
    }

    override fun getItemCount(): Int {
        return posts.size
    }


}