package com.aikyn.al_quran

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class Adapter (listArray: ArrayList<ListItem>, context: Context): RecyclerView.Adapter<Adapter.ViewHolder>() {

    var array = listArray
    var cont = context

    //Создаем то что будем привязывать
    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val arabic = view.findViewById<TextView>(R.id.arabic)
        val english = view.findViewById<TextView>(R.id.english)
        val ayahs = view.findViewById<TextView>(R.id.ayahs)
        val number = view.findViewById<TextView>(R.id.number)

        fun bind(listItem: ListItem, context: Context){
            arabic.text = listItem.arabicName
            english.text = listItem.englishName
            ayahs.text = listItem.ayahs
            number.text = listItem.number
            itemView.setOnClickListener {
                Toast.makeText(context, arabic.text.toString(), Toast.LENGTH_SHORT).show()
                val i = Intent(context, MainActivity::class.java)
                i.putExtra("number", number.text.toString())
                context.startActivity(i)
            }

        }
    }

    //Заполняем шаблоны
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(cont)
        return ViewHolder(inflater.inflate(R.layout.item_layout, parent, false))
    }

    //Привязываем все и что то делаем
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listItem = array.get(position)
        holder.bind(listItem, cont)
    }

    //Сколько элементов
    override fun getItemCount(): Int {
        return array.size
    }
}