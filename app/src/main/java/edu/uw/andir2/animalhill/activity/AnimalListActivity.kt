package edu.uw.andir2.animalhill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import edu.uw.andir2.animalhill.databinding.ActivityAnimalListBinding
import org.json.JSONException
import coil.load

class AnimalListActivity : AppCompatActivity(){

    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAnimalListBinding.inflate(layoutInflater).apply { setContentView(root) }
        val url = "https://raw.githubusercontent.com/Andi-Ren/AnimalHill/coco/app/animals.json"

        requestQueue = Volley.newRequestQueue(this)

        with(binding) {
            val request = JsonObjectRequest(Request.Method.GET, url, null, {
                    response ->try {
                val jsonArray = response.getJSONArray("animals")
//                for (i in 0 until jsonArray.length()) {
                val animal1 = jsonArray.getJSONObject(0)
                val name1 = animal1.getString("name")
                val desc1 = animal1.getString("description")
                val url1 = animal1.getString("imgURL")

                val animal2 = jsonArray.getJSONObject(1)
                val name2 = animal2.getString("name")
                val desc2 = animal2.getString("description")
                val url2 = animal2.getString("imgURL")

                val animal3 = jsonArray.getJSONObject(2)
                val name3 = animal3.getString("name")
                val desc3 = animal3.getString("description")
                val url3 = animal3.getString("imgURL")

                textView1.text = "$name1"
                textView2.text = "$name2"
                textView3.text = "$name3"

                animalPic1.load(url1)
                animalPic2.load(url2)
                animalPic3.load(url3)
//                }

                animalPic1.setOnClickListener {
                    navigateToDetailActivity(this@AnimalListActivity, name1, desc1, url1)
                }

                animalPic2.setOnClickListener {
                    navigateToDetailActivity(this@AnimalListActivity, name2, desc2, url2)
                }

                animalPic3.setOnClickListener {
                    navigateToDetailActivity(this@AnimalListActivity, name3, desc3, url3)
                }

//                animalPic4.setOnClickListener {
//                    navigateToDetailActivity(this@AnimalListActivity, name4, desc4, url4)
//                }
//
//                animalPic5.setOnClickListener {
//                    navigateToDetailActivity(this@AnimalListActivity, name4, desc4, url4)
//                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            }, { error -> error.printStackTrace() })
            requestQueue?.add(request)

        }

        var actionBar = supportActionBar
        actionBar!!.title = "Animal List"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}