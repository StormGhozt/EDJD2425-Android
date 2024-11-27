package com.example.shoppinglist.Lists

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.ListItems
import com.example.shoppinglist.R
import com.example.shoppinglist.Screen
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ListListsView(modifier: Modifier = Modifier,
                  navController: NavController = rememberNavController()) {

    val viewModel: LLVModel = viewModel()
    val state = viewModel.state.value

    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd) {

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Shopping Lists",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart),
                    color = Color.White
                )
                IconButton(
                    onClick = {
                        // Handle logout action
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_exit_to_app_24),
                        contentDescription = "Logout",
                        tint = Color.White
                    )
                }
            }

            LazyColumn(modifier = modifier.fillMaxSize()) {
                itemsIndexed(
                    items = state.listItemsList
                ) { index, item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                navController.navigate(Screen.ListItem.route + "?listId=${item.docId}&listName=${item.name}")
                            }
                            .border(1.dp, Color.Gray, shape= RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = item.name ?: "")
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                viewModel.removeList(item)
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Remove List")
                            }

                        }
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp),
            onClick = {
                navController.navigate(Screen.AddList.route)
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
        viewModel.getLists()
    }
}
@Preview
@Composable
fun ViewListTypesPreview() {
    ShoppingListTheme {
        ListListsView(Modifier.fillMaxSize())
    }
}