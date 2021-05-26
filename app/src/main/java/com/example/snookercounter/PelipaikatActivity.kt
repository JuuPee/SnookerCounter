package com.example.snookercounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PelipaikatActivity : AppCompatActivity(), OnMapReadyCallback {
    var dataStorage = DataStorage()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelipaikat)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val suomi = LatLng(65.5579796134697, 27.264134400600717)
        val paikat = dataStorage.getPaikat()
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(suomi))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(suomi, 5.0f))
        for ((key, value) in paikat) {
            googleMap.addMarker(MarkerOptions()
                    .position(value)
                    .title(key))
        }
    }
}