package com.example.userprofile.userprofile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.userprofile.userprofile.model.MenuItem
import com.example.userprofile.userprofile.model.UserProfileResponse

@Composable
fun MenuScreen(viewModel: UserProfileScreenViewModel) {
    val context = LocalContext.current
    val userProfileState by viewModel.userProfileState.collectAsState()
    var userProfile by remember { mutableStateOf(UserProfileResponse()) }
    val groupedMenus by viewModel.menuGroups.collectAsState()
    when (userProfileState) {
        is UserProfileUiState.Success -> userProfile =
            (userProfileState as UserProfileUiState.Success).userProfile

        is UserProfileUiState.Error -> {}
        is UserProfileUiState.Loading -> {}
    }

    MenuScreenUI(userProfile, groupedMenus, onUserDataFetched = { })
}

@Preview
@Composable
fun MenuScreenPreview() {
    val userProfile = UserProfileResponse()
    MenuScreenUI(userProfile, emptyMap(), onUserDataFetched = {})
}

@Composable
fun MenuScreenUI(
    userProfile: UserProfileResponse,
    groupMenues: Map<String, List<MenuItem>>,
    onUserDataFetched: () -> Unit
) {
    var isAppsExpanded by remember { mutableStateOf(false) }


    // ✅ SafeArea + Scrollable
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(WindowInsets.safeContent.asPaddingValues()) // ✅ Safe area
    ) {
        // Scrollable content
        LazyColumn(
            // contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // 🔝 Header Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Menu",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .height(36.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .height(36.dp)
                                    .width(180.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.Gray),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(Icons.Outlined.Star, contentDescription = null)
                                Text("IND–INR–EN", modifier = Modifier.padding(start = 4.dp))
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                            }

                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(Icons.Default.Search, contentDescription = null)
                        }
                    }
                }
            }

            // 👤 Profile
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = userProfile.result.user_photo,
                        contentDescription = userProfile.result.user_photo,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop

                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(userProfile.result.title, style = MaterialTheme.typography.bodyLarge)
                    }
                    Text(
                        "Edit Profile",
                        color = Color(0xFF3F51B5),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // ⚙️ Settings Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MenuItemUI(
                        imageUrl = userProfile.result.menus[1].icon,
                        label = userProfile.result.menus[1].label,
                        color = Color(0xFF9E86FF),
                        modifier = Modifier.weight(1f)
                    )
                    MenuItemUI(
                        imageUrl = userProfile.result.menus[3].icon,
                        label = userProfile.result.menus[3].label,
                        color = Color(0xFFE0F7A2),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 📋 Menu Sections
            groupMenues.forEach { (section, menuItems) ->

                item {
                    Text(
                        text = section,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                item {
                    val itemsToRender =
                        if (section == "APPS" && !isAppsExpanded) menuItems.take(4) else menuItems
                    val columns = 2
                    val rows = (itemsToRender.size + columns - 1) / columns
                    val gridHeight = (rows * (64 + 12)).dp

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(gridHeight),
                        userScrollEnabled = false,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(itemsToRender.size) { index ->
                            val item = itemsToRender[index]
                            MenuItemUI(
                                imageUrl = item.icon,
                                label = item.label,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp)
                            )
                        }
                    }
                }

                // 🔘 See More
                if (section == "APPS") {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray)
                                .height(48.dp)
                                .clickable { isAppsExpanded = !isAppsExpanded },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "See More",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // ⭐ Rate Us
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .clickable { }
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Rate Us", style = MaterialTheme.typography.bodyLarge)
                }
            }

            // 🚪 Sign Out
            item {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Red
                    ),
                    border = BorderStroke(1.dp, Color.Red)
                ) {
                    Text("Sign Out")
                }
            }
        }
    }
}


@Composable
fun MenuItemUI(
    imageUrl: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            //.aspectRatio(1.8f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { }
            .padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            AsyncImage(
                model = imageUrl,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                label,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}