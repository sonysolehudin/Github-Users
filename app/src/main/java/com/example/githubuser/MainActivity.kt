package com.example.githubuser

import android.app.SearchManager
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_backpressed.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_no_internet.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var usergit = ArrayList<GitUsers>()
    var check_data = true
    //val MAIN_URL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GetData()
        //usergit.addAll(ListGitusers())
        //showRecycler()
    }

    fun GetData(){
        shimmer_layout.startShimmerAnimation()
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization",resources.getString(R.string.key))
        val url_main = resources.getString(R.string.main_URL)
        client.get(url_main, object : AsyncHttpResponseHandler(){
                override fun onSuccess( statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    val data_result = String(responseBody)
                    try {
                        val Arrayresponse = JSONArray(data_result)
                        for(a in 0 until Arrayresponse.length()){
                            val namedata = Arrayresponse.getJSONObject(a).getString("login")
                            DataDetailUsers(namedata)
                        }
                    }
                    catch(e : Exception){
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }

                override fun onFailure( statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                    val errorMessage = when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error.message}"
                    }
                    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
                    dialog_no_internet()
                }
            })
    }

    fun DataDetailUsers(dataUsers: String){
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization",resources.getString(R.string.key))
        val url_detail = resources.getString(R.string.detail_URL) + dataUsers
        client.get(url_detail, object: AsyncHttpResponseHandler(){
            override fun onSuccess( statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                Log.d("OK", statusCode.toString())
                val hasil = String(responseBody)
                try {
                    val objectJson = JSONObject(hasil)
                    val datalogin = objectJson.getString("login")
                    val datafollowers = objectJson.getString("followers")
                    val datafollowing = objectJson.getString("following")
                    val dataphoto = objectJson.getString("avatar_url")
                    var datarealname = objectJson.getString("name")
                    var dataloc = objectJson.getString("location")
                    var datacompany = objectJson.getString("company")
                    var datarepo = objectJson.getString("public_repos")
                    //Tampilkan ke RecyclerView
                    ListGitusers(datalogin, datafollowers, datafollowing, dataphoto)
                }
                catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure( statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
                dialog_no_internet()
            }
        })
    }

    fun ListGitusers(name: String, f1: String, f2: String, pic: String){
        shimmer_layout.stopShimmerAnimation()
        shimmer_layout.removeAllViews()
        rv_gitusers.hasFixedSize()
        val listgit: ArrayList<GitUsers> = ArrayList()
        listgit.add(GitUsers(name, f1, f2, pic, null, null, null, null))
        rv_gitusers.layoutManager = LinearLayoutManager(applicationContext)
        val gituseradapter = Gituser_Adapter(usergit)
        rv_gitusers.adapter = gituseradapter
        val line = DividerItemDecoration(this.applicationContext, DividerItemDecoration.VERTICAL)
        line.setDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.divider)!!)
        rv_gitusers.addItemDecoration(line)
        gituseradapter.setOnItemClickCallback(object : Gituser_Adapter.OnItemClickCallback {
            override fun onItemClicked(data: GitUsers) {
                showSelectedUsers(data)
            }
        })
    }

    fun dialog_no_internet(){
        val custom = LayoutInflater.from(this).inflate(R.layout.activity_no_internet,null)
        val alert = AlertDialog.Builder(this).setView(custom).show()
        val btn = custom.findViewById<Button>(R.id.try_again)
        btn.setOnClickListener {
            GetData()
            alert.dismiss()}
    }

    private fun showSelectedUsers(gitusers: GitUsers) {
        Toast.makeText(this, "Kamu memilih ${gitusers.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.cari_disini)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onBackPressed() {
        val custom = LayoutInflater.from(this).inflate(R.layout.activity_backpressed,null)
        val alert = AlertDialog.Builder(this).setView(custom).show()
        val yes = custom.findViewById<Button>(R.id.btn_yes)
        val no = custom.findViewById<Button>(R.id.btn_no)
        yes.setOnClickListener { finish() }
        no.setOnClickListener { alert.cancel() }
    }

    override fun onResume() {
        super.onResume()
        shimmer_layout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmer_layout.stopShimmerAnimation()
        super.onPause()
    }
}
