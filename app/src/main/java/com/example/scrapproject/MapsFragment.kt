package com.example.scrapproject

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() ,OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var locationArrayList: ArrayList<LatLng>? = null
    private var titlesArrayList= ArrayList<String>()
    val FaislabadSH1 = LatLng(31.4923219, 73.1697321)
    val FaislabadSH2 = LatLng(31.4154526, 73.1113833)
    val FaislabadSH3 = LatLng(31.4219141, 73.0547755)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationArrayList= ArrayList()
        titlesArrayList.add("SH1")
        titlesArrayList.add("SH2")
        titlesArrayList.add("SH3")
        locationArrayList!!.add(FaislabadSH1)
        locationArrayList!!.add(FaislabadSH2)
        locationArrayList!!.add(FaislabadSH3)

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        for (i in locationArrayList!!.indices) {

            mMap.addMarker(MarkerOptions().position(locationArrayList!![i]).title(titlesArrayList[i]))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!!.get(i)))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10f), 3000, null);
        }
    }
}