package com.example.assignment

import android.content.Intent
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment.models.LoginRequestModel
import com.example.assignment.models.LoginResponseModel
import com.example.assignment.models.RegisterResponseModel
import com.example.assignment.models.RetrofitAPI
import com.example.assignment.models.User
import com.example.assignment.ui.theme.AssignmentTheme

class Dang_Nhap {

    @Composable
    fun Main_Dang_Ky(goTo:(String) -> Unit,
                     writeToShared:(User) -> Unit){
        val context = LocalContext.current
        var email by remember {
            mutableStateOf("kha123@gmail.com")
        }
        var password by remember {
            mutableStateOf("123")
        }
        fun loginCallback(response: LoginResponseModel?){
            if(response!=null){
                writeToShared(User(
                    id = response.user?._id,
                    email = response.user?.email,
                    name = response.user?.name
                ))
                Toast.makeText(context,"Đăng nhập thành công", Toast.LENGTH_LONG).show()
                Log.d(">>>","${response}")
                //goTo("home")
            }else{
                Toast.makeText(context,"Đăng nhập thất bại", Toast.LENGTH_LONG).show()
            }
        }
        fun LogIn(){
            try {
                val retrofitAPI = RetrofitAPI()
                var body = LoginRequestModel(
                    email = email,
                    password = password
                )
                retrofitAPI.login(body = body,::loginCallback)
            }catch (e:Exception){
                Log.d(">>>","Login faled")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Logo()
            WelcomeTXT()
            Spacer(modifier = Modifier.height(40.dp))
            Column (
                modifier = Modifier
                    .shadow(15.dp, RectangleShape, false, Color.Gray)
                    .width(420.dp)
                    .background(Color.White)
                    .height(500.dp)
            ){
                Input(
                    email = email, setEmail = {email= it},
                    password = password, setPassword = {password = it}
                )
                Spacer(modifier = Modifier.height(25.dp))
                LoginBTN(goTo = {goTo(it)},login = {LogIn()})
            }
        }
    }

    @Composable
    fun Input(email:String,setEmail:(String) ->Unit,
              password:String,setPassword:(String) ->Unit){
        var showPassword by remember {
            mutableStateOf(false)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column (
            modifier = Modifier
                .padding(start = 35.dp)
        ){
            Text(
                text = "Email",
                color = Color.Gray
            )
            TextField(
                value = email, onValueChange = setEmail,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),

                )
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = "Password",
                color = Color.Gray
            )
            TextField(
                value = password, onValueChange = setPassword,
                visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword },
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
    fun LoginBTN(goTo: (String) -> Unit, login:() -> Unit){
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Forgot Passord",
                    color = Color(0xFF303030),
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    Log.d("DN","LOG")
                          login()
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF242424)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(250.dp)
                    .height(50.dp)
            ) {
                Text(text = "LOG IN",
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            TextButton(onClick = {
                goTo("dangKy")
            }) {
                Text(text = "SIGN UP",
                    color = Color(0xFF303030),
                    fontSize = 18.sp
                )
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

    @Composable
    fun WelcomeTXT(){
        Column (
            modifier = Modifier
                .height(150.dp)
                .padding(start = 35.dp)
        ){
            Text(
                text = "Hello !",
                color = Color.Gray,
                fontWeight = FontWeight(400),
                style = TextStyle(
                    fontSize = 30.sp,

                    ),
                modifier = Modifier
                    .padding( top = 25.dp)
            )
            Text(
                text = "WELLCOME BACK",
                color = Color.Black,
                fontWeight = FontWeight(700),
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(top = 10.dp)
            )
        }
    }


}

@Preview(widthDp = 492, heightDp = 920)
@Composable
fun DNPre(){
    AssignmentTheme {
        Dang_Nhap().Main_Dang_Ky(goTo = {}) {

        }
    }
}