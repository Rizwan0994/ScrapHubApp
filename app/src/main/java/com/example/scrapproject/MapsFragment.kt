package com.example.scrapproject
import android.location.Location
import android.Manifest
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class MapsFragment : Fragment() ,OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var locationArrayList: ArrayList<LatLng>? = null
    private var titlesArrayList = ArrayList<String>()
    private val client = OkHttpClient()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationArrayList = ArrayList()

        // Fetch yards from backend API
        val request = Request.Builder()
            .url("https://apitestregs.onrender.com/yards")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonArray = JSONArray(response.body?.string())
                for (i in 0 until jsonArray.length()) {
                    val yard = jsonArray.getJSONObject(i)
                    titlesArrayList.add(yard.getString("name"))
                    val latitude = yard.getDouble("latitude")
                    val longitude = yard.getDouble("longitude")
                    locationArrayList!!.add(LatLng(latitude, longitude))
                }

                // Update map with fetched yards
                activity?.runOnUiThread {
                    updateMapWithYards()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to fetch yards: ${e.message}")
            }
        })

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        updateMapWithYards()
    }
    private fun updateMapWithYards() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            // Get user's current location
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)

                    // Find nearest yard from MongoDB
                    var minDistance = Float.MAX_VALUE
                    var nearestYardIndex = -1
                    val results = FloatArray(1)
                    for (i in locationArrayList!!.indices) {
                        Location.distanceBetween(
                            userLocation.latitude,
                            userLocation.longitude,
                            locationArrayList!![i].latitude,
                            locationArrayList!![i].longitude,
                            results
                        )
                        val distance = results[0]
                        if (distance < minDistance) {
                            minDistance = distance
                            nearestYardIndex = i
                        }
                    }

                    // Update map with nearest yard
                    if (nearestYardIndex != -1) {
                        mMap.addMarker(
                            MarkerOptions().position(locationArrayList!![nearestYardIndex])
                                .title(titlesArrayList[nearestYardIndex])
                        )
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                locationArrayList!![nearestYardIndex],
                                15f
                            )
                        )
                    }
                }
            }
        }

        // Clear existing markers
        mMap.clear()

        // Add all yards as markers
        for (i in locationArrayList!!.indices) {
            mMap.addMarker(
                MarkerOptions().position(locationArrayList!![i]).title(titlesArrayList[i])
            )
        }

        // Center map on all markers
        if (locationArrayList!!.isNotEmpty()) {
            val builder = LatLngBounds.builder()
            for (location in locationArrayList!!) {
                builder.include(location)
            }
            val bounds = builder.build()
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))

            // Zoom in to an appropriate level
            val zoomLevel = getZoomLevel(bounds.northeast, bounds.southwest)
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel), 2000, null)
        }
    }

    private fun getZoomLevel(northeast: LatLng, southwest: LatLng): Float {
        val latFraction =
            (Math.abs(northeast.latitude - southwest.latitude) / 180).toFloat()
        val lngFraction =
            (Math.abs(northeast.longitude - southwest.longitude) / 360).toFloat()

        val latZoom =
            Math.floor(Math.log(1 / latFraction.toDouble()) / Math.log(2.0)).toFloat()
        val lngZoom =
            Math.floor(Math.log(1 / lngFraction.toDouble()) / Math.log(2.0)).toFloat()
        return Math.min(latZoom, lngZoom)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                updateMapWithYards()
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

