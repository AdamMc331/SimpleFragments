package com.joeracosta.library.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Joe on 8/14/2017.
 * Meant to be a shell map activity that has a map of SimpleFragments. Back presses etc are handled for you.
 * The back presses will be sent to the SimpleFragment thats currently shown in the map and handled there. If it's not handled
 * It will be sent to this activity. You shouldn't inflate a layout here inside the fragment container that is meant to be visible
 * to the user.
 */
abstract class FragmentMapActivity : AppCompatActivity() {

    protected var mCurrentFragment: SimpleFragment? = null
        private set
    private var mCurrentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.getString(CURRENT_FRAG_TAG)?.let {
            mCurrentFragmentTag = it
            mCurrentFragment = supportFragmentManager.findFragmentByTag(mCurrentFragmentTag) as SimpleFragment
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(CURRENT_FRAG_TAG, mCurrentFragmentTag)
        super.onSaveInstanceState(outState)
    }

    fun hasFragments(): Boolean {
        return supportFragmentManager.fragments.size > 0
    }

    /**
     * Show a fragment in the map
     * @param fragmentToAdd New Instance of the Fragment you want
     * @param fragmentContainerId container Id for the fragment
     * @param tag tag of the fragment transaction. If you want to show the same fragment that's
     * already added, just make sure the tag is correct and it won't use the new instance
     */
    fun showFragmentInMap(fragmentToAdd: SimpleFragment, fragmentContainerId: Int,
                          tag: String) {
        var fragmentToActuallyAdd = fragmentToAdd

        supportFragmentManager.findFragmentByTag(tag)?.let{
            fragmentToActuallyAdd = it as SimpleFragment
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction().apply {
            if (mCurrentFragment != null) {
                hide(mCurrentFragment)
            }
            if (fragmentToActuallyAdd.isAdded) {
                show(fragmentToActuallyAdd)
            } else {
                fragmentToActuallyAdd.setAtForefront(true)
                add(fragmentContainerId, fragmentToActuallyAdd, tag)
            }
        }

        mCurrentFragmentTag = tag
        mCurrentFragment = fragmentToActuallyAdd

        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        if (!handleBackPress()) {
            super.onBackPressed()
        }
    }

    /**
     * Passes the back press to children fragments so they can consume it if they'd like
     * @return whether or not the back press was consumed by a child fragment
     */
    protected fun handleBackPress() : Boolean{
        return mCurrentFragment?.onSimpleBackPressed() ?: false
    }

}

private const val CURRENT_FRAG_TAG = "com.joeracosta.current_frag_tag_activity_map"