package com.example.androidhomework.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidhomework.R
import com.example.androidhomework.adapter.InfoAdapter
import kotlinx.android.synthetic.main.fragment_info.*

class WalletInfoFragment : Fragment() {
    var adapter = InfoAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        rsv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        rsv.setHasFixedSize(true)
        rsv.adapter = adapter
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param storeId Parameter 1.
         * @return A new instance of fragment ProductListFragment.
         */
        @JvmStatic
        fun newInstance() =
            WalletInfoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}