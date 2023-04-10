package com.ihsanarslan.swiperefreshlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ihsanarslan.swiperefreshlayout.adapter.Note
import com.ihsanarslan.swiperefreshlayout.adapter.NoteAdapter
import com.ihsanarslan.swiperefreshlayout.database.NoteDB
import com.ihsanarslan.swiperefreshlayout.database.NoteDao
import com.ihsanarslan.swiperefreshlayout.database.NoteDatabase
import com.ihsanarslan.swiperefreshlayout.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteDao: NoteDao
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteList: ArrayList<Note>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteList= ArrayList()

        // NoteDatabase nesnesi oluşturun
        val noteDatabase = NoteDatabase.getInstance(this)
        // NoteDao nesnesini oluşturun
        noteDao = noteDatabase.noteDao()

        //room veritabanımıza biraz not ekleyelim ki boş olmasın içi.
        GlobalScope.launch {
            noteDao.insert(NoteDB(0,"deneme2","deneme",-12544,true))
            noteDao.insert(NoteDB(0,"deneme","deneme",-12554,true))
            noteDao.insert(NoteDB(0,"deneme3","deneme",-124254,true))
        }

        //uygulama ilk açıldığında hiç not yokmuş gibi görünecek çünkü, veritabanında ki notları listeye eklemedik ilk başta.
        //ama eğer kullanıcı yenileme işlemi yaparsa listeye ekleme işlemleri olacak ve notların göründüğünü farkedeceksiniz.

        // RecyclerView'ı oluştur
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Adapter'ı oluştur ve RecyclerView'a bağla
        noteAdapter = NoteAdapter(noteList, noteDao)
        recyclerView.adapter = noteAdapter


        //viewBinding ile swipeRefreshLayout nesnemize erişiyoruz ve olay dinleyiyici ekliyoruz.
        binding.swipeRefreshLayout.setOnRefreshListener {
            //recyclerViewe bağlı olan listenin içerisini temizliyoruz.
            //çünkü notları yeniden yazdıracağız.
            noteList.clear()

            // Tüm notları ben Room veritabanında tutuyorum.
            //Bu yüzden notları veritabannından çekip noteLİst'e yazdırıyorum.
            //Bu işlemi arkaplanda yapıyorum ben, bu yüzden işlemimi GlobalScope içerisinde yazacağım.
            GlobalScope.launch {
                //veritabanından tüm notları çekip notes değişkenine atıyorum.
                val notes = noteDao.getAllNotes()

                //notes içinde bulunan notlara döngü ile tek tek erişiyorum.
                notes.forEach { note ->
                    val newnote = Note(note.id, note.title, note.content, note.color, note.liked)

                    //eğer notumuzun aynısı listede yoksa, listemize notumuzu ekliyoruz.
                    if (newnote !in noteList) {
                        noteList.add(newnote)
                    }
                }
            }
            //adapter'a verilerin değiştiğini haber ediyoruz.
            noteAdapter.notifyDataSetChanged()

            //yenileme işleminde ekranda çıkan refresh iconunu pasif hale getiriyoruz.
            //böylece yenileme işleminin bittiğini kullanıcı anlamış olacak.
            //eğer bu değeri false yapmazsanız ekranda öylece dönüp durur, kaybolmaz.
            binding.swipeRefreshLayout.isRefreshing=false
        }
    }
}