package io.maqsad.androidcomponents.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import io.maqsad.androidcomponents.R
import io.maqsad.androidcomponents.extensions.advancedShadow
import io.maqsad.androidcomponents.extensions.customClickable
import io.maqsad.androidcomponents.ui.theme.GreenColor
import io.maqsad.androidcomponents.ui.theme.SecondaryColor


@Composable
fun Lifecycle.observeAsState(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}

@Composable
fun CustomToast(modifier: Modifier, toastState: CustomToastState) {
    Box(modifier = modifier.padding(horizontal = 15.dp)) {
        AnimatedVisibility(
            visible = toastState.state,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            Box(
                modifier = Modifier
//                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(color = SecondaryColor)
                    .customClickable(
                        onClick = { toastState.onClick?.invoke() },
                        bounded = true,
                        isActive = toastState.onClick != null
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(), verticalAlignment = Alignment.CenterVertically
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 22.dp)
                    ) {
                        val (titleRes, BuyNowRes) = createRefs()

                        PrimaryRegularText(
                            modifier = Modifier
                                .constrainAs(titleRes) {
                                    start.linkTo(parent.start)
                                    end.linkTo(BuyNowRes.start)
                                }
                                .padding(end = 8.dp),
                            text = toastState.text,
                            fontSize = 12.sp,
                            color = Color.White,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                        if (toastState.onClick != null) {
                            PrimaryRegularText(
                                modifier = Modifier
                                    .constrainAs(BuyNowRes) {
                                        start.linkTo(titleRes.end)
                                        end.linkTo(parent.end)
                                    },
                                textDecoration = TextDecoration.Underline,
                                text = "Buy now",
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }
                    }

                }
            }
        }
    }
}

data class CustomToastState(val text: String, var state: Boolean, val onClick: (() -> Unit)?) {
    constructor(state: Boolean) : this(text = "", state = state, onClick = {})
}

@Composable
fun AlertCard(modifier: Modifier = Modifier, text: String, isCompleted: Boolean = false) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .advancedShadow(
                alpha = 0.06f,
                offsetY = (1.dp),
                shadowBlurRadius = 10.dp,
                cornersRadius = 12.dp
            ),
        shape = RoundedCornerShape(7.dp),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(46.dp)
                    .fillMaxHeight()
                    .background(color = (if (isCompleted) GreenColor else SecondaryColor).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.padding(horizontal = 11.dp, vertical = 11.dp),
                    painter = painterResource(id = if (isCompleted) R.drawable.ic_tick_green else R.drawable.ic_ds_info),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = if (isCompleted) GreenColor else SecondaryColor)
                )
            }
            PrimaryRegularText(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                text = text,
                fontSize = 11.sp,
                color = SecondaryColor
            )
        }

    }
}

@Composable
fun AlertCard(modifier: Modifier = Modifier, text: AnnotatedString) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .advancedShadow(
                alpha = 0.06f,
                offsetY = (1.dp),
                shadowBlurRadius = 10.dp,
                cornersRadius = 12.dp
            ),
        shape = RoundedCornerShape(7.dp),
        elevation = 0.dp,
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(46.dp)
                    .background(color = SecondaryColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.padding(horizontal = 11.dp, vertical = 11.dp),
                    painter = painterResource(id = R.drawable.ic_ds_info),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = SecondaryColor)
                )
            }
            PrimaryRegularTextAnnotated(
                modifier = Modifier.padding(start = 12.dp),
                text = text,
                fontSize = 11.sp,
                color = SecondaryColor
            )
        }

    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun DataIncomingFlagCardPreview() {
//    MaqsadTheme {
//        AlertCard(text = "Hum apke liye mazeed tests bana rahay hain")
//    }
//}
