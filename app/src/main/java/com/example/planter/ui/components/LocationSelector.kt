package com.example.planter.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.planter.R

@Composable
fun LocationSelector(
    selectedLocation: String,
    onLocationSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val locations = listOf(
        Location("home", R.string.home_location),
        Location("work", R.string.work_location),
        Location("summer_house", R.string.summer_house)
    )

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.select_location),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(locations) { location ->
                FilterChip(
                    selected = selectedLocation == location.id,
                    onClick = { onLocationSelected(location.id) },
                    label = { Text(stringResource(location.labelResId)) },
                    modifier = Modifier.height(32.dp)
                )
            }
        }
    }
}

private data class Location(
    val id: String,
    val labelResId: Int
) 