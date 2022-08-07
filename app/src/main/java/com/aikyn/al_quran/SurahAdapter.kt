package com.aikyn.al_quran

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SurahAdapter(listArray: ArrayList<ListAyah>, context: Context): RecyclerView.Adapter<SurahAdapter.ViewHolder>() {

    var array = listArray
    var cont = context
    //Создаем то что будем привязывать
    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val v = view
        val ayah = view.findViewById<TextView>(R.id.ayah)
        val numberAyah = view.findViewById<TextView>(R.id.numberOfAyah)
        fun bind(listAyah: ListAyah, context: Context){
            ayah.text = listAyah.ayah
            numberAyah.text = listAyah.numberOfAyah
            val numberOfSurah = listAyah.numberOfSurah
            itemView.setOnClickListener {
                try {
                    val nAyah = numberAyah.text.toString().toInt()
                    val i = Intent(context, AyahAudio::class.java)
                    i.putExtra("nAyah", nAyah.toString())
                    i.putExtra("nSurah", numberOfSurah)
                    i.putExtra("ayah", listAyah.ayah)
                    context.startActivity(i)
                } catch (e: Exception) {}

            }
        }

    }

    //Заполняем шаблоны
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(cont)
        return ViewHolder(inflater.inflate(R.layout.surah_layout, parent, false))
    }

    //Привязываем все и что то делаем
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listAyah = array.get(position)
        holder.bind(listAyah, cont)
    }

    //Сколько элементов
    override fun getItemCount(): Int {
        return array.size
    }

}