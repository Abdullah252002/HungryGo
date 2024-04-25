package com.example.hungrygo.app.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hungrygo.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class set_Location : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var button: Button
    lateinit var searchView: SearchView
    private val REQUEST_LOCATION_PERMISSION = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_location)
        button = findViewById(R.id.button)
        searchView=findViewById(R.id.search)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment


 searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val location=searchView.query.toString()
                var addresslist:List<Address>?=null

                if(location!=null){
                    val geocoder= Geocoder(this@set_Location)
                    try {
                        addresslist=geocoder.getFromLocationName(location,1)
                    }catch (e: IOException){
                        e.printStackTrace()
                    }
                    val address=addresslist?.get(0)
                    val latLng=LatLng(address!!.latitude,address.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })


        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        enableMyLocation()
        // val sydney = LatLng(33.0, 33.0)
        // .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
            button.setOnClickListener {
                val resultIntent = Intent()
                resultIntent.putExtra("latitude", latLng.latitude)
                resultIntent.putExtra("longitude", latLng.longitude)
                setResult(RESULT_OK, resultIntent)
                finish()

            }

        }

    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mMap.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}