 package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.databinding.ActivityAllItemBinding
import com.example.adminwaveoffood.adapter.MenuItemAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.model.AllMenu


 class AllItemActivity : AppCompatActivity() {

     private lateinit var  databaseReference: DatabaseReference
     private lateinit var databse : FirebaseDatabase
     private  var   menuItems: ArrayList<AllMenu> = ArrayList()


    private val binding : ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

        binding.backButton.setOnClickListener {
            finish()
        }

    }

     private fun retrieveMenuItem() {

         databse = FirebaseDatabase.getInstance()
         val foodRef: DatabaseReference = databse.reference.child("menu")

         //fetch data from database

         foodRef.addListenerForSingleValueEvent(object : ValueEventListener {




             override fun onDataChange(snapshot: DataSnapshot) {
                 // clear existing data before populating
                 menuItems.clear()

                 //loop for through each food item

                 for(foodSnapshot in snapshot.children)
                 {
                     val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                     menuItem?.let {
                         menuItems.add(it)
                     }
                 }
                 setAdapter()
             }


             override fun onCancelled(error: DatabaseError) {
                 Log.d("DatabaseError", "Error: ${error.message}")
             }

         })
     }
     private fun setAdapter(){
         val adapter = MenuItemAdapter(this@AllItemActivity, menuItems,databaseReference)
         binding.menuRecyclerView.layoutManager = LinearLayoutManager(this,)
         binding.menuRecyclerView.adapter =adapter
     }
 }