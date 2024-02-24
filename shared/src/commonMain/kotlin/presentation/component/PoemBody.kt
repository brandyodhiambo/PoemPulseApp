package presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun poemBody(paragraph: String) {
    Spacer(Modifier.height(8.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = paragraph,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        ),
    )
    Spacer(Modifier.height(8.dp))
}