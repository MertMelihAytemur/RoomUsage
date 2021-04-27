package com.example.roomusage.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomusage.R
import com.example.roomusage.model.User
import com.example.roomusage.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private lateinit var userViewModel : UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_add, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.buttonToAddUser.setOnClickListener {
            insertDataToDatabase()
        }
        return view;
    }


    private fun insertDataToDatabase(){
        val firstName = editTextFirstName.text.toString().trim()
        val lastName = editTextLastName.text.toString().trim()
        val age  = editTextAge.text

        if(inputCheck(firstName,lastName,age)){
            //Create user object
            val user = User(0,firstName,lastName,Integer.parseInt(age.toString()))

            // Add data to Database
            userViewModel.addUser(user)
            Toast.makeText(requireContext(),"User successfully added!",Toast.LENGTH_SHORT).show()

            //navigate to list fragment
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        }else{
            Toast.makeText(requireContext(),"Please fill out all fileds.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age : Editable): Boolean{
        return !(TextUtils.isEmpty(firstName)&&TextUtils.isEmpty(lastName)&&TextUtils.isEmpty(age))

    }
}