package com.example.petshopper.features.bottomnavigation.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.petshopper.features.bottomnavigation.home.presentation.state.HomeAction
import com.example.petshopper.features.bottomnavigation.home.presentation.viewmodel.HomeViewModel
import com.example.petshopper.common.composables.CategoryTabs
import com.example.petshopper.common.composables.EmptyStateMessage
import com.example.petshopper.common.composables.PetGrid
import com.example.petshopper.common.composables.SearchBarComponent
import com.example.petshopper.core.util.constants.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    vm: HomeViewModel
) {

    val state by vm.state.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    val listState = rememberLazyGridState()
    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItems = listState.layoutInfo.totalItemsCount
            val lastIdx = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            lastIdx >= (totalItems - 2) && !state.pageEndReached && !state.isPaginating
        }
    }

    val selectedCategoryName = remember(state.selectedCategoryId, state.categories) {
        state.categories.find { it.id == state.selectedCategoryId }?.type ?: "Pets"
    }

    val dynamicPlaceholder = remember(selectedCategoryName) {
        Constants.getSearchPlaceholder(selectedCategoryName)
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            vm.onAction(HomeAction.LoadMorePets)
        }
    }

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { vm.onAction(HomeAction.Refresh) },
        state = pullToRefreshState,
        modifier = modifier.fillMaxSize().background(Color(0xFFFBFBFB)) // Page Background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            CategoryTabs(
                categories = state.categories,
                selectedId = state.selectedCategoryId,
                onSelect = { id -> vm.onAction(HomeAction.SelectCategory(id)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchBarComponent(
                query = state.searchQuery,
                onQueryChange = { vm.onAction(HomeAction.OnSearchQueryChange(it)) },
                modifier = Modifier.padding(horizontal = 16.dp),
                placeholder = dynamicPlaceholder
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isPetLoading && !state.isRefreshing) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if(state.pets.isEmpty()) {
                EmptyStateMessage()
            }
            else {
                PetGrid(
                    pets = state.pets,
                    onPetClick = { /* Navigate to Detail */ },
                    listState = listState,
                    isPaginating = state.isPaginating
                )
            }
        }
    }
}