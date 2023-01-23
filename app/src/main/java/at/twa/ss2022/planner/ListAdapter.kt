package at.twa.ss2022.planner

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private var list: ArrayList<ListItem>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    class ListViewHolder(var listItemRootView: View) : RecyclerView.ViewHolder(listItemRootView) {
        var listItemTextView: TextView = listItemRootView.findViewById(R.id.tvDescription)
        var listItemCheckBox: CheckBox = listItemRootView.findViewById(R.id.cbDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_list_item,
                parent,
                false
            )
        )
    }

    fun addItem(item: ListItem) {
        list.add(item)
        notifyItemInserted(list.size - 1)
    }

    fun deleteDoneItems() {
        list.removeAll { item ->
            item.isChecked
        }
        notifyDataSetChanged()
    }

    private fun strikeTrough(description: TextView, isChecked: Boolean) {
        if (isChecked) {
            description.paintFlags = description.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            description.paintFlags = description.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val curItem = list[position]

        holder.listItemTextView.text = curItem.description
        holder.listItemCheckBox.isChecked = curItem.isChecked

        strikeTrough(holder.listItemTextView, curItem.isChecked)
        holder.listItemCheckBox.setOnCheckedChangeListener { _, isChecked ->
            strikeTrough(holder.listItemTextView, isChecked)
            curItem.isChecked = !curItem.isChecked
        }

        holder.listItemRootView.setOnClickListener {
            Toast.makeText(
                holder.listItemRootView.context,
                "is " + curItem.description + " already done?",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
















