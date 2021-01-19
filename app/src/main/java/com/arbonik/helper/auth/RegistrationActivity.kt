package com.arbonik.helper.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.arbonik.helper.Map.MapsFragment
import com.arbonik.helper.R
import com.google.android.gms.maps.model.LatLng


class RegistrationActivity : AuthBase()
{
    val containerButtons by lazy { findViewById<LinearLayout>(R.id.reg_cansel_choose) }
    var mapsFragment = MapsFragment()
    var regFragment = RegistrationFragment()

    var phone: Int? = null
    var name: String? = null
    var typeUser: USER_CATEGORY? = null
    var location: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        findViewById<Button>(R.id.reg_close)
            .setOnClickListener {
                setRegistrationFragment()
            }

        findViewById<Button>(R.id.reg_choose)
            .setOnClickListener {
                if (mapsFragment.myMacker?.position != null)
                {
//                    location = mapsFragment.myMacker!!.position!!
                    setRegistrationFragment()
//                   var a =  regFragment.view?.findViewById<TextView>(R.id.location_status_text)
//                    a!!.text = "выбрано"
                }
            }
        setRegistrationFragment()
    }

    fun setRegistrationFragment()
    {
        containerButtons?.visibility = View.GONE
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_layout_reg, regFragment)
            .commit()
    }

    fun setMapFragment()
    {
        containerButtons?.visibility = View.VISIBLE
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_layout_reg, mapsFragment)
            .commit()
//        if (location != null) mapsFragment.setMyMackerPosition(location!!)
    }

}
