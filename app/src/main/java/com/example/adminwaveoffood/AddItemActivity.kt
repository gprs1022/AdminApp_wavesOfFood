package com.example.adminwaveoffood

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminwaveoffood.databinding.ActivityAddItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

import com.model.AllMenu


class   AddItemActivity : AppCompatActivity() {

    //Food item Details
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private  var foodImageUri: Uri? =null

    // Firebase

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding : ActivityAddItemBinding by lazy {

        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
      //intilization Firebase

        auth = FirebaseAuth.getInstance()

        //Intialize Firebase database instance

        database = FirebaseDatabase.getInstance()

         binding.AddItemButton.setOnClickListener {

             //get Data From Filed
             foodName = binding.foodName.text.toString().trim()
             foodPrice = binding.foodPrice.text.toString().trim()
             foodDescription = binding.description.text.toString().trim()
             foodIngredient = binding.Ingredents.text.toString().trim()


             if(!(foodName.isBlank()||foodPrice.isBlank()||foodDescription.isBlank()||foodIngredient.isBlank()))
             {
                 uploadData()
                 Toast.makeText(this,"Fill all Details", Toast.LENGTH_SHORT).show()
                 finish()

             } else{
                 Toast.makeText(this, "Fill all the Details", Toast.LENGTH_SHORT).show()
             }
         }

        binding.selectedImage.setOnClickListener {
            pickImage.launch("image/* ")
        }



        binding.backButton.setOnClickListener {
            finish()
        }

    }

    private fun uploadData() {
        //get a referance to the menu node in the database
        val menuRef = database.getReference("menu")

        //Generate a unique key for the new menu item

        val newItemKey = menuRef.push().key

                if(foodImageUri != null){
                    val storageRef = FirebaseStorage.getInstance().reference
                    val imageRef = storageRef.child("menu_imageS/${newItemKey}.jpg")
                    val uploadTask = imageRef.putFile(foodImageUri!!)

                    uploadTask.addOnSuccessListener {
                        imageRef.downloadUrl.addOnSuccessListener {
                            downloadUrl ->
                            //create a new menu item
                            val newItem = AllMenu(

                                foodName = foodName,
                                foodPrice = foodPrice,
                                foodDescription = foodDescription,
                                foodIngredient = foodIngredient,
                                foodImage = downloadUrl.toString(),

                            )

                            newItemKey?.let{
                                key ->

                                menuRef.child(key).setValue(newItem).addOnSuccessListener {

                                    Toast.makeText(this, "Data Uploaded successfully", Toast.LENGTH_SHORT).show()
                                }

                                    .addOnSuccessListener {
                                        Toast.makeText(this,"Data Uploaded  failed", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }


                    }  .addOnSuccessListener {
                        Toast.makeText(this,"Image Upload failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this,"Please Select an Image", Toast.LENGTH_SHORT).show()
                }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
            if(uri != null){
                binding.selectedImage.setImageURI(uri)
                foodImageUri = uri
            }
        }

}