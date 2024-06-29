package com.kamikazde328.flight.tech.ui.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamikazde328.flight.tech.MainViewModel
import com.kamikazde328.flight.tech.ui.compose.common.ListWrapper
import com.kamikazde328.flight.tech.ui.compose.common.MultiPreviews
import com.kamikazde328.flight.tech.ui.compose.common.PullToRefreshBox
import com.kamikazde328.flight.tech.ui.model.MainOneTimeEvent
import com.kamikazde328.flight.tech.ui.model.TripProductUiModel
import com.kamikazde328.flight.tech.ui.theme.FlightTechTestTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val oneTimeEvent by viewModel.oneTimeEvent.collectAsState()

    OneTimeEventsProcesses(ListWrapper(oneTimeEvent))

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        PullToRefreshTripsProducts(
            totalCost = uiState.totalCost,
            products = ListWrapper(uiState.trips),
            isRefreshing = uiState.isLoading,
            onRefresh = { viewModel.fetchData() },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun OneTimeEventsProcesses(oneTimeEvents: ListWrapper<MainOneTimeEvent>) {
    oneTimeEvents.value.forEach {
        when (it) {
            is MainOneTimeEvent.ShowError -> {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun PullToRefreshTripsProducts(
    totalCost: Double,
    products: ListWrapper<TripProductUiModel>,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        TripsProducts(
            totalCost = totalCost,
            products = products,
        )
    }
}

@Composable
fun TripsProducts(
    totalCost: Double,
    products: ListWrapper<TripProductUiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(10.dp)
    ) {
        ProductsHeader(
            productsCount = products.value.size,
            totalCost = totalCost,
        )
        ProductsList(
            products = products,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProductsList(products: ListWrapper<TripProductUiModel>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = products.value,
            key = { product -> product.id }
        ) { product ->
            Product(
                product = product,
            )
        }
    }
}


@Composable
fun Product(product: TripProductUiModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(top = 15.dp, end = 15.dp)
    ) {
        val color = if (product.isPhysical) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.error
        }
        val title = buildString {
            append(product.title)
            if (!product.isPhysical) {
                append(" (Physical)")
            }
        }
        Text(
            text = title,
            modifier = modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = color
        )
        val price = product.cost?.toString().orEmpty().ifBlank { "Unknown" }
        Text(
            text = "Quantity: ${product.quantity}; Price per item: $price",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ProductsHeader(productsCount: Int, totalCost: Double, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        val style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            style = style,
            text = "There is $productsCount products!",
        )
        Text(
            style = style,
            text = "Total cost is $totalCost...",
        )
    }
}

@MultiPreviews
@Composable
fun TripsProductsPreview() {
    FlightTechTestTheme {
        PullToRefreshTripsProducts(
            totalCost = 1.0,
            products = ListWrapper(
                listOf(
                    TripProductUiModel(
                        id = "1",
                        quantity = 1,
                        title = "title aw daw awd awd awd aw dwa d adw aw dwa aw dwad a",
                        cost = 1.0,
                        isPhysical = true
                    ),
                    TripProductUiModel(
                        id = "2",
                        quantity = 2,
                        title = "title2",
                        cost = 2.0,
                        isPhysical = false
                    ),
                    TripProductUiModel(
                        id = "3",
                        quantity = 3,
                        title = "title3",
                        cost = 3.0,
                        isPhysical = true
                    )
                )
            )
        )
    }
}