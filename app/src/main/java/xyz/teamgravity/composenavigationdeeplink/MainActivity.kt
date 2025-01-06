package xyz.teamgravity.composenavigationdeeplink

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import xyz.teamgravity.composenavigationdeeplink.ui.theme.ComposeNavigationDeepLinkTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        enableEdgeToEdge()
        setContent {
            ComposeNavigationDeepLinkTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    val controller = rememberNavController()

                    NavHost(
                        navController = controller,
                        startDestination = Route.Home,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable<Route.Home> {
                            HomeScreen()
                        }
                        composable<Route.DeepLink>(
                            deepLinks = listOf(
                                navDeepLink<Route.DeepLink>(
                                    basePath = "https://${DeepLinkConst.DOMAIN}"
                                ),
                                navDeepLink<Route.DeepLink>(
                                    basePath = "https://www.${DeepLinkConst.DOMAIN}"
                                )
                            )
                        ) { entry ->
                            DeepLinkScreen(
                                id = entry.toRoute<Route.DeepLink>().id
                            )
                        }
                    }
                }
            }
        }
    }
}