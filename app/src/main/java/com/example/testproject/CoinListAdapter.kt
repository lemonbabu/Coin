package com.example.testproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CoinListAdapter(private var onItemClickListener: OnCustomerClickListener): RecyclerView.Adapter<CoinListAdapter.MyViewHolder>(){
    private var customerList: ArrayList<DataModelItem> = ArrayList()

    fun submitList(list: List<DataModelItem>){
        val oldList = customerList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            BookDiffCallBack(
                oldList,
                list
            )
        )
        customerList = list as ArrayList<DataModelItem>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_coin_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoinListAdapter.MyViewHolder, position: Int) {
        val customer = customerList[position]

        holder.name.text = customer.name
        holder.phone.text = customer.symbol
        holder.shop.text = customer.id
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tvCustomerName)
        var phone: TextView = view.findViewById(R.id.tvCustomerPhone)
        var shop: TextView = view.findViewById(R.id.tvCustomerShop)
    }

    class BookDiffCallBack(
        private var oldCustomerList: List<DataModelItem>,
        private var newCustomerList: List<DataModelItem>
    ): DiffUtil.Callback(){

        override fun getOldListSize(): Int {
            return oldCustomerList.size
        }

        override fun getNewListSize(): Int {
            return newCustomerList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldCustomerList[oldItemPosition].id == newCustomerList[newItemPosition].id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCustomerList[oldItemPosition] == newCustomerList[newItemPosition]
        }

    }

    interface OnCustomerClickListener{
        fun onCustomerClickListener(id: Int)
    }
}