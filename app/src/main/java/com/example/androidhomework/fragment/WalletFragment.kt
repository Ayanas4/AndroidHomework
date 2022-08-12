package com.example.androidhomework.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.androidhomework.R
import com.example.androidhomework.model.UserinfoModel
import com.example.androidhomework.adapter.CategoryFragmentAdapter
import com.example.androidhomework.http.*
import com.example.androidhomework.utils.Constants
import com.example.androidhomework.utils.UserInfoManager
import com.example.androidhomework.utils.ZoomOutPageTransformer
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_wallet.*
import java.util.Calendar.getInstance
import java.util.HashMap

class WalletFragment(token: String) : Fragment() {
    var usertoken = token
    private var mDrawer: DrawerLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()

    }

    private fun initView() {
        try {
            mDrawer = dLayout
            setupDrawerContent(nvView)

            var list = ArrayList<String>()
            list.add("Coin")
            list.add("Coupon")

            val categoryFragmentAdapter =
                CategoryFragmentAdapter(requireActivity())

            var list1 = mutableListOf<Fragment>()

            for (i in 0 until list.size) {
                list1.add(
                    WalletInfoFragment.newInstance()
                )
            }

            viewPager1.offscreenPageLimit = 1
            viewPager1.adapter = categoryFragmentAdapter
            categoryFragmentAdapter.setPages(list1)

            viewPager1.setPageTransformer(ZoomOutPageTransformer())

            TabLayoutMediator(tabLayout, viewPager1) { tab, position ->
                tab.text = list[position]
                Log.e("position/123654", position.toString())
            }.attach()

            getUserInfo()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initClick() {
        iv_setting.setOnClickListener {
            dLayout.openDrawer(GravityCompat.END)
        }
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            selectDrawerItem(it)
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        mDrawer?.closeDrawers()
        when (menuItem.itemId) {
            R.id.logout -> {
                activity?.let {
                    UserInfoManager.getInstance().setToken(requireContext(), "")
                    activity?.finish()
                }
            }
        }

        return true
    }

    fun getUserInfo() {
        try {
            ApiGetQuery(
                requireContext(),
                "https://inner.ixensor.com/InterviewAPI/android/interview.php?api=get_user_info&token=${usertoken}",
                HashMap(),
                object : HttpCallBack {
                    override fun onSuccess(jsonStr: String?) {
                        val gson =
                            GsonBuilder().registerTypeAdapterFactory(MyTypeAdapterFactory<Any?>())
                                .create()
                        val model = gson.fromJson(jsonStr, UserinfoModel::class.java)
                        val status = model.status
                        if (status == Constants.API_STATUS_SUCCESS) {
                            val username = model.user_info.first_name + model.user_info.last_name
                            tv_username.text = username

                        } else {
                            runOnUI {
                                Toast.makeText(requireContext(), "fail", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFail(msg: String?) {
                        Toast.makeText(requireContext(), "fail", Toast.LENGTH_LONG).show()
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
