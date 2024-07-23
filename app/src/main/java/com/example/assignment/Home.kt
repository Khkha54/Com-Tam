package com.example.assignment

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment.models.User

data class TabBarItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
)

class Home {

    @Composable
    fun MainHome(goTo:(String) -> Unit,writeToShared:(User) ->Unit ){

        MainTabs(goTo,writeToShared = {writeToShared(it)})
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainTabs(goTo: (String) -> Unit,writeToShared:(User) ->Unit ) {
        // setting up the individual tabs
        val homeTab = TabBarItem(
            title = "Home",
            selectedIcon = R.drawable.shome,
            unselectedIcon = R.drawable.uhome
        )
        val alertsTab = TabBarItem(
            title = "Alerts",
            selectedIcon = R.drawable.sbell,
            unselectedIcon = R.drawable.ubell
        )
        val settingsTab = TabBarItem(
            title = "Settings",
            selectedIcon = R.drawable.ssave,
            unselectedIcon = R.drawable.usave
        )
        val moreTab = TabBarItem(
            title = "More",
            selectedIcon = R.drawable.sperson,
            unselectedIcon = R.drawable.uperson
        )
        // creating a list of all the tabs
        val tabBarItems = listOf(homeTab, alertsTab, settingsTab, moreTab)
        // creating our navController
        val navController = rememberNavController()
        Scaffold (bottomBar = {TabView(tabBarItems,navController)}){
            NavHost(navController = navController, startDestination = homeTab.title) {
                composable(homeTab.title){
                    HomeScreen().MainHome (goTo = {goTo(it)})
                }
                composable(alertsTab.title) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            writeToShared(User(id = null, name = null, email = null))
                        }) {
                            Text(text = "Log out")
                        }
                    }
                }
                composable(settingsTab.title) {
                }
                composable(moreTab.title) {
                }
            }
        }
    }

    @Composable
    fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
        var selectedTabIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        NavigationBar {
            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        navController.navigate(tabBarItem.title){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        TabBarIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unselectedIcon,
                            title = tabBarItem.title,
                        )
                    },
                    alwaysShowLabel = false,
                    label = { Text("") })
            }
        }
    }


    @Composable
    fun TabBarIconView(
        isSelected: Boolean,
        selectedIcon: Int,
        unselectedIcon: Int,
        title: String,
    ) {
        Image(
            painter = painterResource(id = if (isSelected) selectedIcon else unselectedIcon),
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            contentDescription = title
        )
    }


}