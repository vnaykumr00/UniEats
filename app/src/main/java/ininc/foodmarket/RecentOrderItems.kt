package ininc.foodmarket

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ininc.foodmarket.adapter.RecentBuyAdapter
import ininc.foodmarket.databinding.ActivityRecentOrderItemsBinding
import ininc.foodmarket.model.OrderDetails


class RecentOrderItems : AppCompatActivity() {
    private val binding:ActivityRecentOrderItemsBinding  by lazy{
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames:ArrayList<String>
    private lateinit var allFoodImages:ArrayList<String>
    private lateinit var allFoodPrices:ArrayList<String>
    private lateinit var allFoodQuantities:ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backbtn.setOnClickListener{
            finish()
        }
        val recentOrderItems=intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<OrderDetails>
        recentOrderItems?.let {orderDetails ->
            if (orderDetails.isNotEmpty()){
                val recentOrderItem=orderDetails[0]
                allFoodNames=recentOrderItem.foodNames as ArrayList<String>
                allFoodImages=recentOrderItem.foodImages as ArrayList<String>
                allFoodPrices=recentOrderItem.foodPrices as ArrayList<String>
                allFoodQuantities=recentOrderItem.foodQuantities as ArrayList<Int>

            }
        }
        setAdapter()

    }

    private fun setAdapter() {
        val rv=binding.idrecentrv
        rv.layoutManager=LinearLayoutManager(this)
        val adapter=RecentBuyAdapter(this,allFoodNames,allFoodImages,allFoodPrices,allFoodQuantities)
        rv.adapter=adapter
    }
}