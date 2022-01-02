package com.example.searchview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun HomeScreen() {

    val listViewModel = viewModel<ListViewModel>()
    var isDefaultAppBar by remember{ mutableStateOf(true)}

    BackHandler(!isDefaultAppBar) {
        if(!isDefaultAppBar)
            isDefaultAppBar = true
    }

    Scaffold(
        topBar = {
            if(isDefaultAppBar)
                DefaultAppBar("Home", onClick = {isDefaultAppBar = false})
            else
                isDefaultAppBar = SearchAppBar(listViewModel)
        }
    ){
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
        ){
            if (!isDefaultAppBar) {
                items(listViewModel.searchList) { item->
                    ListItem(
                        icon = {Icon(Icons.Default.List, null)},
                        text = {Text(item, fontSize = 18.sp, fontWeight = FontWeight.W600)}
                    )
                }
            } else {
                items(listViewModel.bookList) { item->
                    ListItem(
                        icon = {Icon(Icons.Default.List, null)},
                        text = {Text(item, fontSize = 18.sp, fontWeight = FontWeight.W600)}
                    )
                }
            }
        }
    }
}

@Composable
fun DefaultAppBar(
    title: String,
    onClick : ()-> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {Text(title)},
        modifier = modifier,
        contentColor = Color.White,
        elevation = AppBarDefaults.TopAppBarElevation,
        actions = {
            IconButton(onClick = onClick) {
                Icon(Icons.Default.Search, null)
            }
        }
    )
}

@SuppressLint("ComposableNaming")
@Composable
fun SearchAppBar(listViewModel: ListViewModel): Boolean{
    var searchItem by remember{ mutableStateOf("") }
    var visible by remember{ mutableStateOf(false) }

    Surface(
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
        TextField(
            value = searchItem,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.White
            ),
            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500),
            onValueChange = {
                searchItem = it
                searchBook(it, listViewModel)
            },
            placeholder = { Text("Search...", color = Color.DarkGray) },
            leadingIcon = {
                IconButton(
                    onClick = { visible = !visible }
                ) { Icon(Icons.Default.ArrowBack, null, tint = Color.Black) }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (searchItem == "")
                            visible = !visible
                        else
                            searchItem = ""
                    }
                ) { Icon(Icons.Default.Clear, null, tint = Color.Black) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        )
    }
    return visible
}


fun searchBook(query: String, listViewModel: ListViewModel) {
    listViewModel.searchList.clear()
    for(book in listViewModel.bookList){
        if(book.lowercase().contains(query.lowercase()))
            listViewModel.searchList.add(book)
    }
}
