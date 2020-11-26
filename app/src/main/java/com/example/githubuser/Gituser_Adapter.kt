package com.example.githubuser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_gituser.view.*
import java.util.ArrayList

class Gituser_Adapter (private val listGitusers : ArrayList<GitUsers>) : RecyclerView.Adapter<Gituser_Adapter.ListViewHolder>() {
    //internal var user_git = arrayListOf<GitUsers>()
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gituser,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listGitusers[position])
    }

    override fun getItemCount(): Int {
        return listGitusers.size
    }

    inner class ListViewHolder(viewitem: View) : RecyclerView.ViewHolder(viewitem){
        fun bind(users: GitUsers){
            with(itemView){
                Glide.with(itemView.context).load(users.photo)
                    .apply(RequestOptions().override(55,55))
                    .into(img_photo)
                txt_name.text = users.name
                txt_followers.text = "Pengikut : ${users.followers}"
                txt_following.text = "Diikuti: ${users.following}"
                itemView.setOnClickListener{ onItemClickCallback?.onItemClicked(users) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GitUsers)
    }
}