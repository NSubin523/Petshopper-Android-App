package com.example.petshopper.features.bottomnavigation.profile.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.petshopper.core.util.constants.Constants

data class ProfileOption(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

object ProfileOptionsFactory {
    fun getOptions(
        onClickActionAccountInfo: () -> Unit,
        onClickActionSaved: () -> Unit,
        onClickActionNotifications: () -> Unit,
        onClickActionAbout: () -> Unit,
        onClickActionLogout: () -> Unit,
    ): List<ProfileOption> {
        return listOf(
            ProfileOption(Constants.ProfileOptionTitles.accountInfo, Icons.Outlined.Person, onClickActionAccountInfo),
            ProfileOption(Constants.ProfileOptionTitles.saved, Icons.Outlined.BookmarkBorder, onClickActionSaved),
            ProfileOption(Constants.ProfileOptionTitles.notifications, Icons.Outlined.NotificationAdd, onClickActionNotifications),
            ProfileOption(Constants.ProfileOptionTitles.aboutApp, Icons.Outlined.Info, onClickActionAbout),
            ProfileOption(Constants.ProfileOptionTitles.logout, Icons.Outlined.Logout, onClickActionLogout),
        )
    }
}