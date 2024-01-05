package com.example.appointmentsapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appointmentsapp.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    private lateinit var viewModel: CategoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        val binding = FragmentCategoryBinding.inflate(inflater, container, false)

        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            // Update your UI here with the categories data
            // For example, you could use a RecyclerView with an adapter to display the categories in a table format
        })

        viewModel.fetchCategories()

        return binding.root
    }
}