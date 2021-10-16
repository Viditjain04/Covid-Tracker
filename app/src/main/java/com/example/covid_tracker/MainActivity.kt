package com.example.covid_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var stateList:MutableList<StateModel>
    val TAG:String="MainActivities"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stateList= mutableListOf()
        stateList.add(StateModel("State Name",0,0,0))
        val adapter=RViewAdapter(stateList)
        stateRV.adapter=adapter
        stateRV.layoutManager=LinearLayoutManager(this)
        getStateInfo()
        getWorldInfo()

    }

    fun refresh(v:View) {
       getStateInfo()
       //getWorldInfo()
   }
   private fun getStateInfo(){
       val url="https://api.rootnet.in/covid19-in/stats/latest"
       val queue=Volley.newRequestQueue(this@MainActivity)

       val request=JsonObjectRequest(Request.Method.GET, url,null,
           {
               try {
                   val dataObj = it.getJSONObject("data")
                   val summaryObj = dataObj.getJSONObject("summary")
                   val cases: Int = summaryObj.getInt("total")
                   val recovered: Int = summaryObj.getInt("discharged")
                   val deaths: Int = summaryObj.getInt("deaths")
                   noOfCasesIndia.text = cases.toString()
                   noOfRecoveredIndia.text = recovered.toString()
                   noOfDeathsIndia.text = deaths.toString()
                   val regionalArray = dataObj.getJSONArray("regional")

                    for (k in 0 until regionalArray.length()) {
                       val regionalObj = regionalArray.getJSONObject(k)
                       val state: String = regionalObj.getString("loc")
                       val cases: Int = regionalObj.getInt("confirmedCasesIndian")
                       val discharged = regionalObj.getInt("discharged")
                       val deaths = regionalObj.getInt("deaths")
                       val stateModel = StateModel(state, cases, discharged, deaths)
                       stateList.add(stateModel)
                   }
                   val adapter=RViewAdapter(stateList)
                   stateRV.layoutManager = LinearLayoutManager(this)
                   stateRV.adapter = adapter
               }catch (e:JSONException){
                   e.printStackTrace()
                   Log.d(TAG,"state data ${e.printStackTrace()}")
                   Toast.makeText(this,"Fail to Load Data",Toast.LENGTH_SHORT).show()
               }

                }, { Toast.makeText(this,"Fail to Load Data",Toast.LENGTH_SHORT).show()})
queue.add(request)
   }
    private fun getWorldInfo(){
        val url="https://disease.sh/v3/covid-19/all"
        val queue=Volley.newRequestQueue(this@MainActivity)
        val request=JsonObjectRequest(Request.Method.GET, url,null,
            {

                try {
                val dataObj = it.getJSONObject("object")
                val cases: Int = dataObj.getInt("cases")
                val recovered: Int = dataObj.getInt("recovered")
                val deaths: Int = dataObj.getInt("deaths")
                noOfCasesWorld.text = cases.toString()
                noOfRecoveredWorld.text = recovered.toString()
                noOfDeathsworld.text = deaths.toString()
                }catch (e: JSONException){e.printStackTrace()
                    Toast.makeText(this,"Fail to Load Data",Toast.LENGTH_SHORT).show()}

            }
               ,
            {Toast.makeText(this,"Fail to Load Data",Toast.LENGTH_SHORT).show()})
queue.add(request)
    }
}