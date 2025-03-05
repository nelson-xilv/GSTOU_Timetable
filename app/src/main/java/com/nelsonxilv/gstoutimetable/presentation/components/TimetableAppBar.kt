package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import com.nelsonxilv.gstoutimetable.R
import kotlinx.coroutines.delay

private const val SuccessFeedbackDuration = 1700L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    titleAppBar: String,
    isDataUpdating: Boolean,
    onUpdateButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var loadingState by remember { mutableStateOf(LoadingState.IDLE) }

    LaunchedEffect(isDataUpdating) {
        when {
            isDataUpdating -> loadingState = LoadingState.LOADING
            loadingState == LoadingState.LOADING -> {
                loadingState = LoadingState.SUCCESS
                delay(SuccessFeedbackDuration)
                loadingState = LoadingState.IDLE
            }
        }
    }


    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = titleAppBar,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        actions = {
            AnimatedIconButton(
                loadingState = loadingState,
                onUpdateButtonClick = {
                    if (loadingState == LoadingState.IDLE) {
                        onUpdateButtonClick()
                    }
                }
            )
        }
    )
}

@Composable
private fun AnimatedIconButton(
    loadingState: LoadingState,
    onUpdateButtonClick: () -> Unit,
) {
    IconButton(
        onClick = onUpdateButtonClick,
        enabled = loadingState != LoadingState.LOADING,
        colors = IconButtonDefaults.iconButtonColors(
            disabledContentColor = LocalContentColor.current
        )
    ) {
        AnimatedContent(
            targetState = loadingState,
            label = "Animated icon button",
            transitionSpec = {
                (fadeIn() + scaleIn()) togetherWith
                        (fadeOut() + scaleOut()) using SizeTransform()
            }
        ) { targetState ->
            when (targetState) {

                LoadingState.SUCCESS -> Icon(
                    painter = painterResource(id = R.drawable.ic_done_circle),
                    contentDescription = null
                )

                else -> RotatingIcon(
                    isLoading = targetState == LoadingState.LOADING
                )
            }
        }
    }
}

@Composable
private fun RotatingIcon(isLoading: Boolean) {

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        )
    )

    Icon(
        painter = painterResource(id = R.drawable.ic_update_data),
        contentDescription = null,
        modifier = if (isLoading) {
            Modifier.rotate(angle)
        } else {
            Modifier
        }
    )
}

private enum class LoadingState {
    IDLE,
    LOADING,
    SUCCESS
}