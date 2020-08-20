package com.gvetri.kotlin.videolibrary.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<LottieAnimationView>(R.id.rocketAnimation)
            .addAnimatorUpdateListener { animation ->
                animation.doOnEnd {
                    if (findNavController().currentDestination?.id == R.id.splashFragment) {
                        findNavController().navigate(R.id.action_splashFragment_to_home_graph)
                    }
                }
            }
    }
}
