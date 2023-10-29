package edu.bluejack23_1.nowlocate.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityHomeBinding
import edu.bluejack23_1.nowlocate.handlers.BottomNavigationViewHandler
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.viewModels.HomeViewModel
import edu.bluejack23_1.nowlocate.views.fragments.HomeFragment
import edu.bluejack23_1.nowlocate.views.fragments.HomeSearchedFragment
import edu.bluejack23_1.nowlocate.views.fragments.SearchFilterFragment

class HomeActivity : AppCompatActivity(), View {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var homeFragment : Fragment
    private lateinit var searchFilterFragment : Fragment
    private lateinit var homeSearchedFragment : Fragment
    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var homeSV : SearchView
    private lateinit var reportAddBtn: FloatingActionButton
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
        homeSV = binding.svHome
        reportAddBtn = binding.btnAddReport

        BottomNavigationViewHandler(this, binding.bottomNavigationView)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentHome, homeFragment)
            commit()
        }
    }

    override fun eventHandler() {
        homeSV.setOnSearchClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentHome, searchFilterFragment)
                commit()
            }
        }

        homeSV.setOnCloseListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentHome, homeFragment)
                commit()
            }
            false
        }

        homeSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchQuery.value = query
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentHome, homeSearchedFragment)
                    commit()
                }
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchQuery.value = newText
                return true
            }

        })

        reportAddBtn.setOnClickListener {
            IntentHelper.moveTo(this, CreateReportActivity::class.java)
        }
    }
}