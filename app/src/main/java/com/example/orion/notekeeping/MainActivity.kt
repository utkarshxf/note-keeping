package com.example.orion.notekeeping

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {
    var listofnotes=ArrayList<note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //adding data
//        listofnotes.add(
//            note(
//                1,
//                "impotant",
//                "im ok i don't want attention of anyone because no one loves me"
//            )
//        )
//        listofnotes.add(note(2, "passport", "being able to do this make me feel happy"))
//        listofnotes.add(
//            note(
//                3,
//                "its ok",
//                "everthing will be fine utkarsh what you wanted in life you will get one day"
//            )
//        )
//        var myadpt = adapter(listofnotes)


        //load from db
        loadquery("%")
    }

    override fun onResume() {
        loadquery("%")
        super.onResume()
    }
    @SuppressLint("Range")
    fun loadquery(title:String){
        var dbmeneger = DbManager(this)
        val projection= arrayOf("ID","Title","Description")
        val selectionArgs= arrayOf(title)
        val cursor =dbmeneger.query(projection,"Title like ?",selectionArgs,"Title")
        listofnotes.clear()
        if (cursor.moveToFirst()){
            do {
                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val Title=cursor.getString(cursor.getColumnIndex("Title"))
                val Description=cursor.getString(cursor.getColumnIndex("Description"))
                listofnotes.add(note(ID,Title, Description))
            }while (cursor.moveToNext())
        }
        var myadpt = adapter(this,listofnotes)
        mainbox.adapter = myadpt

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        var sv=menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(applicationContext,p0 ,Toast.LENGTH_LONG ).show()
                loadquery("%"+ p0 +"%" )
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.addnoteid -> {
                //go to add page
                var intent=Intent(this,adddnote::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    inner class adapter:BaseAdapter{
        var Context:Context?=null
        var listnoteadpt:ArrayList<note>
        constructor(Context:Context, listnoteadpt:ArrayList<note>):super(){
            this.listnoteadpt=listnoteadpt
            this.Context=Context

        }

        override fun getCount(): Int {
            return listnoteadpt.size

        }

        override fun getItem(p0: Int): Any {
            return listnoteadpt[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myview=layoutInflater.inflate(R.layout.ticket,null)
            var mynode=listnoteadpt[p0]
            myview.nametv.text=mynode.notname
            myview.destv.text=mynode.notdes
            myview.delbut.setOnClickListener(View.OnClickListener {
                var dbManager=DbManager(this.Context!!)
                val selectionArgs= arrayOf(mynode.notid.toString())
                dbManager.Delete("ID=?",selectionArgs)
                loadquery("%")

            })
            myview.editbut.setOnClickListener(View.OnClickListener {
                gotoupdate(mynode)
            })
            return myview
        }

    }

    fun gotoupdate(note: note){
        var intent=Intent(this,adddnote::class.java)
        intent.putExtra("ID",note.notid)
        intent.putExtra("Title",note.notname)
        intent.putExtra("Description",note.notdes)
        startActivity(intent)
    }
}