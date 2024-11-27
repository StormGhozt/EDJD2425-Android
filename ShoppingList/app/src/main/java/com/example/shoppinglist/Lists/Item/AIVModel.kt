package com.example.shoppinglist.Lists.Item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.TAG
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch

class AIVModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun addItem(listId: String, name: String, qtd: Double) {
        viewModelScope.launch {
            try {
                val item = hashMapOf(
                    "name" to name,
                    "quantity" to qtd
                )
                val listRef = db.collection("lists").document(listId)
                listRef.update("items", FieldValue.arrayUnion(item)).await()
            } catch (e: Exception) {
                Log.w(TAG, "Error adding document", e)
            }
        }
    }
}