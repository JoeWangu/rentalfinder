package com.saddict.rentalfinder.rentals.ui.images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.model.remote.RentalImage
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.mapToRentalImage
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar

object RentalImageNavigationDestination : NavigationDestination {
    override val route: String = "rental image"
    override val titleRes: Int = R.string.rental_image
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalImageScreen(
    navigateUp: () -> Unit,
    onImageClick: (RentalImage) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.rental_image),
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) {
        RentalImageBody(
            modifier = modifier.padding(it),
            onImageClick = onImageClick,
        )
    }
}

@Composable
fun RentalImageBody(
    onImageClick: (RentalImage) -> Unit,
    modifier: Modifier = Modifier,
    imagesViewModel: RentalImageViewModel = hiltViewModel(),
) {
    val images = imagesViewModel.getAllImages.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(images.itemCount, key = images.itemKey { it.id }) { index ->
            val image = images[index]
            Box(
                modifier = Modifier
                    .clickable {
                        if (image != null) {
                            onImageClick(image.mapToRentalImage())
                        }
                    }
                    .height(300.dp)
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.small),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image?.imageUrl)
                        .crossfade(true)
                        .error(R.drawable.ic_broken_image)
                        .placeholder(R.drawable.loading_img)
                        .build(),
                    contentDescription = image?.imageName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (image != null) {
                    Text(
                        text = image.imageName,
                        fontSize = 20.sp,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}
