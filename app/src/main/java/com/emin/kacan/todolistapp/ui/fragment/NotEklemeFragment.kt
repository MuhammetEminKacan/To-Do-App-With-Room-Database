package com.emin.kacan.todolistapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.room.Room
import com.emin.kacan.todolistapp.R
import com.emin.kacan.todolistapp.databinding.FragmentNotEklemeBinding
import com.emin.kacan.todolistapp.model.Note
import com.emin.kacan.todolistapp.room.NoteDao
import com.emin.kacan.todolistapp.room.NoteDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NotEklemeFragment : Fragment() {
    private lateinit var binding: FragmentNotEklemeBinding
    private lateinit var db : NoteDatabase
    private lateinit var noteDao : NoteDao
    var compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db =Room.databaseBuilder(requireContext(),NoteDatabase::class.java,"notes").build()
        noteDao = db.noteDao()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNotEklemeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarNotEkle.title = "not ekle"
        binding.btnKaydet.setOnClickListener {
            val baslik = binding.editTextBaslik.text.toString()
            val icerik = binding.editTextYazi.text.toString()
            val note = Note(0,baslik,icerik)
            compositeDisposable.add(
                noteDao.insert(note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                         handleResponse()
                        },{
                            Toast.makeText(requireContext(), "hata oldu ", Toast.LENGTH_SHORT).show()
                        }))
        }

    }
    fun handleResponse(){
        Navigation.findNavController(requireView()).navigate(R.id.notEklemeSayfasindan_anaSayfaDonus)
    }

}