package com.arbonik.helper.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.arbonik.helper.R
import com.arbonik.helper.system.Format
import com.arbonik.helper.system.Format.Companion.makeMask
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint


class RegistrationFragment() : Fragment()
{
    var textPhone: EditText? = null
    var textName: EditText? = null
    var geoPoint: GeoPoint? = null
    val regActivity by lazy { activity as RegistrationActivity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        var root = inflater.inflate(R.layout.fragment_registration, container, false)
        root.apply {

            val container_location = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.container_location)
            val radioButtonVolonteer = findViewById<RadioButton>(R.id.radioButtonVolonteer)
            val radioButtonVeteran = findViewById<RadioButton>(R.id.radioButtonVeteran)
            textPhone = findViewById(R.id.phone_reg)
            textName = findViewById(R.id.name_reg)
            regActivity?.apply {
                if (name != null) textName!!.text.insert(0, name)
                if (phone != null) textPhone!!.text.insert(0, phone.toString())
            }

            findViewById<RadioGroup>(R.id.role_radio_group)
                .setOnCheckedChangeListener { _, id ->
                    if (id == radioButtonVolonteer.id) container_location.visibility = View.GONE
                    else container_location.visibility = View.VISIBLE
                }
            findViewById<Button>(R.id.singInBotton)
                .setOnClickListener {
                    if (Format.format_number(textPhone!!.text.toString()).length == 12 && textName!!.text.toString() != "") // проверка на ввод данных
                        {
                            if (radioButtonVeteran.isChecked && geoPoint != null || radioButtonVolonteer.isChecked) // проверка на выбор адреса, если пользователь ветеран
//                                authUser(Format.format_number(phone_reg.text.toString()), Aim.register)
                            else
                                Toast.makeText(context, getString(R.string.choose_location), Toast.LENGTH_LONG).show()
                        } else
                                Toast.makeText(context, getString(R.string.inputAllView), Toast.LENGTH_LONG).show()
                }
            findViewById<Button>(R.id.adressButton)
                .setOnClickListener{
                    regActivity?.apply {
                        if (phone != null) phone = Format.format_number(textPhone!!.text.toString()).toInt()
                        if (name != null) name = textName!!.text.toString()
                        if (radioButtonVeteran.isChecked) typeUser = USER_CATEGORY.VETERAN
                        else typeUser = USER_CATEGORY.VOLONTEER
                        setMapFragment()
                    }
                }
            makeMask(textPhone!!)

        }

        return root
    }

//    fun newInstance(someInt: Int, someString: String?): CatFragment?
//    {
//        val catFragment = CatFragment()
//        val args = Bundle()
//        args.putInt("someInt", someInt)
//        args.putString("SomeString", someString)
//        catFragment.setArguments(args)
//        return catFragment
//    }
}          