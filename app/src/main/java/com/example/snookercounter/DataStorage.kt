package com.example.snookercounter

import com.google.android.gms.maps.model.LatLng
import java.util.*

class DataStorage {
    private val paikat: MutableMap<String, LatLng> = HashMap()
    fun getPaikat(): Map<String, LatLng> {
        paikat["Patteribaari Mikkeli"] = LatLng(61.68506790126753, 27.272416053969355)
        paikat["Ritz, Helsinki"] = LatLng(60.176639304955, 24.92323983682767)
        paikat["Baribal, Helsinki"] = LatLng(60.15905573229496, 24.93501345729167) //
        paikat["Snooker ja Pool Club Naantali, Naantali"] = LatLng(60.54548774220998, 22.072798881646865) //
        paikat["Jyväs-Snooker, Jyväskylä"] = LatLng(62.28433296109897, 25.73587403888789) //
        paikat["Galaxie Center, Tampere"] = LatLng(61.498766720848515, 23.771659018743133) //
        paikat["My Snooker Room, Joroinen"] = LatLng(60.85500425421781, 23.622374657133125) //
        paikat["Biljardisali Fouli, Lappeenranta"] = LatLng(61.083436252665976, 28.192962558880804) //
        paikat["Kera Snooker, Espoo"] = LatLng(60.23523589978974, 24.75924580770489) //
        paikat["Snooker Time Oy, Oulu"] = LatLng(65.358278222473, 25.349542084753573) //
        return paikat
    }
}