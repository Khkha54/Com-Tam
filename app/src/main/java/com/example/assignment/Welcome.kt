package com.example.assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment.ui.theme.AssignmentTheme

public val gelasio = FontFamily(
    Font(R.font.gelasio_regular, FontWeight.Normal),
    Font(R.font.gelasio_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.gelasio_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.gelasio_medium, FontWeight.Medium)
)

class Welcome {

    @Composable
    fun Body( goTo:(String) -> Unit ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.bgn_boarding),
                    contentScale = ContentScale.FillBounds
                )

        ) {
            Spacer(modifier = Modifier.height(200.dp))
            Greeting()
            Spacer(modifier = Modifier.height(150.dp))
            TextButton(
                onClick = {
                    goTo("dangNhap")
                },
                modifier = Modifier
                    .background(Color(0xFF242424), RoundedCornerShape(6.dp))
                    .align(alignment = Alignment.CenterHorizontally)
                    .width(160.dp)
                    .height(60.dp),

                ) {
                Text(
                    text = "Get Start",
                    color = Color.White,
                    fontWeight = FontWeight(600),
                    fontSize = 20.sp,
                    fontFamily = gelasio
                )
            }

        }

    }

    @Composable
    fun Greeting() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                textAlign = TextAlign.Left,
                color = Color.Gray,
                fontSize = 30.sp,
                text = "MAKE YOUR",
                modifier = Modifier
                    .padding(start = 25.dp, top = 28.dp),
                fontFamily = gelasio
            )

            Text(
                text = "HOME BEAUTIFULL",
                textAlign = TextAlign.Left,
                color = Color.Black,
                fontSize = 33.sp,
                modifier = Modifier
                    .padding(start = 25.dp, top = 1.dp),
                fontFamily = gelasio
            )
            Text(
                text = "The best simple place where you discover most wonderful furnitures and make your home beautiful",
                textAlign = TextAlign.Left,
                color = Color.Gray,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 60.dp, top = 50.dp)
                    .width(280.dp),
                lineHeight = 35.sp,
                maxLines = 3,
                fontFamily = gelasio
            )
        }

    }


}

@Preview(widthDp = 400, heightDp = 800)
@Composable
fun WelPre(){
    AssignmentTheme {
        Welcome().Body {

        }
    }
}