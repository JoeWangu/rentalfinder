package com.saddict.rentalfinder.utils.utilscreens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

// ----------------- IMAGE SLIDER FROM YOUTUBE ------------------ //
@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselSlider(imageList: List<Int>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = imageList.size)
//    LaunchedEffect(Unit) {
//        while (true) {
//            delay(2000L)
//            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
//            pagerState.scrollToPage(nextPage)
//        }
//    }
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .wrapContentSize()
            ) { currentPage ->
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(10.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Image(
                        painter = painterResource(id = imageList[currentPage]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(100.dp)
                    )
                }
            }
            IconButton(
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < imageList.size) {
                        scope.launch {
                            pagerState.scrollToPage(nextPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    tint = Color.LightGray
                )
            }
            IconButton(
                onClick = {
                    val prevPage = pagerState.currentPage - 1
                    if (prevPage >= 0) {
                        scope.launch {
                            pagerState.scrollToPage(prevPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    tint = Color.LightGray
                )
            }
        }
        PageIndicator(
            pageCount = imageList.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
        )
    }
}

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(
                isSelected = it == currentPage
            )
        }
    }
}

@Composable
fun IndicatorDots(
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    ) {
    }
}

//fun LazyGridScope.headerOffSetCells(count: Int, content: @Composable LazyGridItemScope.() -> Unit){
//    item(span = { GridItemSpan(count) }, content = content)
//}

/*import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.utils.Constants
import com.saddict.rentalfinder.utils.carouselTransition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch*/

// ----------------- CURRENTLY WORKING SLIDER WITH AUTO SWITCH FROM GITHUB ----------------------//
/*@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MediaCarousel(
    list: List<Int>,
    totalItemsToShow: Int = 10,
    carouselLabel: String = "",
    autoScrollDuration: Long = Constants.CAROUSEL_AUTO_SCROLL_TIMER,
    onItemClicked: () -> Unit = {}
) {
    val pageCount = list.size
    val pagerState: PagerState = rememberPagerState(pageCount = { pageCount })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (isDragged.not()) {
        with(pagerState) {
            if (pageCount > 0) {
                var currentPageKey by remember { mutableIntStateOf(0) }
                LaunchedEffect(key1 = currentPageKey) {
                    launch {
                        delay(timeMillis = autoScrollDuration)
                        val nextPage = (currentPage + 1).mod(pageCount)
                        animateScrollToPage(
                            page = nextPage,
                            animationSpec = tween(
                                durationMillis = Constants.ANIM_TIME_LONG
                            )
                        )
                        currentPageKey = nextPage
                    }
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(
                    horizontal = dimensionResource(id = R.dimen.double_padding)
                ),
                pageSpacing = dimensionResource(id = R.dimen.normal_padding)
            ) { page: Int ->
                val item: Int = list[page]
                item.let {
                    Card(
                        onClick = { },
                        modifier = Modifier.carouselTransition(
                            page,
                            pagerState
                        )
                    ) {
                        CarouselBox(it)
                    }
                }
            }
        }

        if (carouselLabel.isNotBlank()) {
            Text(
                text = carouselLabel,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun CarouselBox(item: Int) {
    Box {
        Image(
            painter = painterResource(id = item),
            contentDescription = null
        )
    }
}*/

// -------------------- ORIGINAL SLIDER IMAGE HOLDER ------------------- //
/*//@Composable
//fun CarouselBox(item: HomeMediaUI) {
//    Box {
//        AsyncImage(
//            model = item.backdropPath.toFullImageUrl(),
//            contentDescription = null,
//            placeholder = painterResource(id = R.drawable.ic_load_placeholder),
//            error = painterResource(id = R.drawable.ic_load_error),
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier
//                .height(
//                    dimensionResource(id = R.dimen.home_grid_poster_height)
//                )
//                .fillMaxWidth()
//        )
//        val gradient = remember {
//            Brush.verticalGradient(
//                listOf(
//                    Color.Transparent,
//                    flix_color_translucent_black
//                )
//            )
//        }
//
//        Text(
//            text = item.name,
//            color = Color.White,
//            maxLines = 2,
//            overflow = TextOverflow.Ellipsis,
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter)
//                .background(gradient)
//                .padding(
//                    horizontal = dimensionResource(id = R.dimen.normal_padding),
//                    vertical = dimensionResource(id = R.dimen.small_padding)
//                )
//        )
//    }
//}*/

// ----------------------- FAILED EXPERIMENTS --------------------------------//
/*
//@Composable
//fun CarouselSlider(imageList: List<Int>, modifier: Modifier = Modifier) {
////    val pagerState = rememberPagerState(pageCount = imageList.size)
////    LaunchedEffect(Unit){
////        while (true){
////            delay(2000L)
////            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
////            pagerState.scrollToPage(nextPage)
////        }
////    }
////    Column(
////        modifier = Modifier,
////        horizontalAlignment = Alignment.CenterHorizontally
////    ) {
//        Box(modifier = Modifier){
//            HorizontalPager(
//                contentPadding = PaddingValues(horizontal = 32.dp),
//                pageSpacing = 16.dp
//            ) { currentPage ->
//                CarouselItem()
//
//            }
//        }
//    }
////}

//@Composable
//fun CarouselItem(){
//                Card(
//                    modifier = Modifier
//                        .wrapContentSize()
//                        .padding(start = 20.dp, end = 20.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//                ) {
//                    Image(
//                        painter = painterResource(id = imageList[currentPage]),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .height(200.dp)
//                    )
//                }
//}

// @OptIn(ExperimentalPagerApi::class)
//@Composable
//fun CarouselSlider(imageList: List<Int>, modifier: Modifier = Modifier) {
//    val pagerState = rememberPagerState(pageCount = imageList.size)
//    LaunchedEffect(Unit){
//        while (true){
//            delay(2000L)
//            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
//            pagerState.scrollToPage(nextPage)
//        }
//    }
//    Column(
//        modifier = Modifier,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Box(modifier = Modifier){
//            HorizontalPager(
//                state = pagerState,
//                modifier = Modifier
//                    .wrapContentSize()
//                    .padding(top = 16.dp, bottom = 8.dp)
//            ) { currentPage ->
//                Card(
//                    modifier = Modifier
//                        .wrapContentSize()
//                        .padding(start = 20.dp, end = 20.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//                ) {
//                    Image(
//                        painter = painterResource(id = imageList[currentPage]),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .height(200.dp)
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun CarouselSlider(imageList: List<Int>, modifier: Modifier = Modifier) {
//    var imageIndex by remember { mutableIntStateOf(0) }
//    val scrollState = rememberLazyListState()
////    val coroutineScope = rememberCoroutineScope()
//    LaunchedEffect(key1 = Unit, block = {
////        coroutineScope.launch {
//        while (true) {
//            delay(2000L)
//            if (imageIndex == imageList.size - 1) {
//                imageIndex = (0)
//            } else {
//                imageIndex++
//                scrollState.animateScrollToItem(imageIndex)
//            }
//        }
////        }
//    })
//    Column(
//        modifier = Modifier,
////        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Box(
//            modifier = modifier
//                .padding(dimensionResource(id = R.dimen.padding_medium))
//                .wrapContentSize(),
//        ) {
//            LazyRow(
//                modifier = Modifier,
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                state = scrollState
//            ) {
//                itemsIndexed(imageList) { index, item ->
//                    Card(
//                        modifier = Modifier.wrapContentSize(),
//                        elevation = CardDefaults.cardElevation(
//                            defaultElevation = 8.dp
//                        )
//                    ) {
//                        Image(
//                            painter = painterResource(id = item),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .height(150.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}*/
