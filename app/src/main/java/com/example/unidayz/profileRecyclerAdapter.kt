package com.example.unidayz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_list_view.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val posts = arrayOf("Robocon, Nit Durgapur", "Generation Google Scholarship", "Training and Placement Representative election"
    , "AtCoder Beginner Contest 194 ")

    private val details = arrayOf(
        "Well, here we, Robocon, Nit Durgapur are giving you a small insite on what's Robocon is and what all we actually do, here's a video for you all people to light a fire in ur hearts.\nDo come in auditions of Team Robocon",
        "We are excited to share that the applications for Generation Google Scholarship: for women in computer science (formerly known as Women Techmakers Scholarship) are now open! ",
        "With all the due efforts and arrangements of the TPRs and the office of the Training and Placement, the 2nd year Training and Placement Representative election is now ready to happen.",
        "AtCoder Beginner Contest 194 will start on 6th March 2021 at 5:30 PM IST.\nThe contest duration is 100 minutes.\n \n Contest Link : https://atcoder.jp/contests/abc194\n \n Happy Coding"
    )

    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle : TextView
        var itemDetail : TextView

        init {
            itemTitle = itemView.findViewById(R.id.pr_title)
            itemDetail = itemView.findViewById(R.id.pr_des)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder  {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.profile_list_view, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = posts[i]
        viewHolder.itemDetail.text = details[i]
    }

    override fun getItemCount(): Int {
        return posts.size
    }


}