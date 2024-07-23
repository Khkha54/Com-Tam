package com.example.assignment

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.util.TypedValueCompat
import androidx.core.view.ContentInfoCompat.Flags
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment.models.Cart
import com.example.assignment.models.User
import com.example.assignment.ui.theme.AssignmentTheme

fun Context.topx(dp:Int):Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentTheme {
                Body()
            }
        }
    }

    public fun Float.pxtoDp(contex: Context): Float =
        (this / (contex.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))

    @Composable
    fun Body() {
        val welcomeScreen = Welcome()
        val dnScreen = Dang_Nhap()
        val dkScreen = Dang_Ky()
        val homeScreen = Home()
        val detailScreen = ProductDetail()
        val cartCreen = CartScreen()
        val navController = rememberNavController()
        fun goTo(name: String) {
            navController.navigate(name)
        }


        // hàm đọc thông tin user từ shared preferences
        fun readFromShared(): User {
            val getPref = getPreferences(Context.MODE_PRIVATE)
            return User(
                id = getPref.getString("id", null),
                name = getPref.getString("name", null),
                email = getPref.getString("email", null)
            )
        }

        var userÌnfo by remember {
            mutableStateOf(readFromShared())
        }

        fun writeToShared(user: User) {
            val setPref = getPreferences(Context.MODE_PRIVATE)
            with(setPref.edit()) {
                putString("id", user.id)
                putString("name", user.name)
                putString("email", user.email)
                apply()
            }
            userÌnfo = user
        }

        //cart
        var cart by remember {
            mutableStateOf(listOf<Cart>())
        }

        fun updateCart(item: Cart) {
            val index = cart.indexOfFirst { it.item.id == item.item.id }

            if (index == -1) {
                cart = cart + item
            } else {
                if (item.quantity + cart[index].quantity == 0) {
                    cart = cart.filterIndexed { i, _ -> i != index }
                } else {
                    cart = cart.mapIndexed { i, cart ->
                        if (i == index) {
                            Cart(
                                item = cart.item,
                                quantity = cart.quantity + item.quantity
                            )
                        } else {
                            cart
                        }
                    }
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = if (userÌnfo.id.isNullOrEmpty()) "welcome" else "home"
        ) {
            composable("welcome") {
                welcomeScreen.Body(goTo = { goTo(it) })
            }
            composable("dangNhap") {
                dnScreen.Main_Dang_Ky(goTo = { goTo(it) }, writeToShared = { writeToShared(it) })
            }
            composable("dangKy") {
                dkScreen.MainDK(goTo = { goTo(it) })
            }
            composable("home") {
                homeScreen.MainHome(goTo = { goTo(it) }, writeToShared = { writeToShared(it) })
            }
            composable("detail/{value}") { backStackEntry ->
                detailScreen.DetailMain(
                    value = backStackEntry.arguments?.getString("value") ?: "",
                    updateCart = { updateCart(it) },
                    goTo = { goTo(it) }
                )
            }
            composable("cart") {
                cartCreen.CartMain(
                    updateCart = { updateCart(it) },
                    cart = cart,
                    goTo = { goTo(it) },
                    setcart = { cart = it }
                )
            }
        }

    }

    @Preview(showBackground = true, )
    @Composable
    fun GreetingPreview() {
        AssignmentTheme {
            Welcome().Body(goTo = { it })
        }
    }
}