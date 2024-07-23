package com.example.assignment

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.assignment.models.ProductModel
import com.example.assignment.models.ProductResponseModel
import com.example.assignment.models.RegisterRequestModel
import com.example.assignment.models.RegisterResponseModel
import com.example.assignment.models.RetrofitAPI
import com.example.assignment.ui.theme.AssignmentTheme

class HomeScreen {
    @Composable
    fun MainHome(goTo:(String) -> Unit ){
        val contex = LocalContext.current
        var items by remember {
            mutableStateOf(listOf<ProductModel>())
        }
        fun getProductCallback(response: ProductResponseModel?){
            val product = response?.data
            items = product ?: listOf()
            Log.d(">>>getPro",response?.status.toString())
        }
        fun getproducts(){
            try {
                val retrofitAPI = RetrofitAPI()
                retrofitAPI.getProducts("65b07ddcfc13ae4836b4cb08", callback = {getProductCallback(it)})

            }catch (e:Exception){
                Log.d("--->","getproduct thất bại : ${e.message}}")
            }
        }
        getproducts()
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            TopBar(goTo = {goTo(it)})
            Spacer(modifier = Modifier.height(20.dp))
            CategorryView()
            Spacer(modifier = Modifier.height(20.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(),
                modifier = Modifier.height(800.dp),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                items(items){ item ->
                    Item(item = item, goTo = {goTo(it)})
                }
            }
        Spacer(modifier = Modifier.height(100.dp))
        }
    }

    @Composable
    private fun Item(item:ProductModel,goTo: (String) -> Unit) {
        val painter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
            .data(item.image)
            .size(Size.ORIGINAL)
            .build()
             )
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(400.dp)
                .padding(horizontal = 10.dp)
                .clickable {
                           goTo("detail/${item.id}")
                },

            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent, RoundedCornerShape(15.dp))
            ) {
                //item Image
                Box(
                    modifier = Modifier
                        .background(Color.Transparent, RoundedCornerShape(15.dp))
                        .paint(
                            painter = painter,
                            contentScale = ContentScale.FillBounds
                        )
                        .fillMaxWidth()
                        .height(300.dp)
                        ,

                ) {
                    //item Shopping Button
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(70.dp)
                            .height(70.dp)
                            .align(alignment = Alignment.BottomEnd)
                            .padding(PaddingValues())
                            .padding(end = 10.dp)
                            .background(Color.Transparent)

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.itembag),
                            contentDescription = null,
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .background(Color.Transparent)
                        )
                    }
                }
                //item Info
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Text(
                        text = "${item.name}",
                        color = Color.Gray,
                        fontSize = 22.sp,
                        fontFamily = gelasio,
                        fontWeight = FontWeight(400)
                    )
                    Text(
                        text = "${item.price}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(300),
                        fontFamily = gelasio
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

    @Composable
    fun TopBar(goTo: (String) -> Unit ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = PaddingValues())
                .padding(horizontal = 20.dp)
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image (
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(PaddingValues())
                    .width(40.dp)
            )


            Column (
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Make home",
                    fontSize = 22.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight(400),
                    fontFamily = gelasio)
                Text(text = "BEAUTIFULL",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(700),
                    fontFamily = gelasio)
            }

            Image(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(PaddingValues())
                    .width(40.dp).clickable { goTo("cart") }
            )
        }
    }


    @Composable
    fun CategorryView(){
        data class cateItem(val name:String,val image:Int)
        val listCate = listOf<cateItem>(
            cateItem(
              name = "Favorite",
                image = R.drawable.star_white
            ),
            cateItem(
                name = "Table",
                image = R.drawable.table
            ),
            cateItem(
                name = "Chair",
                image = R.drawable.chair
            ),
            cateItem(
                name = "Armchair",
                image = R.drawable.sofa
            ),
            cateItem(
                name = "Bed",
                image = R.drawable.bed
            ),
            cateItem(
                name = "Lamp",
                image = R.drawable.lamp
            )
        )

        LazyRow (
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()

        ){

            items(listCate.size){ index ->
                Column (
                    modifier = Modifier.padding( start = if (listCate[index].name.equals("Favorite")) 20.dp else 30.dp ),
                    horizontalAlignment = Alignment.CenterHorizontally

                ){
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                            .background(
                                if (listCate[index].name.equals("Favorite")) Color(0xFF303030) else Color(
                                    0xFFF5F5F5
                                ),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(PaddingValues()),
                        ) {
                        Image(
                            painter = painterResource(id = listCate[index].image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(PaddingValues())
                                .height(25.dp)

                        )
                    }
                    Text(
                        text = listCate[index].name,
                        fontSize = 15.sp,
                        color = Color.Gray
                        )

                }
            }
        }
    }

}


@Preview(widthDp = 492, heightDp = 920)
@Composable
fun HomeScreenPre(){
    AssignmentTheme {
      HomeScreen().MainHome {

      }
    }
}