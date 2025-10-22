package com.example.movieapplication.ui.view

import android.R.attr.description
import android.R.attr.rating
import android.net.Uri
import android.util.Config
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movieapplication.ui.model.DummyMovieData.movies
import com.example.movieapplication.ui.viewmodel.AddMovieViewModel

@Composable
fun AddMovieView(
    viewModel: AddMovieViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val movie by viewModel.movie.collectAsState()

    var showDialog by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        viewModel.onImageSelected(uri)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ){
        Text(
            text = "Add Movie",
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .clickable { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ){
            if (movie.posterPath.isNotBlank()) {
                Image(
                    painter = rememberAsyncImagePainter(movie.posterPath),
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            else{
                Text("Tap to choose poster", color = Color.DarkGray)
            }
        }

        OutlinedTextField(
            value = movie.title,
            onValueChange = viewModel::onTitleChange,
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = movie.description,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            maxLines = 10,
            singleLine = false
        )

        OutlinedTextField(
            value = movie.genre,
            onValueChange = viewModel::onGenreChange,
            label = { Text("Genre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = movie.director,
            onValueChange = viewModel::onDirectorChange,
            label = { Text("Director") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = if (movie.releaseYear == 0) "" else movie.releaseYear.toString(),
            onValueChange = viewModel::onReleaseYearChange,
            label = { Text("Release Year") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Text(text = "Rating", modifier = Modifier.padding(top = 8.dp))

        StarRatingBar(
            rating = movie.rating.toInt(),
            onRatingChanged = viewModel::onRatingChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }

    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false }, confirmButton = {
            TextButton(onClick = { showDialog = false }) {
                Text("Close")
            }
        }, title = { Text(movie.title.ifBlank { "Movie Detail" }) }, text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (movie.posterPath != null) {
                    Image(
                        painter = rememberAsyncImagePainter(movie.posterPath),
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Text("Description: ${movie.description}")
                Text("Genre: ${movie.genre}")
                Text("Director: ${movie.director}")
                Text("Release Year: ${movie.releaseYear}")
                Text("Rating: ${movie.rating}")
            }
        })
    }
}

@Composable
fun StarRatingBar(
    rating: Int, onRatingChanged: (Int) -> Unit, maxRating: Int = 5
) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Star $i",
                tint = Color(0xFFFFC107),
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun AddMoviePreview(){
    AddMovieView()
}
