package com.example.alcoholcounter.ui.drinks

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.alcoholcounter.MainApp

import com.example.alcoholcounter.R
import com.example.alcoholcounter.ui.events.Event
import com.example.alcoholcounter.ui.events.EventEditFragment
import kotlinx.android.synthetic.main.fragment_drink_edit.*
import kotlinx.android.synthetic.main.fragment_drink_edit.confirm_button
import kotlinx.android.synthetic.main.fragment_drink_edit.nameEditText
import kotlinx.android.synthetic.main.fragment_event_edit.*
import java.math.BigDecimal
import java.util.*

class DrinkEditFragment(private val drink : Drink?, val event : Event? = null) : Fragment() {

    var onEditedListener : EventEditFragment.OnEditedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_drink_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locale = Locale.getDefault()
        val currency = Currency.getInstance(locale)

        priceSymbol.text = currency.symbol

        if(drink != null){
            priceEditText.setText(drink.price.toString())
            nameEditText.setText(drink.name)
        }

        confirm_button.setOnClickListener{
            if(nameEditText.text.isNotEmpty() && priceEditText.text.isNotEmpty()){
                if(drink == null && event != null){
                    val newDrink = Drink(
                        nameEditText.text.toString())
                    newDrink.price = BigDecimal(priceEditText.text.toString())
                    event.drinks.add(newDrink)
                }else if (drink != null){
                    drink.name = nameEditText.text.toString()
                    drink.price = BigDecimal(priceEditText.text.toString())
                } else{
                    Log.e("Drink", "Invalid drink edit")
                }
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStackImmediate()
                onEditedListener?.onEditConfirmClicked()
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Invalid data!")
                    .setCancelable(false)
                    .setPositiveButton("OK"
                    ) { dialog, id -> }
                val alert: AlertDialog = builder.create()
                alert.show()
            }
        }
    }

    interface OnItemChangedListener {
        fun onItemChanged()
    }
}
