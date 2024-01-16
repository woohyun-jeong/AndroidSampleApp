package com.example.sampleapp.feature.sample.nav

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class ScreenType(val icon: ImageVector, var route: String, val resourceId: String) {
    object Home : ScreenType(Icons.Filled.Home, "home", "Home")
    object Search : ScreenType(Icons.Filled.Search, "search", "Search")
    object Notifications : ScreenType(Icons.Filled.Notifications, "notifications", "Notifications")
    object Profile : ScreenType(Icons.Filled.Person, "profile", "Profile")
}

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = ScreenType.Home.resourceId,
                icon = ScreenType.Home.icon,
                route = ScreenType.Home.route
            ),
            BottomNavigationItem(
                label = ScreenType.Search.resourceId,
                icon = ScreenType.Search.icon,
                route = ScreenType.Search.route
            ),
            BottomNavigationItem(
                label = ScreenType.Profile.resourceId,
                icon = ScreenType.Profile.icon,
                route = ScreenType.Profile.route
            ),
        )
    }
}
@Composable
fun BottomNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ScreenType.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier) {
        composable(ScreenType.Home.route) {
            Log.d("BottomNavBar","builder Home route")
        }
        composable(ScreenType.Search.route) {
            Log.d("BottomNavBar","builder Search route")
        }
        composable(ScreenType.Profile.route) {
            Log.d("BottomNavBar","builder Profile route")
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun BottomNavBar() {
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                BottomNavigationItem().bottomNavigationItems().forEachIndexed {index,navigationItem ->
                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        BottomNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = ScreenType.Home.route
        )
    }
}
