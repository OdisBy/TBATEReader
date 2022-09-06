package com.ruliam.tbatereader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.ruliam.tbatereader.adapter.TitleAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var capitulosRecyclerView: RecyclerView
    private lateinit var capitulosArrayListOfTitles : ArrayList<String>
    var swappedOrder = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbref = FirebaseDatabase.getInstance().reference

        supportActionBar?.setDisplayShowHomeEnabled(true)

        capitulosRecyclerView = findViewById(R.id.recycler_capitulos)
        capitulosRecyclerView.layoutManager = LinearLayoutManager(this)
        capitulosRecyclerView.setHasFixedSize(true)
        fillWithExemple()
        //getCapituloTitle()



        val swapOrderButton : ImageButton = findViewById(R.id.swapOrder)
        swapOrderButton.setOnClickListener {
            capitulosArrayListOfTitles.reverse()
            capitulosRecyclerView.adapter?.notifyDataSetChanged()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Atualizar capitulos")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title.equals("Atualizar capitulos")){
            getCapituloTitle()
            Toast.makeText(this, "Capítulos sendo atualizados...", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fillWithExemple() {
        capitulosArrayListOfTitles = arrayListOf()
        var index = 0

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (capituloSnapshot in snapshot.children) {
                        val capituloTitle = capituloSnapshot.child("Title").value.toString()
                        capitulosArrayListOfTitles.add(capituloTitle)
                    }
                    var adapter = TitleAdapter(capitulosArrayListOfTitles)
                    capitulosRecyclerView.adapter = adapter


                    adapter.setOnItemClickListener(object: TitleAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            var text = capitulosArrayListOfTitles[position]
                            Toast.makeText(this@MainActivity, "Capítulo selecionado foi: $text", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            })


        dbref.keepSynced(true)
    }
    private fun getCapituloTitle() {
        capitulosArrayListOfTitles = arrayListOf()

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (capituloSnapshot in snapshot.children) {
                        val capituloTitle = capituloSnapshot.child("Title").value.toString()
                        capitulosArrayListOfTitles.add(capituloTitle)
                    }
                    //capitulosArrayListOfTitles.reverse()
                    capitulosRecyclerView.adapter = TitleAdapter(capitulosArrayListOfTitles)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        dbref.keepSynced(true)
    }
}

