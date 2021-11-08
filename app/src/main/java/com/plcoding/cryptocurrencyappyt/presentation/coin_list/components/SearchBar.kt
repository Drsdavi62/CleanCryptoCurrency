package com.plcoding.cryptocurrencyappyt.presentation.coin_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plcoding.cryptocurrencyappyt.presentation.coin_list.CoinListEvents

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchBar(
    isSearching: Boolean,
    searchQuery: String,
    onValueChange: (String) -> Unit,
    onStartSearch: () -> Unit,
    onEndSearch: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp)
            .height(60.dp),
        horizontalArrangement = if (isSearching) Arrangement.SpaceBetween else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            visible = isSearching,
            enter = slideInHorizontally(initialOffsetX = {
                it + it/2
            }),
            exit = slideOutHorizontally(targetOffsetX = {
                it * 2
            }),
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = onValueChange,
                placeholder =  {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.body1,
                        color = Color.LightGray
                    )
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.body1,
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
            )
        }

        IconButton(
            onClick = if (isSearching) onEndSearch else onStartSearch,
            modifier = if (isSearching) Modifier.fillMaxWidth() else Modifier
        ) {
            Icon(
                imageVector = if (isSearching) Icons.Default.Close else Icons.Default.Search,
                contentDescription = if (isSearching) "Close Search" else "Search"
            )
        }
    }
}