package presentation.component

import KottieAnimation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import animateKottieCompositionAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import rememberKottieComposition

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AnimationLoader(
    spec:String,
    modifier: Modifier = Modifier
) {
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(resource(spec))
    )

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        speed = 1f,
        iterations = 10
    )

    KottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = modifier,
    )
}