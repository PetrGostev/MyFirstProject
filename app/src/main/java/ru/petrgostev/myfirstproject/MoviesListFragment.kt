package ru.petrgostev.myfirstproject

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesListBinding

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private var viewBinding: FragmentMoviesListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMoviesListBinding.bind(view)

        viewBinding?.image?.setOnClickListener{
            showMoviesDetailsFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun showMoviesDetailsFragment() {
        val supportFragmentManager = activity?.supportFragmentManager
        supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fame, MoviesDetailsFragment())
            ?.addToBackStack("MoviesDetailsFragment")
            ?.commit()
    }
}