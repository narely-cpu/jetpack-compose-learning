package com.narely.feedbackjourney.features.home.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.R
import com.narely.feedbackjourney.R.string
import com.narely.feedbackjourney.commons.ui.EnterpriseLogo
import com.narely.feedbackjourney.features.managementuser.domain.model.MyListItem
import com.narely.feedbackjourney.features.managementuser.ManagementUserActivity
import com.narely.feedbackjourney.ui.theme.Blue40
import com.narely.feedbackjourney.ui.theme.Blue80
import com.narely.feedbackjourney.ui.theme.Grey40
import com.narely.feedbackjourney.ui.theme.Magenta80
import com.narely.feedbackjourney.ui.theme.Typography

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    HomeScreenLayout()
}

@Composable
private fun HomeScreenLayout() {
    Scaffold(
        containerColor = Grey40
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 16.dp)
                .height(56.dp)
            ) {
                EnterpriseLogo(modifier = Modifier.padding(16.dp))
            }
            ProfileComponent()
            MyJourneyComponent()
            MyTeamComponent()
        }
    }
}

@Composable
private fun ProfileComponent() {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 24.dp, end = 16.dp)
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InfoUser()
        StartFeedbackCollection(onClick = { })
    }
}

@Composable
private fun InfoUser() {
    Row(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.PersonOutline,
            contentDescription = stringResource(id = string.image_profile),
            tint = Blue80,
            modifier = Modifier
                .size(48.dp)
                .background(color = Blue40, shape = CircleShape)
                .padding(12.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                "JOÃO CARLOS DA SILVA",
                style = Typography.titleMedium,
                color = Blue80
            )
            Text(
                "Admin",
                style = Typography.displaySmall,
                color = Blue80
            )
        }
    }
}

@Composable
private fun StartFeedbackCollection(onClick: () -> Unit) {
    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
            .height(32.dp),
        colors = ButtonColors(
            containerColor = Magenta80,
            contentColor = Color.White,
            disabledContainerColor = Magenta80,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            stringResource(string.start_new_feedback),
            style = Typography.titleSmall
        )
    }
}

@Composable
private fun ListItemComponent(onClick: () -> Unit, painterId: Int, contentDescription: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = painterId),
                contentDescription = stringResource(contentDescription),
                modifier = Modifier.size(24.dp)
            )
            Text(
                stringResource(contentDescription),
                style = Typography.displaySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        IconButton(onClick = onClick, modifier = Modifier.size(24.dp)) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(contentDescription),
                tint = Magenta80
            )
        }
    }
}

@Composable
private fun MyJourneyComponent() {
    val list = listOf(
        MyListItem(
            onClick = {},
            painterId = R.drawable.face_skill,
            contentDescription = string.skills
        ),
        MyListItem(
            onClick = {},
            painterId = R.drawable.clock_evaluation,
            contentDescription =string.evaluation_history
        ),
        MyListItem(
            onClick = {},
            painterId = R.drawable.line_pdi,
            contentDescription =string.my_pdi
        ),
        MyListItem(
            onClick = {},
            painterId = R.drawable.chart_dashboard,
            contentDescription =string.dashboard
        ),
    )

    Text(
        stringResource(string.my_journey),
        style = Typography.labelLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        items(list) { item ->
            ListItemComponent(item.onClick, item.painterId, item.contentDescription)
        }
    }
}

@Composable
private fun MyTeamComponent() {
    val context = LocalContext.current
    val list = listOf(
        MyListItem(
            onClick = {
                context.startActivity(
                    Intent(
                        context,
                        ManagementUserActivity::class.java
                    )
                )
            },
            painterId = R.drawable.members,
            contentDescription = string.manage_members
        )
    )

    Text(
        stringResource(string.my_team),
        style = Typography.labelLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 16.dp)
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        items(list) { item ->
            ListItemComponent(item.onClick, item.painterId, item.contentDescription)
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreenLayout()
}