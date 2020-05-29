package com.example.alcoholcounter.ui.drinks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alcoholcounter.R
import java.text.DateFormat
import java.util.*

class DrinkListAdapter(private val drinks: List<Drink>) :
    RecyclerView.Adapter<DrinkListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.drinks_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(drinks[position])
    }

    override fun getItemCount(): Int {
        return drinks.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.drinkTitle)
        var price: TextView = itemView.findViewById(R.id.drinkPrice)
        var amount: TextView = itemView.findViewById(R.id.drinkCount)
        var lastAdded: TextView = itemView.findViewById(R.id.lastAdded)

        var cntPlus: ImageButton = itemView.findViewById(R.id.countPlus)
        var cntMinus: ImageButton = itemView.findViewById(R.id.countMinus)

        fun bind(drink: Drink) {
            val locale = Locale.getDefault()
            val currency = Currency.getInstance(locale)
            val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale)

            title.text = drink.name
            price.text = String.format("%s %s", drink.price.toString(), currency.symbol)
            amount.text = drink.amount.toString()

            fun updateLastAddedText(){
                if(drink.lastAdded.size > 0){
                    lastAdded.text = dateFormat.format(drink.lastAdded.last())
                } else {
                    lastAdded.text = "-"
                }
            }

            updateLastAddedText()

            cntMinus.setOnClickListener{
                if(drink.amount > 0){
                    drink.amount--
                    amount.text = drink.amount.toString()
                    if(drink.lastAdded.size > 0){
                        drink.lastAdded.removeAt(drink.lastAdded.lastIndex)
                    }
                    updateLastAddedText()
                }
            }

            cntPlus.setOnClickListener{
                drink.amount++
                amount.text = drink.amount.toString()
                drink.lastAdded.add(Calendar.getInstance().time)
                updateLastAddedText()
            }
        }
    }
}