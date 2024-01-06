package com.example.appointmentsapp.ui.category

import CategoryViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointmentsapp.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CategoryViewModel
    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModel.fetchCategories()


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter = CategoriesAdapter(categories.data)
            binding.categoriesRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = categoriesAdapter
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}