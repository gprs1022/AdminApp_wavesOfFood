package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.databinding.ActivityPendingOrderBinding
import com.example.adminwaveoffood.adapter.PendingOrderAdapter


class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
        val orderCustomerName = arrayListOf(

            "Jhone Deo",
            "Jane Smith",
            "Mike Johnson",
        )

        val orderQuantity = arrayListOf(
            "8",
            "7",
            "4",
        )
        val orderFoodImage = arrayListOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3)
        val adapter = PendingOrderAdapter (orderCustomerName,orderQuantity,orderFoodImage, this )

        binding.pendingOrdersRecyclerView.adapter = adapter
        binding.pendingOrdersRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}