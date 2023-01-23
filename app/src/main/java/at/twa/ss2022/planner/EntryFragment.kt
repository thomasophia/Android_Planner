package at.twa.ss2022.planner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController


class EntryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<EditText>(R.id.etName)
        etName.doAfterTextChanged {
            Log.e("EditText", "count")
        }

        val sharedPreferences =
            requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val storedName = sharedPreferences.getString("NAME", "")
        etName.setText(storedName)

        val buttonList = view.findViewById<Button>(R.id.btList)
        buttonList.setOnClickListener {
            Log.e("btList", "onClick")

            val navHostFragment = findNavController()
            val action = EntryFragmentDirections.actionEntryFragmentToListFragment()
            navHostFragment.navigate(action)
        }

        val buttonNote = view.findViewById<Button>(R.id.btNote)
        buttonNote.setOnClickListener {
            Log.e("btNote", "onClick")

            val content = etName.text.toString()

            val navHostFragment = findNavController()
            val action = EntryFragmentDirections.actionEntryFragmentToNoteFragment(content)
            navHostFragment.navigate(action)
            Toast.makeText(requireContext(), "write a Note to yourself", Toast.LENGTH_SHORT).show()
        }

        val imageView = view.findViewById<ImageView>(R.id.ivEntry)
        imageView.setOnClickListener {

            val permissionRequestCode = 1000

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("EntryFragment", "permission already granted")
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    permissionRequestCode
                )
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    permissionRequestCode
                )
            }

        }

        val cbRememberMe = view.findViewById<CheckBox>(R.id.cbRememberMe)
        cbRememberMe.setOnClickListener {

            val sharedPrefs =
                requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                putString("NAME", etName.text.toString())
                apply()

                Log.e("cbRememberMe", "name has been saved")
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val permissionRequestCode = 1000
        if (requestCode == permissionRequestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("EntryFragment", "permission granted")
        } else {
            Log.e("EntryFragment", "permission denied")
            Toast.makeText(requireContext(), "permission denied", Toast.LENGTH_SHORT).show()
        }
    }

}