package com.example.feature.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.feature.screen.home.HomeScreen.HomeState

@Composable
fun HomeUi(
    state: HomeState,
    modifier: Modifier = Modifier,
) {
    Home(
        state = state,
        modifier = modifier,
    )
}
// class HomeUi
//    @Inject
//    constructor(
//        private val navigator: Navigator,
//    ) : Ui<HomeScreen.HomeState> {
//        @Composable
//        override fun Content(
//            state: HomeScreen.HomeState,
//            modifier: Modifier,
//        ) {
//            Home(
//                state = state,
//                modifier = modifier,
//            )
//        }
//    }
