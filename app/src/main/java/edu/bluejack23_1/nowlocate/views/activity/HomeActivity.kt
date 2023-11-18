package edu.bluejack23_1.nowlocate.views.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.builder.FragmentBuilder
import edu.bluejack23_1.nowlocate.databinding.ActivityHomeBinding
import edu.bluejack23_1.nowlocate.handlers.BottomNavigationViewHandler
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.interfaces.Sortable
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.viewModels.HomeViewModel
import edu.bluejack23_1.nowlocate.views.fragments.HomeFragment
import edu.bluejack23_1.nowlocate.views.fragments.HomeSearchedFragment
import edu.bluejack23_1.nowlocate.views.fragments.SearchFilterFragment

class HomeActivity : AppCompatActivity(), View {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFilterFragment: SearchFilterFragment
    private lateinit var homeSearchedFragment: HomeSearchedFragment
    private lateinit var currentFragment: Fragment
    private lateinit var homeSB: SearchBar
    private lateinit var homeSV: SearchView
    private lateinit var reportAddBtn: FloatingActionButton
    private var isAscending: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }


    override fun bindingHandler() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler() {
        homeFragment = HomeFragment()
        searchFilterFragment = SearchFilterFragment(viewModel)
        homeSearchedFragment = HomeSearchedFragment(viewModel)
        homeSB = binding.searchBar
        homeSV = binding.searchView
        reportAddBtn = binding.btnAddReport

        BottomNavigationViewHandler(this, binding.bottomNavigationView)

        setSupportActionBar(homeSB)

        FragmentBuilder(supportFragmentManager).replace(R.id.fragment_home, homeFragment).commit()

        FragmentBuilder(supportFragmentManager).replace(R.id.fragment_filter, searchFilterFragment)
            .commit()

        currentFragment = homeFragment
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_descending -> {
                if (!isAscending) {
                    item.setIcon(R.drawable.baseline_keyboard_double_arrow_up_24)
                } else {
                    item.setIcon(R.drawable.baseline_keyboard_double_arrow_down_24)
                }

                isAscending = !isAscending

                if (currentFragment == homeFragment) {
                    homeFragment.setSort(isAscending)
                } else {
                    homeSearchedFragment.setSort(isAscending)
                }
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun eventHandler() {

        homeSV.editText.addTextChangedListener { text ->
            viewModel.searchQuery.value = text.toString()
        }

        homeSV.editText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                viewModel.filterQuery.value = null
                viewModel.getReportDataSearched()

                FragmentBuilder(supportFragmentManager).replace(
                    R.id.fragment_home,
                    homeSearchedFragment
                ).commit()
                currentFragment = homeSearchedFragment

                homeSV.hide()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        viewModel.filterQuery.observe(this) {
            if (it == "" || it == null) {
                return@observe
            }
            viewModel.searchQuery.value = null
            viewModel.reportList.value = ArrayList()
            viewModel.getReportDataFiltered()

            FragmentBuilder(supportFragmentManager).replace(
                R.id.fragment_home,
                homeSearchedFragment
            ).commit()
            currentFragment = homeSearchedFragment

            homeSV.hide()
        }

        reportAddBtn.setOnClickListener {
            IntentHelper.moveTo(this, CreateReportActivity::class.java)
        }
    }
}