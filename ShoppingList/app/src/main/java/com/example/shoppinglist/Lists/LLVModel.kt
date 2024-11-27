package com.example.shoppinglist.Lists

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.ListItems
import com.example.shoppinglist.TAG
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

data class ListListsState(
    val listItemsList : List<ListItems> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class LLVModel : ViewModel() {

    var state = mutableStateOf(ListListsState())
        private set


    fun getLists() {

        val db = Firebase.firestore
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        db.collection("lists")
            //.whereEqualTo("capital", true)
            .get()
            .addOnSuccessListener { documents ->
                val listItemsList = arrayListOf<ListItems>()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val listItem = document.toObject(ListItems::class.java)
                    listItem.docId = document.id
                    listItemsList.add(listItem)
                }
                state.value = state.value.copy(
                    listItemsList = listItemsList
                )
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }


    }

    fun addList(list: ListItems) {
        val db = Firebase.firestore
        db.collection("lists")
            .add(list)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                getLists() // Refresh the list after adding a new item
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun removeList(list: ListItems) {
        val db = Firebase.firestore
        db.collection("lists")
            .document(list.docId!!)
            .delete()
            .addOnSuccessListener {
                // Update the state after successful deletion
                val updatedList = state.value.listItemsList.toMutableList()
                updatedList.remove(list)
                state.value = state.value.copy(listItemsList = updatedList)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)

            }
    }
}