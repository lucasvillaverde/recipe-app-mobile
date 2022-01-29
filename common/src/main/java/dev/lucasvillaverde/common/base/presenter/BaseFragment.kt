package dev.lucasvillaverde.common.base.presenter

import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    lateinit var navigator: Navigator

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Navigator) navigator = context
    }
}