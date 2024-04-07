package com.emin.kacan.todolistapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.emin.kacan.todolistapp.R
import com.emin.kacan.todolistapp.databinding.CardTasarimBinding
import com.emin.kacan.todolistapp.model.Note
import com.emin.kacan.todolistapp.room.NoteDao
import com.emin.kacan.todolistapp.room.NoteDatabase
import com.emin.kacan.todolistapp.ui.fragment.AnaSayfaFragmentDirections
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ListAdapter(var mContext: Context,var noteList : List<Note>) : RecyclerView.Adapter<ListAdapter.cardTasarimTutucu>() {
    private lateinit var db : NoteDatabase
    private lateinit var noteDao : NoteDao
    var compositeDisposable = CompositeDisposable()

    inner class cardTasarimTutucu(var binding: CardTasarimBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardTasarimTutucu {
        val binding =CardTasarimBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return cardTasarimTutucu(binding)
    }

    fun setFilteredList(notlarList: List<Note>){
        this.noteList = notlarList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: cardTasarimTutucu, position: Int) {
        val note =noteList.get(position)
        val b =holder.binding
        val tarih = tarihVeSaatGetir()

        b.txtBaslik.text = note.baslik
        b.txtIcerik.text = note.icerik
        b.txtOlusturmaTarih.text = tarih

        b.cv.setOnClickListener {
            val gecis = AnaSayfaFragmentDirections.notGoruntulemeyeGecis(Note = note)
            Navigation.findNavController(it).navigate(gecis)
        }
    }

    fun tarihVeSaatGetir() : String {
        val zaman = Calendar.getInstance().time
        val format =  SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.getDefault())
        return format.format(zaman)
    }
}