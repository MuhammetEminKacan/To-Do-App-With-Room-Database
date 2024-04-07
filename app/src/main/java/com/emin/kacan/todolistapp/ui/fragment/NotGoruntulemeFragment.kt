package com.emin.kacan.todolistapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.emin.kacan.todolistapp.R
import com.emin.kacan.todolistapp.databinding.FragmentNotGoruntulemeBinding
import com.emin.kacan.todolistapp.model.Note
import com.emin.kacan.todolistapp.room.NoteDao
import com.emin.kacan.todolistapp.room.NoteDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Locale

class NotGoruntulemeFragment : Fragment() {
    private lateinit var binding: FragmentNotGoruntulemeBinding
    private lateinit var db : NoteDatabase
    private lateinit var noteDao : NoteDao
    var compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(),NoteDatabase::class.java,"notes").build()
        noteDao = db.noteDao()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNotGoruntulemeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar2.title = "not görüntüleme ve düzenleme sayfası"
        val bundle : NotGoruntulemeFragmentArgs by navArgs()
        val gelenNot = bundle.Note
        val gelenBaslik = gelenNot.baslik
        val gelenIcerik = gelenNot.icerik
        val gelenId = gelenNot.id
        binding.editTextBaslikGoster.setText(gelenBaslik)
        binding.editTextYaziGoster.setText(gelenIcerik)
        val note = Note(gelenId,gelenBaslik,gelenIcerik)
        binding.btnSil.setOnClickListener {
            compositeDisposable.add(
                noteDao.delete(note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse)
            )
        }

        binding.btnDuzenle.setOnClickListener {
            val baslik = binding.editTextBaslikGoster.text.toString()
            val icerik = binding.editTextYaziGoster.text.toString()
            val duzenlenenNot = Note(gelenId,baslik,icerik)
            compositeDisposable.add(
                noteDao.update(duzenlenenNot)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse)
            )

        }

    }
    fun handleResponse(){
        Navigation.findNavController(requireView()).navigate(R.id.notGoruntulemeSayfasindan_AnaSayfaDonus)
    }


}