package com.grayimediary.testmania.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.grayimediary.testmania.R
import com.grayimediary.testmania.USER_EXTRA
import com.grayimediary.testmania.model.User
import com.grayimediary.testmania.ui.fragment.AllTestsFragment
import com.grayimediary.testmania.ui.fragment.ProfileFragment
import com.grayimediary.testmania.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity() {

    val viewModel by viewModel<MainViewModel>()
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user = intent.getSerializableExtra(USER_EXTRA) as User

        val allTestFragment = AllTestsFragment.newInstance(user.login)
        val profileFragment = ProfileFragment.newInstance(user)

        val adapter = MainPagerAdapter(supportFragmentManager, listOf(allTestFragment, profileFragment))
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)?.icon = getDrawable(R.drawable.ic_tests)
        tabs.getTabAt(1)?.icon = getDrawable(R.drawable.ic_profile)
    }

    fun deleteTest(id: Int) {
        viewModel.deleteTest(id)
        viewModel.getTests()
        viewModel.getCreatedTests(user.createdTests)
    }

    class MainPagerAdapter(
        fm: FragmentManager,
        private val fragmentList: List<Fragment>
    ) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int) = fragmentList[position]

        override fun getCount() = fragmentList.size


    }
}