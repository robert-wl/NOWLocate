package edu.bluejack23_1.nowlocate.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityConversationBinding
import edu.bluejack23_1.nowlocate.handlers.BottomNavigationViewHandler
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.viewModels.ConversationViewModel
import edu.bluejack23_1.nowlocate.views.fragments.ConversationFragment

class ConversationActivity : AppCompatActivity(), View {

    private lateinit var binding: ActivityConversationBinding
    private lateinit var viewModel: ConversationViewModel
    private lateinit var conversationFragment: ConversationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityConversationBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ConversationViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler() {
        conversationFragment = ConversationFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentConversation, conversationFragment)
            commit()
        }

        BottomNavigationViewHandler(this, binding.bottomNavigationView)
    }

    override fun eventHandler() {
        viewModel.searchQuery.observe(this){
            conversationFragment.handleSearch(it)
        }
    }
}