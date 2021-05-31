package com.example.recycleviewtest1

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MyAdapter : RecyclerView.Adapter<myViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val contactRow = layoutInflater.inflate(R.layout.contact_row, parent, false)
        return myViewHolder(contactRow)

    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val name = holder.view.findViewById<TextView>(R.id.contactName_TextView)

        val number = holder.view.findViewById<TextView>(R.id.phoneNumber_TextView)

        val call_Button = holder.view.findViewById<Button>(R.id.call_Button)

        val sms_Button = holder.view.findViewById<Button>(R.id.sms_Button)

//        name.setText(ContactDataBase.contactList[position].toUpperCase())
//        number.setText(ContactDataBase.contactNumberList[position])
        name.setText(MainActivity.contactNameList[position])
        number.setText(MainActivity.contactNumberList[position])

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                val editIntent = Intent()
                editIntent.data = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, MainActivity.contactIdList[position].toLong())
                editIntent.action = Intent.ACTION_EDIT

                startActivity(holder.itemView.context, editIntent, null)

                return false
            }
        })

        sms_Button.setOnClickListener {
            val smsIntent = Intent()
            if(MainActivity.contactNumberList[position].equals("Empty")){
                Toast.makeText(holder.itemView.context, "Wrong number", Toast.LENGTH_SHORT).show()
            }
            else {
                smsIntent.data = Uri.parse("sms:${MainActivity.contactNumberList[position]}")
                smsIntent.action = Intent.ACTION_VIEW
                startActivity(holder.itemView.context, smsIntent, null)
            }
        }

        call_Button.setOnClickListener {
            val callIntent = Intent()
            if (MainActivity.contactNumberList[position].equals("Empty")) {
                Toast.makeText(holder.itemView.context, "Wrong number", Toast.LENGTH_SHORT).show()
            } else {
                callIntent.data = Uri.parse("tel:${MainActivity.contactNumberList[position]}")
                callIntent.action = Intent.ACTION_CALL
                startActivity(holder.itemView.context, callIntent, null)
            }
        }
    }

    override fun getItemCount(): Int {
        //return ContactDataBase.contactList.size
        return MainActivity.contactNameList.size
    }


}

class myViewHolder(val view: View) : RecyclerView.ViewHolder(view)