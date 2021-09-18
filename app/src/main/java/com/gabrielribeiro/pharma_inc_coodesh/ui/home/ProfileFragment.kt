package com.gabrielribeiro.pharma_inc_coodesh.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.gabrielribeiro.pharma_inc_coodesh.R
import com.gabrielribeiro.pharma_inc_coodesh.data.model.UserResponse
import com.gabrielribeiro.pharma_inc_coodesh.databinding.FragmentHomeBinding
import com.gabrielribeiro.pharma_inc_coodesh.databinding.FragmentProfileBinding
import com.gabrielribeiro.pharma_inc_coodesh.utils.BackendUtils
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var userResponse: UserResponse

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    companion object {
        const val ARG_USER = "arg_user"

        fun newIntent(userResponse : UserResponse) = Bundle().apply {
            putParcelable(ARG_USER, userResponse)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().let {
            userResponse = (it.getParcelable(ARG_USER) as UserResponse?)!!
        }

        with(userResponse) {
            Glide.with(requireActivity())
                .load(picture.large)
                .placeholder(R.drawable.ic_user_placeholder)
                .error(R.drawable.ic_user_placeholder)
                .circleCrop()
                .into(binding.imageUserProfile)


            val dateBirthDay : Date? = BackendUtils.parseDateTime(dob.date)
            val formattedBirthDay : String? = dateBirthDay?.let { BackendUtils.formatBirthDay(it) }

            binding.textUserName.text = name.fullName
            binding.textUserEmail.text = email
            binding.textUserGender.text = formattedGender()
            binding.textUserPhone.text = phone
            binding.textUserBirthDay.text = dob.date
            binding.textUserNationality.text = location.country
            binding.textUserAddress.text = location.formattedAddress
            binding.textUserBirthDay.text = formattedBirthDay
            if (id.value != null) {
                binding.textIdLabel.visibility = View.VISIBLE
                binding.textUserId.visibility = View.VISIBLE
                binding.textUserId.text = id.value
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}