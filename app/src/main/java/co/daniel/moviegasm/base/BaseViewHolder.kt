package co.daniel.moviegasm.base


import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<W>(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

    override fun onClick(v: View) {}

    abstract fun setData(mData: W)
}
