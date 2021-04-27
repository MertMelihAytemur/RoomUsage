package com.example.roomusage.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomusage.R
import com.example.roomusage.model.User
import com.example.roomusage.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        view.editTextUpdateFirstName.setText(args.currentUser.firstName)
        view.editUpdateTextLastName.setText(args.currentUser.lastName)
        view.editUpdateTextAge.setText(args.currentUser.age.toString())

        view.buttonUpdateUser.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        return view;
    }

    private fun updateItem() {
        val firstName = editTextUpdateFirstName.text.toString().trim()
        val lastName = editUpdateTextLastName.text.toString().trim()
        val age = Integer.parseInt(editUpdateTextAge.text.toString())

        if (inputCheck(firstName, lastName, editUpdateTextAge.text)) {
            //Create user object
            val updateUser = User(args.currentUser.id, firstName, lastName, age)
            //Update current user
            userViewModel.updateUser(updateUser)
            //NavigateBack to list fragment
            Toast.makeText(requireContext(), " User Sucessfully Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else
            Toast.makeText(requireContext(), "Please fill out all fileds", Toast.LENGTH_SHORT)
                .show()

    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(
            age
        ))

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            userViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "${args.currentUser.firstName} Sucessfully Deleted.", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete? ${args.currentUser.firstName}? ")
        builder.create().show()

    }

}