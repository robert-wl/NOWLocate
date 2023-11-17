package edu.bluejack23_1.nowlocate.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.adapters.ConversationAdapter
import edu.bluejack23_1.nowlocate.databinding.FragmentConversationBinding
import edu.bluejack23_1.nowlocate.interfaces.ViewFragment
import edu.bluejack23_1.nowlocate.viewModels.ConversationFragmentViewModel

class ConversationFragment : Fragment(), ViewFragment {

    private lateinit var binding: FragmentConversationBinding
    private lateinit var conversationRV: RecyclerView
    private lateinit var viewModel: ConversationFragmentViewModel
    private lateinit var conversationAdapter: ConversationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConversationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ConversationFragmentViewModel::class.java]
        binding.viewModel = viewModel

        elementHandler()
        eventHandler()

        viewModel.getUserChats()

    }


    override fun elementHandler() {
        conversationRV = binding.rvConversation
        conversationAdapter = ConversationAdapter(requireContext())
        conversationAdapter.conversationList = ArrayList()
        conversationRV.layoutManager = LinearLayoutManager(requireContext())
        conversationRV.adapter = conversationAdapter

    }

    override fun eventHandler() {
        viewModel.chatDocs.observe(viewLifecycleOwner){
            viewModel.updateChatData(it)
        }
        viewModel.chats.observe(viewLifecycleOwner){
            conversationAdapter.conversationList = it
            conversationAdapter.notifyDataSetChanged()
        }
    }

}