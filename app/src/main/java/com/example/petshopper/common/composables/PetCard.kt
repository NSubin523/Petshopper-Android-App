package com.example.petshopper.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet

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