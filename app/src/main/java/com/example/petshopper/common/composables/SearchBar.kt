package com.example.petshopper.common.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarComponent(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search....",
    leadingIcon: ImageVector = Icons.Default.Search,
    onSearchClicked: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp), // Uber bars are usually slightly chunky
        shape = RoundedCornerShape(50), // 50 makes it a perfect "Pill" shape

        // Visuals
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "Search Icon",
                tint = Color.Black
            )
        },

        // Keyboard Handling
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked()
                focusManager.clearFocus() // Hides keyboard after search
            }
        ),

        // Custom Colors to remove the underline and make it look like a "Box"
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFEEEEEE), // Light Gray
            unfocusedContainerColor = Color(0xFFEEEEEE),
            disabledContainerColor = Color(0xFFEEEEEE),
            focusedIndicatorColor = Color.Transparent, // Hides the underline
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        )
    )
}