package edu.bluejack23_1.nowlocate.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityProfileBinding
import edu.bluejack23_1.nowlocate.handlers.BottomNavigationViewHandler
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.models.User
import edu.bluejack23_1.nowlocate.viewModels.ProfileViewModel
import edu.bluejack23_1.nowlocate.views.fragments.ProfileFragment

class ProfileActivity : AppCompatActivity(), View {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var editProfileBtn: Button
    private lateinit var changePasswordBtn: Button
    private lateinit var logoutBtn: ImageButton
    private lateinit var addReportFBtn: FloatingActionButton
    private lateinit var firstNameTV: TextView
    private lateinit var lastNameTV: TextView
    private lateinit var emailTV: TextView
    private lateinit var usernameTV: TextView
    private lateinit var profileImageIV: ImageView
    private lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        val userExtra = intent.getParcelableExtra("user", User::class.java)

        viewModel.handleExtrasData(userExtra)

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler(){
        editProfileBtn = binding.btnEditProfile
        changePasswordBtn = binding.btnChangePassword
        addReportFBtn = binding.btnAddReport
        firstNameTV = binding.tvFirstName
        lastNameTV = binding.tvLastName
        emailTV = binding.tvEmail
        usernameTV = binding.tvUsername
        logoutBtn = binding.btnSignOut
        profileImageIV = binding.ivProfileImage

        profileFragment = ProfileFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentProfile, profileFragment)
            commit()
        }
        BottomNavigationViewHandler(this, binding.bottomNavigationView)
    }

    override fun eventHandler() {
        logoutBtn.setOnClickListener {
            viewModel.handleLogout()
        }

        editProfileBtn.setOnClickListener {
            IntentHelper.moveTo(this, EditProfileActivity::class.java)
        }

        changePasswordBtn.setOnClickListener {
            IntentHelper.moveTo(this, ChangePasswordActivity::class.java)
        }

        addReportFBtn.setOnClickListener {
            IntentHelper.moveTo(this, CreateReportActivity::class.java)
        }

        viewModel.image.observe(this) {
            Picasso.get().load(it).into(profileImageIV)
        }

        viewModel.activityToStart.observe(this) {
            IntentHelper.moveTo(this, it.java)
        }
    }


}