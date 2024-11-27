package com.example.shoppinglist.Lists.Item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.Items

@Composable
fun ItemRomView(
    modifier: Modifier = Modifier,
    item: Items,
    onCheckedChange: () -> Unit = {}
) {
    Row {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            text = item.name ?: ""
        )

        Text(
            modifier = Modifier
                .padding(16.dp),
            text = item.qtd.toString()
        )
        Checkbox(
            checked = item.checked,
            onCheckedChange = { onCheckedChange() }
        )
    }
}

