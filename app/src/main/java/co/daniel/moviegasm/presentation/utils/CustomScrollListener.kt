package co.daniel.moviegasm.presentation.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomScrollListener(
    private val host: RecyclerView,
    private val thresholdItemCount: Int,
    private val layoutManager: LinearLayoutManager,
    private val action: () -> Unit
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        layoutManager.let {
            val lastVisible: Int = layoutManager.findLastVisibleItemPosition()
            if (lastVisible >= host.adapter!!.itemCount - thresholdItemCount) {
                action.invoke()
            }
        }
    }
}

fun RecyclerView.addCustomScrollListener(count: Int ,action: () -> Unit){
    this.addOnScrollListener(
        CustomScrollListener(
            this,
            count,
            this.layoutManager as LinearLayoutManager
        ) {
           action.invoke()
        })
}