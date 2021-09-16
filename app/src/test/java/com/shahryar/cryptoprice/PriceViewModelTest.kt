package com.shahryar.cryptoprice

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shahryar.cryptoprice.repository.RepositoryImpl
import com.shahryar.cryptoprice.repository.base.Repository
import com.shahryar.cryptoprice.repository.local.LocalDataSourceImpl
import com.shahryar.cryptoprice.repository.local.getDatabase
import com.shahryar.cryptoprice.repository.preferences.UserPreferencesRepository
import com.shahryar.cryptoprice.repository.remote.ApiService
import com.shahryar.cryptoprice.repository.remote.RemoteDataSourceImpl
import com.shahryar.cryptoprice.viewModel.PriceViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import org.koin.test.KoinTestRule

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