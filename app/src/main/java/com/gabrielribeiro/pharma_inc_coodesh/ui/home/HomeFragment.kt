package com.gabrielribeiro.pharma_inc_coodesh.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gabrielribeiro.pharma_inc_coodesh.BaseViewModel
import com.gabrielribeiro.pharma_inc_coodesh.R
import com.gabrielribeiro.pharma_inc_coodesh.data.model.UserResponse
import com.gabrielribeiro.pharma_inc_coodesh.databinding.FragmentHomeBinding
import com.gabrielribeiro.pharma_inc_coodesh.ui.home.DialogFilter.Companion.filterSex
import com.gabrielribeiro.pharma_inc_coodesh.utils.BackendUtils
import com.gabrielribeiro.pharma_inc_coodesh.utils.Constants.FEMALE_QUERY
import com.gabrielribeiro.pharma_inc_coodesh.utils.Constants.MALE_QUERY
import com.gabrielribeiro.pharma_inc_coodesh.utils.Resource

class HomeFragment : Fragment(), HomeAdapter.OnResultClickListener {
    private lateinit var viewModel : BaseViewModel
    private var limitSizeUserList = 50
    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeAdapter : HomeAdapter
    private lateinit var dialogFilter : DialogFilter

    private var userArray = listOf<UserResponse>()
    private var filteredUserArray = listOf<UserResponse>()
    private val listLock = Any()

    private val binding get() = _binding!!

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel = (activity as HomeActivity).viewModel

        observerViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        homeAdapter = HomeAdapter(this)
        binding.recyclerViewHome.adapter = homeAdapter
        dialogFilter = DialogFilter(requireActivity())

        setupHooks()

    }

    private fun setupHooks() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateUserList()
            }

            override fun afterTextChanged(p0: Editable?) {
                filterSex.value = ""
            }


        })

        binding.recyclerViewHome.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.recyclerViewHome.canScrollVertically(1)){
                    limitSizeUserList += 50
                    if (filterSex.value != "") {
                        filterSex.value?.let { viewModel.getUser(limitSizeUserList, gender = it) }
                    } else {
                        viewModel.getUser(limitSizeUserList)
                    }

                }
            }
        })

        binding.textError.setOnClickListener {
            viewModel.getUser(limitSizeUserList)
        }

    }

    private fun observerViewModel() {
        viewModel.resultResponse.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.includeLoading.progressIndicator.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    binding.includeLoading.progressIndicator.visibility = View.GONE
                    binding.textError.visibility = View.VISIBLE
                    Log.d(TAG, "onCreate: Failure ${result.exception}")
                }
                is Resource.Success -> {
                    Log.d(TAG, "onCreate: Success { result.data?.userResponses!!.size}")

                    binding.textError.visibility = View.GONE
                    setUserList(result.data?.userResponses!!)
                    updateUserList()
                    binding.includeLoading.progressIndicator.visibility = View.GONE
                }
            }
        })
    }

    fun updateUserList() {
        synchronized(listLock) {
            filteredUserArray = userArray.filter {
                it.name.fullName.contains(binding.editTextSearch.text.toString().trim(), ignoreCase = true)
            }
            filterSex.value = ""
            homeAdapter.submitList(filteredUserArray)
        }
    }

    private fun setUserList(userList: List<UserResponse>) {
        synchronized(listLock) {
            userArray = userList
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_filter) {
            dialogFilter.showDropDownCountries(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    filteredUserArray = filteredUserArray.filter { it.nat == BackendUtils.initialsCountry(text.toString())}
                    homeAdapter.submitList(filteredUserArray)
                }

            })
            dialogFilter.showDefaultDialog { _, id ->
                when (id) {
                    R.id.radio_button_female -> {
                        updateSexAdapter(FEMALE_QUERY)
                    }
                    R.id.radio_button_male -> {
                        updateSexAdapter(MALE_QUERY)
                    }
                    else -> {
                        viewModel.getUser(limitSizeUserList)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateSexAdapter(gender: String) {
        homeAdapter.submitList(filteredUserArray.filter { it.gender == gender })
        filterSex.value = gender
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResultClick(userResponse: UserResponse) {
        findNavController().navigate(R.id.action_navigation_home_to_profileFragment, ProfileFragment.newIntent(userResponse) )
    }
}