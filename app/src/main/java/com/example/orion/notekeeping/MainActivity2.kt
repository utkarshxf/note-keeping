package com.example.orion.notekeeping

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.add_note.*
import java.lang.Exception

class adddnote : AppCompatActivity() {
    val bdtable="Notes"
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note)


        try {
            var bundle:Bundle=intent.extras!!
            id=bundle.getInt("ID",0)
            if (id!=0)
                titleid.setText(bundle.getString("Title"))
            desid.setText(bundle.getString("Description").toString())

        }catch (ex:Exception){}

    }

    fun butdone(view: View) {
        var dbmeneger=DbManager(this)
        var value=ContentValues()
        value.put("title",titleid.text.toString())
        value.put("description",desid.text.toString())
        if(id==0){
        val ID = dbmeneger.insert(value)
        if(ID>0){
            Toast.makeText(this,"note is added",Toast.LENGTH_LONG).show()
            finish()
        }else{Toast.makeText(this,"note is added",Toast.LENGTH_LONG).show()}
        }else
        {
            var selectionArs= arrayOf(id.toString())
            val ID = dbmeneger.Update(value,"ID=?",selectionArs)
            if(ID>0){
                Toast.makeText(this,"note is added",Toast.LENGTH_LONG).show()
                finish()
            }else{Toast.makeText(this,"note is added",Toast.LENGTH_LONG).show()}

        }
    }


}