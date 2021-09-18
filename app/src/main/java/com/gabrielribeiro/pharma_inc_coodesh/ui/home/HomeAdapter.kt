package com.gabrielribeiro.pharma_inc_coodesh.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabrielribeiro.pharma_inc_coodesh.R
import com.gabrielribeiro.pharma_inc_coodesh.data.model.UserResponse

class HomeAdapter(private val onResultClickListener: OnResultClickListener) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private var resultResponse = listOf<UserResponse>()

    fun submitList(userResponse: List<UserResponse>) {
        resultResponse = userResponse
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewUserItem: ImageView = itemView.findViewById(R.id.image_view_user_item)
        private val textViewNameItem: TextView = itemView.findViewById(R.id.text_view_name_item)
        private val textViewEmailItem: TextView = itemView.findViewById(R.id.text_view_email_item)
        private val textViewSexItem: TextView = itemView.findViewById(R.id.text_view_sex_item)
        private val textViewAgeItem: TextView = itemView.findViewById(R.id.text_view_age_item)

        fun bind(user: UserResponse) {
            with(user) {
                Glide.with(itemView.context)
                    .load(picture.large)
                    .placeholder(R.drawable.ic_user_placeholder)
                    .error(R.drawable.ic_user_placeholder)
                    .circleCrop()
                    .into(imageViewUserItem)

                textViewNameItem.text = name.fullName
                textViewEmailItem.text = email
                textViewSexItem.text = HtmlCompat.fromHtml(
                    itemView.context.getString(R.string.text_gender, formattedGender()),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                textViewAgeItem.text = HtmlCompat.fromHtml(
                    itemView.context.getString(R.string.text_age, dob.age.toString()),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_user_home, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val result = resultResponse[position]

        holder.bind(result)

        holder.itemView.setOnClickListener {
            onResultClickListener.onResultClick(result)
        }
    }

    override fun getItemCount(): Int = resultResponse.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    interface OnResultClickListener {
        fun onResultClick(userResponse: UserResponse)
    }
}