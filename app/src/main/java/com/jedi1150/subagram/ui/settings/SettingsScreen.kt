package com.jedi1150.subagram.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jedi1150.subagram.R
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(contentPadding: PaddingValues = PaddingValues()) {
    val layoutDirection = LocalLayoutDirection.current

    var showInfoDialog by remember { mutableStateOf(false) }

    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showInfoDialog = false
                }) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Help, contentDescription = null)
            },
            title = {
                Text(text = stringResource(id = R.string.about))
            },
            text = {
                Text(text = stringResource(id = R.string.about_description))
            },
        )
    }

    Surface {
        Column(
            modifier = Modifier.padding(PaddingValues(
                start = WindowInsets.navigationBars.asPaddingValues().calculateStartPadding(layoutDirection) + WindowInsets.displayCutout.asPaddingValues().calculateStartPadding(layoutDirection),
                top = contentPadding.calculateTopPadding(),
                end = WindowInsets.navigationBars.asPaddingValues().calculateEndPadding(layoutDirection) + WindowInsets.displayCutout.asPaddingValues().calculateEndPadding(layoutDirection),
                bottom = contentPadding.calculateBottomPadding(),
            )),
        ) {
            ListItem(
                headlineText = {
                    Text(text = stringResource(R.string.about))
                },
                modifier = Modifier.clickable { showInfoDialog = true },
                leadingContent = {
                    Icon(imageVector = Icons.Default.Help, contentDescription = null)
                },
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}
