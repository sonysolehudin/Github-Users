package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class detail_gituser : AppCompatActivity() {

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_gituser)
        val nama: TextView = findViewById(R.id.txt_realname)
        val data: TextView = findViewById(R.id.txt_data_rec)
        val poto: CircleImageView = findViewById(R.id.img_photo)
        val rec_data = intent.getParcelableExtra(EXTRA_PERSON) as GitUsers
        val t1 = "Nama Lengkap: ${rec_data.nameuser}"
        val t2 = "Username: ${rec_data.name}\nLocation: ${rec_data.location}\n" +
                "Pengikut: ${rec_data.followers}\nDiikuti: ${rec_data.following}\n" +
                "Perusahaan: ${rec_data.usaha}\nRepository: ${rec_data.reposit}\n"
        //poto.setImageResource(rec_data.photo)
        nama.text = t1
        data.text = t2
    }
}
