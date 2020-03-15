package com.example.githubtrending.ui.repos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrending.R
import com.example.githubtrending.utils.AppUtils
import com.example.githubtrending.viewmodel.DevsViewModel
import com.example.githubtrending.viewmodel.RepoViewModel


class RepoFragment : Fragment() {
    private lateinit var mAdapter: RepoListAdapter
    private var mRepoViewModel: RepoViewModel? = null
    private var mEditTextSearch: EditText? = null
    var mProgressBar: ProgressBar? = null
    var mRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRepoViewModel = ViewModelProviders.of(this).get(RepoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_repos, container, false)
        mRecyclerView = root.findViewById(R.id.recyclerView)
        mProgressBar = root.findViewById(R.id.progress_bar)
        mEditTextSearch = root.findViewById(R.id.editTextSearch)
        mRepoViewModel!!.init()
        if (AppUtils.isConnected(activity)) {
            mProgressBar!!.visibility = View.VISIBLE
            mRecyclerView!!.visibility = View.GONE
            mRepoViewModel!!.apiCall()
        } else {
            AppUtils.showToast(activity, getString(R.string.no_internet_msg))
        }
        observeData()
        bindSearchView()
        return root
    }

    private fun bindSearchView() {
        mEditTextSearch!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (mAdapter != null) mAdapter.getFilter().filter(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun observeData() {
        mRepoViewModel!!.repoLiveData.observe(
            viewLifecycleOwner,
            Observer {
                mProgressBar!!.visibility = View.GONE
                mRecyclerView!!.visibility = View.VISIBLE
                val manager = LinearLayoutManager(context)
                mAdapter = RepoListAdapter(context, it!!)
                mRecyclerView!!.layoutManager = manager
                mRecyclerView!!.adapter = mAdapter
            }
        )
    }
}