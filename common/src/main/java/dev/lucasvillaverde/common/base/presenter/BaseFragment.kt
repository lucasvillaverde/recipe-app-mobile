package dev.lucasvillaverde.common.base.presenter

import android.content.Context
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    lateinit var navigator: Navigator
    var actionBar: ActionBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Navigator) navigator = context
        if (context is AppCompatActivity) actionBar = context.supportActionBar
    }
}