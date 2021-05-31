package com.example.recycleviewtest1

import android.content.ContentResolver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleviewtest1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    companion object{
        var contactNameList = arrayListOf<String>()
        var contactNumberList = arrayListOf<String>()
        var contactIdList = arrayListOf<String>()
    }

    private lateinit var activityMainBinding: ActivityMainBinding
    //private lateinit var contactRowBinding: ContactRowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        //contactRowBinding = ContactRowBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        //setContentView(contactRowBinding.root)
        activityMainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        //Ustawianie separatora miedzy kontaktami
        activityMainBinding.recyclerView.addItemDecoration(DividerItemDecoration
            (activityMainBinding.recyclerView.context, DividerItemDecoration.VERTICAL))


//        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null, null)
//
//        try {
//            cursor?.moveToFirst()
//            while (!cursor!!.isAfterLast){
//                val name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
//                val id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
//                readPhoneNumber(contentResolver, id)
//                contactIdList.add(id)
//                contactNameList.add(name)
//                cursor.moveToNext()
//            }
//        }finally {
//            cursor?.close()
//        }
//
//        activityMainBinding.recyclerView.adapter = MyAdapter()
    }

    override fun onResume() {
        super.onResume()
        contactIdList.clear()
        contactNameList.clear()
        contactNumberList.clear()

        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null, null)

        try {
            cursor?.moveToFirst()
            while (!cursor!!.isAfterLast){
                val name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                val id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                readPhoneNumber(contentResolver, id)
                contactIdList.add(id)
                contactNameList.add(name)
                cursor.moveToNext()
            }
        }finally {
            cursor?.close()
        }

        activityMainBinding.recyclerView.adapter = MyAdapter()

    }
}

fun readPhoneNumber(contentResolver: ContentResolver, id: String){
    val phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", arrayOf(id), null)
    if(phoneCursor?.moveToFirst() == true){
        val number = phoneCursor.getString(
                phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
        MainActivity.contactNumberList.add(number)
    }
    else{
        MainActivity.contactNumberList.add("Empty")
    }
    phoneCursor?.close()
}