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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.R
import com.narely.feedbackjourney.R.string
import com.narely.feedbackjourney.commons.ui.EnterpriseLogo
import com.narely.feedbackjourney.features.managementuser.domain.model.HomeListItem
import com.narely.feedbackjourney.features.managementuser.ManagementUserActivity
import com.narely.feedbackjourney.ui.theme.Blue40
import com.narely.feedbackjourney.ui.theme.Blue80
import com.narely.feedbackjourney.ui.theme.Grey40
import com.narely.feedbackjourney.ui.theme.Magenta80
import com.narely.feedbackjourney.ui.theme.Typography

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val homeUiState = viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Grey40
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
            ) {
                EnterpriseLogo(modifier = Modifier.padding(16.dp))
            }
            ProfileComponent(
                userName = homeUiState.value.currentUser?.name ?: "",
                userType = homeUiState.value.currentUser?.type ?: ""
            )
            MyJourneyComponent()
            MyTeamComponent()
        }
    }
}

@Composable
private fun ProfileComponent(userName: String, userType: String) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 24.dp, end = 16.dp)
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InfoUser(userName = userName, userType = userType)
        StartFeedbackCollection(onClick = { })
    }
}

@Composable
private fun InfoUser(userName: String, userType: String) {
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
                text = userName,
                style = Typography.titleMedium,
                color = Blue80
            )
            Text(
                text = userType,
                style = Typography.displaySmall,
                color = Blue80
            )
        }
    }
}

@Composable
private fun StartFeedbackCollection(onClick: () -> Unit) {
    Button(
        onClick = onClick,
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
            text = stringResource(string.start_new_feedback),
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
        HomeListItem(
            onClick = {},
            painterId = R.drawable.face_skill,
            contentDescription = string.skills
        ),
        HomeListItem(
            onClick = {},
            painterId = R.drawable.clock_evaluation,
            contentDescription =string.evaluation_history
        ),
        HomeListItem(
            onClick = {},
            painterId = R.drawable.line_pdi,
            contentDescription =string.my_pdi
        ),
        HomeListItem(
            onClick = {},
            painterId = R.drawable.chart_dashboard,
            contentDescription =string.dashboard
        ),
    )

    Text(
        text = stringResource(string.my_journey),
        style = Typography.labelLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
    )

    Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        list.forEach { item ->
            ListItemComponent(item.onClick, item.painterId, item.contentDescription)
        }
    }
}

@Composable
private fun MyTeamComponent() {
    val context = LocalContext.current

    Text(
        text = stringResource(string.my_team),
        style = Typography.labelLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 16.dp)
    )

    ListItemComponent(
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
}