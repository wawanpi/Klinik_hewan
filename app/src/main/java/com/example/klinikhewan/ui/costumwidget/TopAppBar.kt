package com.example.pertemuan12.ui.costumwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostumeTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(

        title = {
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,

                color = Color.Black,
            )
        },
        actions = {
            IconButton(onClick = {
                onRefreshClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF4CAF50), // Green
                                    Color(0xFF2196F3)  // Blue
                                )
                            ),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(4.dp)
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Navigate Back",
                        tint = Color.White,
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFF44336), // Red
                                        Color(0xFFFFC107)  // Yellow
                                    )
                                ),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(4.dp)
                    )
                }
            }
        },
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3F51B5), // Deep Blue
                        Color(0xFF9C27B0)  // Purple
                    )
                )
            )
            .fillMaxWidth()
            .height(70.dp),
        scrollBehavior = scrollBehavior
    )
}
