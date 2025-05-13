package com.yeminnaing.randomquotegenerator.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yeminnaing.randomquotegenerator.R
import com.yeminnaing.randomquotegenerator.response.QuoteResponse

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeScreenVm = hiltViewModel()
    val state by viewModel.homeScreenStates.collectAsState()
    HomeScreenDesign(modifier = modifier, state, onNext = {
           viewModel.getQuote()
    })
}

@Composable
fun HomeScreenDesign(
    modifier: Modifier = Modifier,
    state: HomeScreenVm.HomeScreenStates,
    onNext: () -> Unit,
) {
val context = LocalContext.current
    when (state) {
        is HomeScreenVm.HomeScreenStates.Empty -> {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    "It's Empty",
                    fontSize = 20.sp,
                    modifier = modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
        is HomeScreenVm.HomeScreenStates.Error -> {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    "It's Error",
                    fontSize = 20.sp,
                    modifier = modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
        is HomeScreenVm.HomeScreenStates.Loading ->
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = modifier.align(
                        Alignment.Center
                    )
                )
            }
        is HomeScreenVm.HomeScreenStates.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = modifier
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 24.dp),
                    ) {
                        Text(text = state.data.quote, fontSize = 16.sp)
                        Box(modifier = modifier.fillMaxWidth().padding(top = 16.dp)) {
                            Text(
                                text = state.data.author,
                                fontSize = 14.sp,
                                modifier = modifier.align(Alignment.BottomEnd)
                            )
                        }


                    }


                }
                Box(modifier.fillMaxWidth()) {
                    Row(
                        modifier = modifier
                            .align(Alignment.BottomEnd)
                    ) {

                        IconButton(
                            onClick = {
                                onNext()
                            }, modifier = modifier
                                .padding(top = 24.dp)
                        ) {
                            Image(imageVector = Icons.Default.Refresh, contentDescription = "ShareQoutes")
                        }

                        IconButton(
                            onClick = {
                                val sendIntent= Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT,"\"${state.data.quote}\"\n- ${state.data.author}")
                                    type="type/plain"
                                }
                                val shareIntent= Intent.createChooser(sendIntent,"Share Quote")
                                context.startActivity(shareIntent)
                            }, modifier = modifier
                                .padding(top = 24.dp)
                        ) {
                            Image(imageVector = Icons.Default.Send, contentDescription = "ShareQoutes")
                        }
                    }
                }


            }
        }

    }


}


@Preview(showSystemUi = true)
@Composable
private fun HomeScreenDesignPrev() {
    HomeScreenDesign(state = HomeScreenVm.HomeScreenStates.Success(
        data = QuoteResponse(
            id = 30,
            quote = "Life is a gamble. You can get hurt, but people die in plane crashes, lose their arms and legs in car accidents; people die every day. Same with fighters: some die, some get hurt, some go on. You just don't let yourself believe it will happen to you.",
            author = "Muhammad Ali"
        )
    ), onNext = {

    })
}

