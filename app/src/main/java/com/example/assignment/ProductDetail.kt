package com.example.assignment

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.example.assignment.models.ProductResponseModel
import com.example.assignment.models.ProductbyIdResponeseModel
import com.example.assignment.models.RetrofitAPI
import com.example.assignment.ui.theme.AssignmentTheme

class ProductDetail {

    @Composable
    fun DetailMain(value:String?,
                   updateCart:(Cart) ->Unit,
                   goTo:(String) ->Unit){
        var number by remember {
            mutableStateOf(1)
        }

        var product by remember {
            mutableStateOf(ProductModel(null,null,null,null,null,null,null,null))
        }
        fun getProductIdCallback(response: ProductbyIdResponeseModel?){
            product =ProductModel(
                id = response?.product?.id,
                name = response?.product?.name,
                price = response?.product?.price,
                description = response?.product?.description,
                image = response?.product?.image,
                rating = response?.product?.rating,
                voting = response?.product?.voting,
                category = response?.product?.category
            )
            Log.d(">>>getProdete",response?.status.toString())
        }
        fun getproductbyId(){
            try {
                Log.d(">>>getProdete","a")
                val retrofitAPI = RetrofitAPI()
                retrofitAPI.getProductbyId(value, callback = {getProductIdCallback(it)})

            }catch (e:Exception){
                Log.d("--->","getproduct thất bại : ${e.message}}")
            }
        }
        getproductbyId()
        Column (
            modifier = Modifier.fillMaxSize()
        ){
            ProductImage(product = product,goTo = {goTo(it)})
            Spacer(modifier = Modifier.height(20.dp))
            Detail(
                number = number,
                setNumber = {number = it},
                product = product,
                updateCart = {updateCart(it)},
                goTo = {goTo(it)}
            )
        }
    }

    @Composable
    fun ProductImage(product:ProductModel ,goTo: (String) -> Unit ){
        val painter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
            .data(product.image)
            .size(Size.ORIGINAL)
            .build()
        )
        Box (
            modifier = Modifier
                .height(480.dp)
                .fillMaxWidth()
                .background(Color.Transparent)
        ){

            Box (
                modifier = Modifier
                    .background(Color.Transparent, RoundedCornerShape(bottomStart = 50.dp))
                    .padding(start = 70.dp)
                    .fillMaxHeight()
                    .width(350.dp)
                    .align(Alignment.CenterEnd)
                ,
            ){
                Image(painter = painter, contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Transparent, RoundedCornerShape(bottomStart = 50.dp))
                    )
            }

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
                        )
                ) {
                    Icon(painter = painterResource(id = R.drawable.backicon),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()

                    )
                }
            }
        }
    }

    @Composable
    fun Detail(
        number:Int,
        setNumber:(Int) ->Unit,
        product: ProductModel,
        updateCart: (Cart) -> Unit,
        goTo: (String) -> Unit
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ){
            Text(text = "${product.name}",
                fontSize = 35.sp,
                fontFamily = gelasio,
                fontWeight = FontWeight(500),

            )
            //product price, number
            Price_Add_Subtract(
                number = number,
                setNumber = setNumber,
                product = product
            )
            //product detail
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(painter = painterResource(id = R.drawable.start_big),
                    contentDescription = null,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp))
                Spacer(modifier = Modifier.width(15.dp))
                Text(text = "${product.rating}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = "(${product.voting})" ,
                    fontSize = 17.sp,
                    color = Color(0xFF606060))
            }

            Text(text = "${product.description}",
                fontSize = 17.sp,
                color = Color(0xFF606060),
                maxLines = 5)
            Spacer(modifier = Modifier.height(25.dp))
            //Add to cart
            AddToCart(
                updateCart ={updateCart(it)},
                number = number,
                product = product,
                goTo = {goTo(it)}
            )
        }
    }

    @Composable
    private fun AddToCart(updateCart: (Cart) -> Unit,number: Int,product: ProductModel,goTo: (String) -> Unit) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(70.dp)
                    .width(70.dp)
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(10.dp)),) {
                Image(painter = painterResource(id = R.drawable.ssave),
                    contentDescription = null,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .background(Color.Transparent))

            }
            Spacer(modifier = Modifier.width(30.dp))
            Button(
                onClick = {
                          updateCart(Cart(
                              item = product,
                              quantity = number
                          ))
                    goTo("cart")
                },
                modifier = Modifier
                    .width(280.dp)
                    .height(70.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF303030)
                )
            ) {
                Text(text = "Add to cart",
                    fontSize = 25.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = gelasio
               )
            }
        }
    }

    @Composable
    private fun Price_Add_Subtract(
        number:Int,
        setNumber:(Int) ->Unit,
        product: ProductModel
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "${product.price}",
                fontSize = 30.sp,
                fontWeight = FontWeight(700),
                fontFamily = gelasio
                )
            Row (
                modifier = Modifier
                    .height(40.dp)
                    .align(alignment = Alignment.CenterVertically)
                    .width(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                IconButton(onClick = {
                                     setNumber(number+1)
                                     },
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.Transparent)
                        .width(40.dp)) {
                    Image(painter = painterResource(id = R.drawable.plus),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent),

                        )
                }
                Text(text = "${number}")
                IconButton(onClick = {
                                     if (number == 1){
                                         setNumber(1)
                                     }else{
                                         setNumber(number-1)
                                     }
                                     },
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.Transparent)
                        .width(40.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 492, heightDp = 920, showBackground = true)
@Composable
fun ProducDetePre(){
    AssignmentTheme {
        ProductDetail().DetailMain(value = "", updateCart = {}, goTo = {})
    }
    }
