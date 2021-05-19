package co.daniel.moviegasm.base


import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*


abstract class BaseRecyclerAdapter<itemType, viewType : BaseViewHolder<itemType>> :
    RecyclerView.Adapter<viewType>() {


    protected var dataList: MutableList<itemType> = arrayListOf()


    override fun onBindViewHolder(holder: viewType, position: Int) {
        if (dataList.isNotEmpty()) {
            holder.setData(dataList[position])
        }
    }

    override fun getItemCount(): Int {

        return dataList.size
    }

    fun getItemAt(position: Int): itemType? {
        return if (position < dataList.size - 1) dataList[position] else null

    }

    fun addNewData(newItem: itemType, position: Int) {
        dataList.add(position, newItem)
        notifyDataSetChanged()
    }

    fun update(newDataList: List<itemType>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtils(dataList, newDataList))
        this.dataList.clear()
        this.dataList.addAll(newDataList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun appendNewData(newData: List<itemType>) {
        if (dataList.isEmpty()) {
            dataList.addAll(newData)
            notifyDataSetChanged()
        } else
            update(newData)
    }

    fun removeData(data: itemType) {
        dataList.remove(data)
        notifyDataSetChanged()
    }

    fun addNewData(data: itemType) {
        dataList.add(data)
        notifyDataSetChanged()
    }

    fun addNewDataList(dataList: List<itemType>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        dataList = ArrayList()
        notifyDataSetChanged()
    }

    fun updateItemAt(index: Int, item: itemType) {
        dataList[index] = item
        notifyItemChanged(index)
    }

}
