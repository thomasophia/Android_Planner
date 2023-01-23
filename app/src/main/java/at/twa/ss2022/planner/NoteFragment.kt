package at.twa.ss2022.planner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NoteFragment : Fragment() {

    private val navigationArguments: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        tvName.text = navigationArguments.name

        val fabShare = view.findViewById<FloatingActionButton>(R.id.fabShare)
        fabShare.setOnClickListener {
            Log.e("NoteFragment", "fab")
            val shareText = view.findViewById<EditText>(R.id.etShareText)?.text.toString()
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, shareText)
            intent.type = "text/plain"
            val chooserIntent = Intent.createChooser(intent, "Note")
            startActivity(chooserIntent)

        }
    }
}