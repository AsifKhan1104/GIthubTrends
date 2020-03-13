package com.example.githubtrending.ui.devs

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


class DevsFragment : Fragment() {
    private lateinit var mAdapter: DevsListAdapter
    private var mDevsViewModel: DevsViewModel? = null
    private var mEditTextSearch: EditText? = null
    var mProgressBar: ProgressBar? = null
    var mRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDevsViewModel = ViewModelProviders.of(this).get(DevsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_devs, container, false)
        mRecyclerView = root.findViewById(R.id.recyclerView)
        mProgressBar = root.findViewById(R.id.progress_bar)
        mEditTextSearch = root.findViewById(R.id.editTextSearch)
        mDevsViewModel!!.init()
        if (AppUtils.isConnected(activity)) {
            mProgressBar!!.visibility = View.VISIBLE
            mRecyclerView!!.visibility = View.GONE
            mDevsViewModel!!.apiCall()
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
        mDevsViewModel!!.devsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                mProgressBar!!.visibility = View.GONE
                mRecyclerView!!.visibility = View.VISIBLE
                val manager = LinearLayoutManager(context)
                mAdapter = DevsListAdapter(context, it!!)
                mRecyclerView!!.layoutManager = manager
                mRecyclerView!!.adapter = mAdapter
            }
        )
    }
}