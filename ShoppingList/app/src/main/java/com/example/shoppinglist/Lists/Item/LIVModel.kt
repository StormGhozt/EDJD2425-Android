package com.example.shoppinglist.Lists.Item

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.example.shoppinglist.Items
import com.example.shoppinglist.TAG
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class ListItemsState(
    val items: List<Items> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class LIVModel : ViewModel() {

    var state = mutableStateOf(ListItemsState())
        private set

    fun getItems(listId: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("lists")
            .document(listId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    state.value = state.value.copy(
                        error = error.message
                    )
                    return@addSnapshotListener
                }

                val items = snapshot?.get("items") as? List<Map<String, Any>> ?: emptyList()
                val itemList = items.mapNotNull { item ->
                    val name = item["name"] as? String
                    val quantity = (item["quantity"] as? Number)?.toDouble()
                    if (name != null && quantity != null) {
                        Items(
                            docId = null,
                            name = name,
                            qtd = quantity,
                            checked = false // Assuming default unchecked state
                        )
                    } else {
                        null
                    }
                }
                state.value = state.value.copy(
                    items = itemList
                )
            }
    }

    fun toggleItemChecked(listId: String, item: Items){
        val db = Firebase.firestore

        item.checked = !item.checked

        db.collection("lists")
            .document(listId)
            .collection("items")
            .document(item.docId!!)
            .set(item)

    }
}