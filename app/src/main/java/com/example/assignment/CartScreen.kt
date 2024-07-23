package com.example.assignment

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.assignment.models.Cart
import com.example.assignment.models.ProductModel
import com.example.assignment.ui.theme.AssignmentTheme

class CartScreen {

    @Composable
    fun CartMain(
        updateCart: (Cart) ->Unit,
        cart:List<Cart>,
        setcart:(List<Cart>)->Unit,
        goTo:(String)->Unit
    ){
        val context = LocalContext.current
        var sum =0
        fun sumitem(){
            cart.forEach { item->
                if(item.item.price!=null){
                    sum+=item.item.price.times(item.quantity)
                }
            }
        }
        sumitem()
        fun resetcart(){
            setcart(listOf<Cart>())
        }

        Column (
            modifier = Modifier.fillMaxSize()
        ){
            Row (
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(start = 40.dp, top = 40.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = {
                                     goTo("home")
                                     },
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .shadow(
                            elevation = 0.5.dp,
                            shape = RoundedCornerShape(10.dp),
                            clip = false,
                            ambientColor = Color.LightGray,
                            spotColor = Color.LightGray
                        )
                ) {
                    Icon(painter = painterResource(id = R.drawable.backicon),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()

                    )
                }
            }

            LazyColumn (
                modifier = Modifier.height(620.dp)
            ){
//                CartItem(item =Cart(
//                    item = ProductModel(null,"null","null",null,null,null,null,1),
//                    quantity = 1
//                ), updateCart = {updateCart(it)} )
             items(cart){item ->
                 CartItem(item = item, updateCart = {updateCart(it)})
             }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Total: ",
                    fontSize = 35.sp,
                    color = Color(0xFF808080))
                Text(text = "$ $sum",
                    fontSize = 35.sp,)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                                resetcart()
                                sumitem()
                                Toast.makeText(context,"Thanh toán thành công",Toast.LENGTH_LONG).show()
                             },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF242424),
                    contentColor = Color.White
                )
                    ) {
                Text(text = "Check out",
                    fontSize = 35.sp)
            }
        }
    }



    @Composable
    fun CartItem(item:Cart,
                     updateCart: (Cart) -> Unit ){
        val painter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
            .data(item.item.image)
            .size(Size.ORIGINAL)
            .build()
        )

        Box (
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .drawBehind {
                    val border = 2.dp.toPx()
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = border
                    )
                },
        ){
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp),
            ){
                Image(painter =painter ,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(140.dp)
                        .background(Color.LightGray, RoundedCornerShape(15.dp)))
                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(270.dp)
                        .padding(start = 20.dp)
                ){
                    Text(text = "${item.item.name}",
                        fontSize = 24.sp,
                        color = Color.LightGray,
                        fontWeight = FontWeight(600),
                        maxLines = 1
                    )
                    Text(text = "$${item.item.price?.times(item.quantity)}",
                        fontSize = 30.sp,
                        fontWeight = FontWeight(700),
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Row (
                        modifier = Modifier.height(50.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(onClick = {
                            updateCart(
                                Cart(
                                    item = item.item,
                                    quantity = 1
                                ))
                        },
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(PaddingValues())
                            ) {
                            Image(painter = painterResource(id = R.drawable.plus),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(40.dp))
                        }
                        Text(text = "${item.quantity}",
                            fontSize = 30.sp)
                        IconButton(onClick = {
                            updateCart(Cart(
                                item = item.item,
                                quantity = -1
                            ))
                        },
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(PaddingValues())
                        ) {
                            Image(painter = painterResource(id = R.drawable.minus),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(40.dp)
                                )
                        }
                    }
                }
                IconButton(onClick = {
                    updateCart(Cart(
                        item = item.item,
                        quantity = -item.quantity
                    ))
                }, modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
                    .align(alignment = Alignment.Top)) {
                    Image(painter = painterResource(id = R.drawable.delete),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                        )

                }
            }
        }
        }



}

@Preview(widthDp = 492, heightDp = 920, showBackground = true)
@Composable
fun CartPre(){
    AssignmentTheme {
        CartScreen().CartMain(updateCart = {}, cart = listOf(), goTo = {}, setcart = {})
    }
}