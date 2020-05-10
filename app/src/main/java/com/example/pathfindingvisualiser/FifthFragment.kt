package com.example.pathfindingvisualiser

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import kotlinx.android.synthetic.main.fragment_fifth.*
import kotlinx.android.synthetic.main.fragment_fourth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FifthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fifth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Default).launch {
            var mediaController = MediaController(activity)
            videoView2.setMediaController(mediaController)
            val path = "android.resource://com.example.pathfindingvisualiser/"+ R.raw.dijs;
            val uri = Uri.parse(path)
            videoView2.setVideoURI(uri)
            videoView2.start()
        }
}

}
