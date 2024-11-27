package com.example.shoppinglist.Lists.Item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shoppinglist.R

@Composable
fun ListItemsView(
    modifier: Modifier = Modifier,
    listId: String,
    listName: String,
    navController: NavController
) {
    val viewModel: LIVModel = viewModel()
    val state = viewModel.state.value

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = listName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart),
                    color = Color.White
                )
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_keyboard_return_24),
                        contentDescription = "Go Back",
                        tint = Color.White
                    )
                }
            }

            LazyColumn(modifier = modifier.fillMaxSize()) {
                itemsIndexed(
                    items = state.items
                ) { index, item ->
                    ItemRomView(item = item) {
                        viewModel.toggleItemChecked(
                            listId = listId,
                            item = item
                        )
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp),
            onClick = {
                navController.navigate("addItem?listId=$listId")
            }) {
            Image(
                modifier = Modifier
                    .scale(2.0f)
                    .size(64.dp),
                colorFilter = ColorFilter.tint(Color.White),
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "add"
            )
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getItems(listId)
    }
}