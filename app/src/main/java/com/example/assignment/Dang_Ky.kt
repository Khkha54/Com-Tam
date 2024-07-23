package com.example.assignment

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment.models.RegisterRequestModel
import com.example.assignment.models.RegisterResponseModel
import com.example.assignment.models.RetrofitAPI
import com.example.assignment.ui.theme.AssignmentTheme
import retrofit2.Response

class Dang_Ky {


    @Composable
    fun MainDK(goTo:(String) -> Unit ){
        var name by remember {
            mutableStateOf("kha12")
        }
        var email by remember {
            mutableStateOf("@gmail.com")
        }
        var password by remember {
            mutableStateOf("123")
        }
        var repassword by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        fun registerCallback(response: RegisterResponseModel?){
            Toast.makeText(context,"Đăng ký thành công",Toast.LENGTH_LONG).show()
            goTo("dangNhap")
        }
        fun register(){
            try {
                val retrofitAPI = RetrofitAPI()
                val body = RegisterRequestModel(
                    name = name,
                    email = email,
                    password = password
                )
                retrofitAPI.register(body = body, callback = {registerCallback(it)})
            }catch (e:Exception){
                Toast.makeText(context,"Đăng ký thất bại",Toast.LENGTH_LONG).show()
                Log.d("--->","ĐK thất bại : ${e.message}}")
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Logo()
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "WELCOME",
                color = Color.Black,
                fontWeight = FontWeight(700),
                fontSize = 28.sp,
                modifier = Modifier
                    .padding(top = 10.dp, start = 30.dp),
                fontFamily = gelasio
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column (
                modifier = Modifier
                    .shadow(10.dp, RectangleShape, false, Color.Gray)
                    .width(420.dp)
                    .background(Color.White)
                    .height(650.dp)
            ){
                InputDK(name = name, namec = {name =it},
                    email = email, emailc = {email=it},
                    password = password, passwordc = {password = it},
                    repassord = repassword, repassordc = {repassword = it})
                Spacer(modifier = Modifier.height(25.dp))
                RegisterBTN(name = name,
                    email = email,
                    password = password,
                    register = {register()})
            }
        }
    }

    @Composable
    fun InputDK(
        name:String,namec:(String) ->Unit,
        email:String,emailc:(String) ->Unit,
        password: String,passwordc:(String) ->Unit,
        repassord:String,repassordc: (String) ->Unit
    ){
        var show by remember {
            mutableStateOf(false)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Column (
            modifier = Modifier
                .padding(start = 35.dp)
        ){
            //Name
            Text(
                text = "Name",
                color = Color.Gray
            )
            TextField(
                value = name, onValueChange = namec,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),

                )
            Spacer(modifier = Modifier.height(35.dp))
            //Email
            Text(
                text = "Email",
                color = Color.Gray
            )
            TextField(
                value = email, onValueChange = emailc,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),

                )
            Spacer(modifier = Modifier.height(35.dp))

            //Password
            Text(
                text = "Password",
                color = Color.Gray
            )
            TextField(
                value = password, onValueChange = passwordc,
                keyboardOptions = if (show) KeyboardOptions(keyboardType = KeyboardType.Text) else KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { show=!show },
                        modifier = Modifier
                            .width(20.dp)
                            .padding(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.viewpass), contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(0.dp)

                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
            )
            Spacer(modifier = Modifier.height(35.dp))
            //Repeat password
            Text(
                text = "Repeat password",
                color = Color.Gray
            )
            TextField(
                value = repassord, onValueChange = repassordc,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(20.dp)
                            .padding(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.viewpass), contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(0.dp)

                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
            )
        }
    }


    @Composable
    fun RegisterBTN(name: String,email: String,password: String,register:() ->Unit){
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF242424)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(250.dp)
                    .height(50.dp)
            ) {
                Text(text = "SIGN UP",
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(text = "Already have account? ",
                    color = Color(0xFF303030),
                    fontWeight = FontWeight(500)
                )
                TextButton(onClick = {
                                     Log.d(">>>","${name}===${password}===${email}")
                                     },
                    contentPadding = PaddingValues()
                ) {
                    Text(text = "SIGN IN",
                        color = Color(0xFF303030),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }

    @Composable
    fun Logo(){
        Spacer(modifier = Modifier.height(50.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

            ){
            Image(
                painter = painterResource(id = R.drawable.line),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(150.dp)
                ,

                )
            Spacer(modifier = Modifier.width(20.dp))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription =null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight(),
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(id = R.drawable.line),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(150.dp),

                )
        }
    }

}

@Preview(widthDp = 420, heightDp = 920)
@Composable
fun DKPre(){
    AssignmentTheme {
        Dang_Ky().MainDK {

        }
    }
}