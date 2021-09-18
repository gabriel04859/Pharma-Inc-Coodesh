package com.gabrielribeiro.pharma_inc_coodesh.ui.home

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.MutableLiveData
import com.gabrielribeiro.pharma_inc_coodesh.R
import com.gabrielribeiro.pharma_inc_coodesh.utils.Constants.FEMALE_QUERY
import com.gabrielribeiro.pharma_inc_coodesh.utils.Constants.MALE_QUERY

class DialogFilter (private val activity: Activity, filterSex : String = "") {


    private var dialog: AlertDialog
    private val alert = AlertDialog.Builder(activity)
    private val inflater = activity.layoutInflater
    private val view: View = inflater.inflate(R.layout.dialog_filter_layout, null)

    private val radioGroupOptions : RadioGroup = view.findViewById(R.id.rb_group_options)
    private val radioButtonAll : RadioButton = view.findViewById(R.id.radio_button_all)
    private val radioButtonMale : RadioButton = view.findViewById(R.id.radio_button_male)
    private val radioButtonFemale : RadioButton = view.findViewById(R.id.radio_button_female)
    private val autoCompleteTextViewCountries : AutoCompleteTextView = view.findViewById(R.id.auto_complete_textView_countries)

    private lateinit var textWatcher: TextWatcher
    init {
        alert.setView(view)
        dialog = alert.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    companion object {
        val filterSex = MutableLiveData<String>().apply {
            value = ""
        }

    }

    fun showDefaultDialog(setOnCheckedChangeListener: RadioGroup.OnCheckedChangeListener) {
        if (filterSex.value == "") {
            radioButtonAll.isChecked = true
        }
        if (filterSex.value == FEMALE_QUERY) {
            radioButtonFemale.isChecked = true
            radioButtonAll.isChecked = false
        }
        if (filterSex.value == MALE_QUERY) {
            radioButtonMale.isChecked = true
            radioButtonAll.isChecked = false
        }
        dialog.show()

        radioGroupOptions.setOnCheckedChangeListener { _, checkedId ->
            setOnCheckedChangeListener.onCheckedChanged(radioGroupOptions,checkedId)
        }
    }


    fun showDropDownCountries(d : TextWatcher) {
        setupAdapter()
        dialog.show()
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                d.afterTextChanged(p0)
            }

        }
        autoCompleteTextViewCountries.addTextChangedListener(textWatcher)
        clearAutoCompleteCountries(textWatcher, autoCompleteTextViewCountries)
        dialog.dismiss()
    }


    private fun setupAdapter() {
        val countries = activity.resources.getStringArray(R.array.countries)
        val arrayAdapter = ArrayAdapter(activity.applicationContext, R.layout.drop_down_item, countries)
        autoCompleteTextViewCountries.setAdapter(arrayAdapter)
    }

    private  fun clearAutoCompleteCountries(textWatcher: TextWatcher, autoCompleteTextView: AutoCompleteTextView) {
        autoCompleteTextView.removeTextChangedListener(textWatcher)
        autoCompleteTextView.setText("")
        autoCompleteTextView.addTextChangedListener(textWatcher)
    }


}