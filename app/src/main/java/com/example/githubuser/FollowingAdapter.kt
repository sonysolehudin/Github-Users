package com.example.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_gituser.view.*

class Following_Adapter (private val listFollowing: ArrayList<GitUsers>) : RecyclerView.Adapter<Following_Adapter.ListViewHolder>() {
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): Following_Adapter.ListViewHolder {
        val view = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gituser, parent, false))
        return view
    }

    override fun onBindViewHolder(holder: Following_Adapter.ListViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int {
        return listFollowing.size
    }

    inner class ListViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        fun bind(gitusers: GitUsers){
            with(itemView){
                Glide.with(itemView.context).load(gitusers.photo)
                    .apply(RequestOptions().override(55,55))
                    .into(img_photo)
                txt_name.text = gitusers.name
                txt_followers.text = "Pengikut : ${gitusers.followers}"
                txt_following.text = "Diikuti: ${gitusers.following}"
            }
        }
    }

}