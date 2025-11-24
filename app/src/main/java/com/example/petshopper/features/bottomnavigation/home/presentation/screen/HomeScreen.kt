package com.example.petshopper.features.bottomnavigation.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories
import com.example.petshopper.features.bottomnavigation.home.presentation.state.HomeAction
import com.example.petshopper.features.bottomnavigation.home.presentation.viewmodel.HomeViewModel
import com.example.petshopper.common.colors.*
import com.example.petshopper.common.composables.SearchBarComponent
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = hiltViewModel()
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
                placeholder = "Golden Retriever, Siam..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isPetLoading && !state.isRefreshing) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
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

@Composable
fun PetGrid(
    pets: List<Pet>,
    onPetClick: (String) -> Unit,
    listState: LazyGridState,
    isPaginating: Boolean
) {
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(pets) { pet ->
            UberStylePetCard(pet, onPetClick)
        }

        if (isPaginating) {
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}

@Composable
fun UberStylePetCard(
    pet: Pet,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(pet.id) }
    ) {
        // 1. Image Container (The "Card")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp) // Fixed height for consistent grid
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {
            AsyncImage(
                model = pet.imageUrl,
                contentDescription = pet.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Optional: "Available" Badge overlay
            if (pet.isAvailable) {
                Surface(
                    color = Color.White.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = "25 miles away", // Or your location logic
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Title
        Text(
            text = pet.name,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // 3. Subtitle (Breed • Age)
        Text(
            text = "${pet.gender} • ${pet.age} yrs", // Using mapped data
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            maxLines = 1
        )

        // 4. Price (DoorDash style bold price)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$${pet.priceMin.toInt()} - $${pet.priceMax.toInt()}",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Black // Very bold
            ),
            color = Color.Black
        )
    }
}