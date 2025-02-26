package ininc.foodmarket.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ininc.foodmarket.R
import ininc.foodmarket.databinding.FragmentProfileBinding
import ininc.foodmarket.model.UserModel


class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding

    private val auth=FirebaseAuth.getInstance()
    private val database=FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater,container,false)

        setUserData()
        binding.idname.isEnabled=false
        binding.idaddress.isEnabled=false
        binding.idemail.isEnabled=false
        binding.idphone.isEnabled=false


        var isEnable=false
        binding.ideditbtn.setOnClickListener {
            isEnable = !isEnable
            binding.idname.isEnabled=isEnable
            binding.idaddress.isEnabled=isEnable
            binding.idemail.isEnabled=isEnable
            binding.idphone.isEnabled=isEnable
            if (isEnable){
                binding.idname.requestFocus()
            }
        }
        binding.idsaveinfobtn.setOnClickListener {
            val name=binding.idname.text.toString()
            val email=binding.idemail.text.toString()
            val address=binding.idaddress.text.toString()
            val phone=binding.idphone.text.toString()
            updateUserData(name,email,address,phone)
        }
        return binding.root
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {
        val userId=auth.currentUser?.uid
        if(userId!=null){
            val userReference=database.getReference("user").child("buyer").child(userId)

            val userData= hashMapOf("name" to name,"email" to email,"address" to address,"phone" to phone)
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(),"Proifle updated successfully",Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Profile updation failed",Toast.LENGTH_SHORT).show()
                }

        }
    }

    private fun setUserData() {
        val userId=auth.currentUser?.uid
        if(userId!=null){
            val userReference=database.getReference("user").child("buyer").child(userId)

            userReference.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val userProfile=snapshot.getValue(UserModel::class.java)
                        if(userProfile!=null){
                            binding.idname.setText(userProfile.name)
                            binding.idaddress.setText(userProfile.address)
                            binding.idemail.setText(userProfile.email)
                            binding.idphone.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }

    companion object {

    }
}