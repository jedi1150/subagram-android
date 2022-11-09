package com.jedi1150.subagram.ui.word.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jedi1150.subagram.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnagramHeader(
    size: Int,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Card(
            shape = shape,
            colors = colors,
        ) {
            Row(modifier = Modifier.padding(paddingValues),
                horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = stringResource(id = R.string.anagrams),
                )
                AnimatedContent(
                    targetState = size,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
                        } else {
                            slideInVertically { height -> -height } + fadeIn() with slideOutVertically { height -> height } + fadeOut()
                        }.using(SizeTransform(clip = false))
                    },
                ) { targetCount ->
                    Text(text = targetCount.toString())
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAnagramHeader() {
    AnagramHeader(size = 0)
}
