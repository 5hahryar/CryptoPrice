package com.shahryar.cryptoprice

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shahryar.cryptoprice.data.repository.RepositoryImpl
import com.shahryar.cryptoprice.data.repository.Repository
import com.shahryar.cryptoprice.prices.PriceViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class PriceViewModelTest: KoinComponent {

    private val testModule = module {
        single<Repository> {
            RepositoryImpl(
                RemoteDataSourceTest(),
                LocalDataSourceTest()
            )
        }

        viewModel { (PriceViewModel(ApplicationProvider.getApplicationContext(), get())) }
    }

    private lateinit var mViewModel: PriceViewModel

    @Before
    fun setup() {
        mViewModel = PriceViewModel(ApplicationProvider.getApplicationContext(), FakeRepository())
    }

//    @get:Rule
//    val koinTestRule = KoinTestRule.create {
//        printLogger()
//        modules(testModule)
//    }

    @Test
    fun getCurrencies() {
        mViewModel.refreshData()
    }
}