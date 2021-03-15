package dev.berggren

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
@ExperimentalComposeUiApi
fun FocusableBoxes() {
    val (tapOutside, box1, box2, box3, box4) = FocusRequester.createRefs()
    var isDpadNavigationHidden by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .focusOrder(tapOutside) {
                up = box1
                right = box1
                down = box1
                left = box1
            }
            .focusModifier()
            .pointerInput(Unit) { detectTapGestures { isDpadNavigationHidden = true } }
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.SpaceAround,

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            FocusableBox(
                modifier = Modifier
                    .focusOrder(box1) {
                        up = box3
                        right = box2
                        down = box3
                        left = box2
                    }
            ) {
                Text("Box 1")
            }
            FocusableBox(
                modifier = Modifier
                    .focusOrder(box2) {
                        up = box4
                        right = box1
                        down = box4
                        left = box1
                    }
            ) {
                Text("Box 2")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            FocusableBox(
                modifier = Modifier
                    .focusOrder(box3) {
                        up = box1
                        right = box4
                        down = box1
                        left = box4
                    }
            ) {
                Text("Box 3")
            }
            FocusableBox(
                modifier = Modifier
                    .focusOrder(box4) {
                        up = box2
                        right = box3
                        down = box2
                        left = box3
                    }
            ) {
                Text("Box 4")
            }
        }
        DisposableEffect(isDpadNavigationHidden) {
            if (isDpadNavigationHidden) {
                tapOutside.requestFocus()
                isDpadNavigationHidden = false
            }
            onDispose { }
        }
    }
}

@Composable
fun FocusableBox(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    var color by remember { mutableStateOf(Color.Transparent) }

    Box(
        modifier = modifier
            // Focus related
            .border(2.dp, color)
            .onFocusChanged { color = if (it.isFocused) Color.Red else Color.Transparent }
            .focusModifier()

            // Styling
            .background(color = Color.LightGray)
            .width(256.dp)
            .height(64.dp),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}