package com.example.petshopper.features.bottomnavigation.profile.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.petshopper.common.composables.InfoGroupCard
import com.example.petshopper.common.composables.ProfileInfoRow
import com.example.petshopper.common.composables.SharedTopBar
import com.example.petshopper.features.auth.presentation.viewmodel.AuthViewModel
import com.example.petshopper.features.bottomnavigation.profile.presentation.state.EditableFieldUserProfile

@Composable
fun AccountInformationScreen(
    authViewModel: AuthViewModel,
    onBackClick: () -> Unit,
    onFieldClick: (EditableFieldUserProfile) -> Unit
) {
    val state by authViewModel.state.collectAsState()
    val user = state.currentUser

    Scaffold(
        topBar = {
            SharedTopBar(
                title = "Account Information",
                onBackClick = onBackClick,
                action = null
            )
        },
        containerColor = Color(0xFFFBFBFB)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // ONE Clean Card for all editable info (Instagram Style)
            InfoGroupCard {
                ProfileInfoRow(
                    label = "First Name",
                    value = user?.firstName ?: "",
                    onClick = { onFieldClick(EditableFieldUserProfile.FirstName) }
                )
                ProfileInfoRow(
                    label = "Last Name",
                    value = user?.lastName ?: "",
                    onClick = { onFieldClick(EditableFieldUserProfile.LastName) }
                )
                ProfileInfoRow(
                    label = "Email",
                    value = user?.email ?: "",
                    onClick = { onFieldClick(EditableFieldUserProfile.Email) }
                )
                ProfileInfoRow(
                    label = "Phone",
                    value = user?.phoneNumber ?: "Not set",
                    onClick = { onFieldClick(EditableFieldUserProfile.PhoneNumber) },
                    showDivider = false
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            InfoGroupCard {
                ProfileInfoRow(
                    label = "Member Since",
                    value = user?.createdAt.toString(),
                    isEditable = false,
                    showDivider = false,
                    onClick = {}
                )
            }
        }
    }
}