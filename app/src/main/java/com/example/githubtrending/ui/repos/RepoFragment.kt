package com.example.githubtrending.ui.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.githubtrending.R
import com.example.githubtrending.viewmodel.RepoViewModel

class RepoFragment : Fragment() {

    private lateinit var repoViewModel: RepoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        repoViewModel =
                ViewModelProviders.of(this).get(RepoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_repos, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        repoViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}
