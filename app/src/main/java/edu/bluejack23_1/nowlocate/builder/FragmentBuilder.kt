package edu.bluejack23_1.nowlocate.builder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentBuilder(private val fragmentManager: FragmentManager) {
    private var container: Int? = null
    private var fragment: Fragment? = null
    fun replace(container: Int, fragment: Fragment) = apply {
        this.container = container
        this.fragment = fragment
    }
    fun commit() {
        fragmentManager.beginTransaction().replace(container!!, fragment!!).commit()
    }


}