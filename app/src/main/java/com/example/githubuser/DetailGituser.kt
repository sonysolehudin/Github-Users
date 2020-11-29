package com.example.githubuser

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_gituser.*

class detail_gituser : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_gituser)
        setData()
        InitviewPager()
    }

    fun setData() {
        val rec_data = intent.getParcelableExtra(EXTRA_DATA) as GitUsers
        rec_data.nameuser.let {
            if (supportActionBar != null) {
                this.title = rec_data.nameuser
            }
        }
        Glide.with(this).load(rec_data.photo)
            .apply(RequestOptions().override(55,55))
            .into(img_photo)
        txt_realname.text = resources.getString(R.string.nama_lengkap) + rec_data.nameuser
        txt_data_rec.text = resources.getString(R.string.username) + rec_data.name + "\n" + resources.getString(R.string.lokasi) + rec_data.location + "\n" +
                            resources.getString(R.string.pengikut) + rec_data.followers + "\n" + resources.getString(R.string.diikuti) + rec_data.following + "\n" +
                            resources.getString(R.string.company) + rec_data.usaha + "\n" + resources.getString(R.string.repo) + rec_data.reposit
    }

    fun InitviewPager() {
        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }
}
