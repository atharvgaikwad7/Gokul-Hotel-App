package com.example.wavesoffood.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.wavesoffood.LoginActivity5
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentProfileBinding
import com.example.wavesoffood.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ProfileFragment : Fragment() {


    private lateinit var binding:FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private val database=FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        setUserData()

        binding.saveInfobutton.setOnClickListener {
            val name  =binding.Name.text.toString()
            val address  =binding.Address.text.toString()
            val email  =binding.Email.text.toString()
            val phone  =binding.Phone.text.toString()

            updateUserData(name,email,address,phone)
        }
        return binding.root

        // Set up the sign-out button
        val signOutButton: Button = view.findViewById(R.id.sign_out_button)
        signOutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), LoginActivity5::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }

    @SuppressLint("SuspiciousIndentation")
    private fun updateUserData(name: String, email: String, address: String, phone: String) {
     val userId=auth.currentUser?.uid
        if (userId!=null){
            val userReference=database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone

            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(),"Profile update Successfuly 😁",Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Profile update Failed 🥲",Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun setUserData() {
        val userId=auth.currentUser?.uid
        if(userId!=null){
            val userReference  =database.getReference("user").child(userId)

            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val userProfile =snapshot.getValue(UserModel::class.java)
                        if (userProfile!=null){
                            binding.Name.setText(userProfile.name)
                            binding.Address.setText(userProfile.address)
                            binding.Email.setText(userProfile.email)
                            binding.Phone.setText(userProfile.phone)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
