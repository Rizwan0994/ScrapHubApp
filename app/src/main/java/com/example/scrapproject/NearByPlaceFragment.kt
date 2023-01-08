package com.example.scrapproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class NearByPlaceFragment : Fragment(), OnMapReadyCallback {

    private  lateinit var mMap:GoogleMap
    val Faislabad= LatLng(18.8872,74.5307)
    val Sargodha= LatLng(16.8872,74.5307)

    private var locationArrayList:ArrayList<LatLng>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        locationArrayList!!.add(Faislabad)
        locationArrayList!!.add(Sargodha)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_near_by_place, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap= googleMap
        for(i in locationArrayList!!.indices){
            mMap.addMarker(MarkerOptions().position(locationArrayList!![i]).title("Marker"))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!!.get(i)))
        }
    }


}