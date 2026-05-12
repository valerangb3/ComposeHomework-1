package ru.otus.marketsample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import ru.otus.marketsample.databinding.ActivityMainBinding
import ru.otus.marketsample.di.DaggerAppComponent
import ru.otus.marketsample.presentation.Inject
import ru.otus.marketsample.presentation.daggerViewModel
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.ui.screens.ProductsScreen

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val appComponent = (this.applicationContext as MarketSampleApp).appComponent
        val vmFactory = DaggerProductListComponent.factory()
            .create(appComponent)
            .productListViewModelFactory()


        setContent {
            Inject(vmFactory) {
                val context = LocalContext.current
                val viewModel = daggerViewModel<ProductListViewModel>()
                viewModel.state
                ProductsScreen(
                    productListViewModel = viewModel,
                    onClick = { productId ->

                    },
                    onError = {
                        Toast.makeText(
                            context,
                            "Error wile loading data",
                            Toast.LENGTH_SHORT
                        ).show()

                        viewModel.errorHasShown()
                    }
                )
            }
        }
        /*binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.container) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
}