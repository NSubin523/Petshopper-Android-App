package com.example.petshopper.features.bottomnavigation.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories
import com.example.petshopper.features.bottomnavigation.home.presentation.state.HomeAction
import com.example.petshopper.features.bottomnavigation.home.presentation.viewmodel.HomeViewModel
import com.example.petshopper.common.colors.ChipSelectedColor
import com.example.petshopper.common.colors.ChipUnselectedColor
import com.example.petshopper.common.colors.PageBackgroundColor
import com.example.petshopper.common.composables.SearchBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel()
) {

    val state by vm.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PageBackgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(top = 16.dp)
        ){
            CategoryTabs(
                categories = state.categories,
                selectedId = state.selectedCategoryId,
                onSelect = { id -> vm.onAction(HomeAction.SelectCategory(id)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchBarComponent(
                query = searchQuery,
                onQueryChange = {searchQuery = it},
                modifier = Modifier.padding(horizontal = 16.dp),
                onSearchClicked = {

                },
                placeholder = "Search your favorite pet"
            )
        }
    }

}

@Composable
fun CategoryTabs(
    categories: List<Categories>,
    selectedId: String?,
    onSelect: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            val isSelected = category.id == selectedId

            CategoryTabItem(
                text = category.type,
                selected = isSelected,
                onClick = { onSelect(category.id) }
            )
        }
    }
}

@Composable
fun CategoryTabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected) ChipSelectedColor else ChipUnselectedColor
    val textColor = if (selected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor, style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.Medium
        ))
    }
}