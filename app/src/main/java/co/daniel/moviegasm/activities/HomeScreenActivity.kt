package co.daniel.moviegasm.activities

import android.os.Bundle
import co.daniel.moviegasm.R
import co.daniel.moviegasm.viewmodel.HomeViewModel

class HomeScreenActivity : BaseActivity<HomeViewModel>() {


    override val viewModel: HomeViewModel by contractedViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun loadData() {

    }

    override fun initUI() {

    }
}