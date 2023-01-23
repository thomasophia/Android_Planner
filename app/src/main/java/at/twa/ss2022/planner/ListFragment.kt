package at.twa.ss2022.planner

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: ListAdapter
    private lateinit var arraylist: ArrayList<ListItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvList)
        loadData()
        listAdapter = ListAdapter(arraylist)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = listAdapter

        val addButton = view.findViewById<Button>(R.id.btAddItem)
        addButton.setOnClickListener {
            val textDescription = view.findViewById<EditText>(R.id.etListItem).text.toString()
            if (textDescription.isNotEmpty()) {
                val item = ListItem(textDescription)
                listAdapter.addItem(item)
                view.findViewById<EditText>(R.id.etListItem).text.clear()
            }
        }

        val deleteButton = view.findViewById<Button>(R.id.btDeleteDone)
        deleteButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.title))
                .setMessage(resources.getString(R.string.supporting_text))

                .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                    Log.e("btDeleteDone", "canceled")
                    Toast.makeText(
                        requireContext(),
                        "\"Delete Done\" was cancelled",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                    listAdapter.deleteDoneItems()
                }
                .create()
                .show()
        }

        val saveButton = view.findViewById<Button>(R.id.btSave)
        saveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val sharedPreferences =
            requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val sharedPreferencesEditor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(arraylist)
        sharedPreferencesEditor.putString("TODO LIST", json)
        sharedPreferencesEditor.apply()
        Log.e("btSave", "List saved")
        Toast.makeText(requireContext(), "the list has been saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadData() {
        val sharedPreferences =
            requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("TODO LIST", null)
        val type = object : TypeToken<ArrayList<ListItem>>() {}.type


        if (json == null) {
            arraylist = ArrayList()
            Log.e("loadData", "initialize ArrayList")
        } else {
            arraylist = gson.fromJson(json, type)
        }
    }

}