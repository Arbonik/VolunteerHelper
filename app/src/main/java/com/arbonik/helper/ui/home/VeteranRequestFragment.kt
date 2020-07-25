package com.arbonik.helper.ui.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arbonik.helper.*
import com.arbonik.helper.auth.SharedPreferenceUser
import com.arbonik.helper.helprequest.RequestData
import com.arbonik.helper.helprequest.RequestManager

class VeteranRequestFragment : Fragment() {
    var requestManager = RequestManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true // сохранение сострояния при перевороте
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_veteran_request, container, false)

        val data: RecyclerView = root.findViewById(R.id.fragmentRecycler)

        val linear = LinearLayoutManager(context)
        data.layoutManager = linear

        var ca = CategoryAdapter().apply {
            categories = mutableListOf(CategoryWidget("SOTEXT"),
                CategoryWidget("wefweg"),
                CategoryWidget("SwewegOTEXT"))
        }
        data.adapter = ca

        val commentText = root.findViewById<TextView>(R.id.comment)
        val timeHelp = root.findViewById<TextView>(R.id.timeHelp)
        val datePicker = root.findViewById<DatePicker>(R.id.calendarView)

        root.findViewById<Button>(R.id.toAuth).setOnClickListener {
            v ->
                for (c in ca.categories) {
                    if (c.choise) {
                        var request = RequestData(
                            c.category,
                            commentText.text.toString(),
                            SharedPreferenceUser.currentUser!!,
                            "${datePicker.dayOfMonth} числа.${datePicker.month} месяца в ${timeHelp.text}",
                            false
                        )
                        requestManager.addRequest(request)
                        c.choise = false
                    }
                }
                ca.notifyDataSetChanged()

                val t = Toast.makeText(
                    HelperApplication.globalContext,
                    "Ваша заявка принята!", Toast.LENGTH_LONG
                )
                t.setGravity(Gravity.CENTER, 0, 0)
                t.show()
            }
//        }
        return root
    }

}