package com.emin.kacan.todolistapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.emin.kacan.todolistapp.R
import com.emin.kacan.todolistapp.databinding.FragmentAnaSayfaBinding
import com.emin.kacan.todolistapp.model.Note
import com.emin.kacan.todolistapp.room.NoteDatabase
import com.emin.kacan.todolistapp.ui.adapter.ListAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Locale

class AnaSayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnaSayfaBinding
    private val compositeDisposable = CompositeDisposable()
    private lateinit var adapter : ListAdapter
    private val notlarListesi = ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAnaSayfaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Room.databaseBuilder(requireContext(), NoteDatabase::class.java,"notes").build()
        val noteDao = db.noteDao()

        binding.SearchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    ara(newText)
                }
                return true
            }

        })

        binding.fab.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.not_eklemeye_gecis)
        }
        compositeDisposable.add(
            noteDao.GetAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        for (i in it){
                            notlarListesi.add(i)
                        }
                        handleResponse(it)
                    },{
                        Toast.makeText(requireContext(), "hata oldu", Toast.LENGTH_SHORT).show()
                    }
                )
        )

    }

    private fun handleResponse(noteList : List<Note>){
      binding.rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListAdapter(requireContext(),noteList)
        binding.rv.adapter = adapter
    }

    private fun ara(aramaKelimesi:String){
        if (aramaKelimesi != null){
            val filtreliListe = ArrayList<Note>()
            for (i in notlarListesi){
                if (i.baslik.lowercase(Locale.ROOT).contains(aramaKelimesi)){
                    filtreliListe.add(i)
                }
            }
            if (filtreliListe.isEmpty()){

            }else{
                adapter.setFilteredList(filtreliListe)
            }
        }
    }

}