package com.example.petshopper.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*


@Composable
fun InfoGroupCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            content()
        }
    }
}

// Reusable Wrapper that includes the Divider logic automatically
@Composable
fun ProfileInfoRow(
    label: String,
    value: String,
    isEditable: Boolean = true,
    showDivider: Boolean = true,
    onClick: () -> Unit
) {
    InfoRow(
        label = label,
        value = value,
        isEditable = isEditable,
        onClick = onClick
    )
    if (showDivider) {
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    isEditable: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isEditable) { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    color = if (value.isEmpty() || value == "Not set") Color.Gray else Color.Black
                ),
                maxLines = 1
            )
        }

        if (isEditable) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                contentDescription = "Edit",
                tint = Color.LightGray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}