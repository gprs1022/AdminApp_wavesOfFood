package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.databinding.ActivityOutForDeliveryBinding
import com.example.adminwaveoffood.adapter.DeliveryAdapter


class OutForDeliveryActivity : AppCompatActivity() {
    private val binding : ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
  binding.backButton.setOnClickListener {
      finish()
  }
        val customerName = arrayListOf(

            "Jhone Deo",
            "Jane Smith",
            "Mike Johnson",
        )

        val moneyStatus = arrayListOf(
            "recevied",
            "notRecevied",
            "Pending",
        )
        val adapter = DeliveryAdapter(customerName,moneyStatus)
        binding.deliveryRecyclerView.adapter = adapter
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(this)


    }
}