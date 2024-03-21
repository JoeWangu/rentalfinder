package com.saddict.rentalfinder.rentals.ui.images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
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
    images: LazyPagingItems<ImageEntity> = imagesViewModel.images.collectAsLazyPagingItems()
) {
//    val coroutineScope = rememberCoroutineScope()
    LazyColumn(modifier = modifier) {
        items(images.itemCount, key = images.itemKey { it.id }) { index ->
            val image = images[index]
            Box(
                modifier = Modifier
//                            .fillMaxWidth()
                    .clickable {
                        if (image != null) {
                            onImageClick(image.mapToRentalImage())
                        }
                    }
            ) {
                AsyncImage(
                    model = image?.imageUrl,
                    contentDescription = image?.imageName,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    modifier = Modifier
                        .height(100.dp)
                        .padding(8.dp)
                )
            }
        }
    }
//    val uiState by imagesViewModel.uiState.collectAsState(Unit)
//    val ctx = LocalContext.current
//    LaunchedEffect(key1 = Unit) {
//        imagesViewModel.getImages()
//    }
//    LazyColumn(modifier = modifier) {
//        when (uiState) {
//            ImagesUiState.Loading -> {
//                ctx.toastUtil("loading data")
//                items(count = 10) { LoadingPlaceholderCardItem() }
//            }
//
//            ImagesUiState.Error -> {
//                ctx.toastUtil("unable to load data")
//            }
//
//            ImagesUiState.Empty -> {
//                ctx.toastUtil("data is empty")
//            }
//
//            is ImagesUiState.Success -> {
//                items(
//                    (uiState as ImagesUiState.Success).images
//                ) { imageData ->
//                    Box(
//                        modifier = Modifier
////                            .fillMaxWidth()
//                            .clickable {
//                                onImageClick(imageData)
//                            }
//                    ) {
//                        AsyncImage(
//                            model = imageData.imageUrl,
//                            contentDescription = imageData.imageName,
//                            contentScale = ContentScale.Crop,
//                            error = painterResource(id = R.drawable.ic_broken_image),
//                            placeholder = painterResource(id = R.drawable.loading_img),
//                            modifier = Modifier
//                                .height(100.dp)
//                                .padding(8.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
}

//@Composable
//fun RentalImageBody(
////    onImageSelected: (Uri) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var selectedImages by remember { mutableStateOf(listOf<Uri>()) }
//    val galleryLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
//            selectedImages = uriList
//        }
//    Column(
//        modifier = modifier
//    ) {
//        Button(
//            onClick = {
//                galleryLauncher.launch("image/*")
//            },
//            modifier = Modifier
//                .wrapContentSize()
//                .padding(10.dp)
//        ) {
//            Text(text = "Pick Image From Gallery")
//        }
//        Spacer(modifier = Modifier.padding(8.dp))
//        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
//            items(selectedImages) { uri ->
//                Image(
//                    painter = rememberAsyncImagePainter(uri),
//                    contentScale = ContentScale.FillWidth,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(16.dp, 8.dp)
//                        .size(100.dp)
//                        .clickable {}
//                )
//            }
//        }
//    }
//}

//@Composable
//fun ImagePickerScreen(onImageUpload: (Uri) -> Unit) {
//    val context = LocalContext.current
//    var imageUri: Any? by remember { mutableStateOf(R.drawable.ic_broken_image) }
//    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
//    val internalStoragePicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri ->
//        if (uri != null) {
//            Log.d("PhotoPicker", "Selected URI: $uri")
//            imageUri = uri
//            onImageUpload(uri)
//        }
//    }
//
//    Button(onClick = { internalStoragePicker.launch("image/*") }) {
//        Text(text = "Pick image")
//    }
//    LazyRow {
//        items(selectedImageUris.size) { uri ->
//            AsyncImage(
//                modifier = Modifier.size(250.dp),
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(uri)
//                    .crossfade(enable = true)
//                    .build(),
//                contentDescription = "Avatar Image",
//                contentScale = ContentScale.Crop,
//            )
//                Spacer(modifier = Modifier.height(24.dp))
//        }
//    }
//}
