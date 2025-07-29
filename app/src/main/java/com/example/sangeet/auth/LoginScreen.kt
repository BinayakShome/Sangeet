package com.example.sangeet.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sangeet.components.LoginButton
import com.example.sangeet.vm.LoginViewModel

@Composable
fun LoginScreen(
    state: SignInResult?,
    onSignInSuccess: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(context) {
        loginViewModel.initGoogleSignIn(context as Activity)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        loginViewModel.handleSignInResult(result.data)
    }

    LaunchedEffect(state?.success) {
        if (state?.success != null) {
            onSignInSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_animation))
//        val progress by animateLottieCompositionAsState(
//            composition = composition,
//            iterations = LottieConstants.IterateForever
//        )
//        LottieAnimation(
//            composition = composition,
//            progress = { progress },
//            modifier = Modifier.size(300.dp)
//        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome to Schedulo",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        LoginButton(
            iconOnly = false,
            onClick = {
                loginViewModel.startGoogleSignIn(context, launcher)
            }
        )
    }
}