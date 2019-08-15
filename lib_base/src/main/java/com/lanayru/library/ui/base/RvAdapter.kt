package com.lanayru.library.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.lanayru.view.IDateView

/**
 * RecyclerView adapter
 * @author seven
 * @since 2018/2/27
 * @version V1.0
 */
open abstract class RvAdapter<D> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: MutableList<D>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClick: ((v: View?, d: D?)-> Unit)? = null

    abstract fun onCreateView(parent: ViewGroup?, viewType: Int): View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = onCreateView(parent, viewType)

        onItemClick?.let {l->
            view.setOnClickListener {v->
                if (v is IDateView<*>) {
                    l.invoke(v, v.data as? D)
                }
            }
        }

        return RvHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val d = data?.get(position)
        if (holder?.itemView is IDateView<*>) {
            val view = holder.itemView as IDateView<D>
            view.data = d
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    fun remove(d: D) {
        if (itemCount <= 0) {
            return
        }

        var index = data!!.indexOf(d)
        if (index != -1) {
            data!!.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun addOrUpdate(d: D) {
        val start = itemCount

        if (null == data) {
            data = ArrayList<D>()

        }

        var index = data!!.indexOf(d)
        if (index != -1) {
            data!![index] = d
            notifyItemChanged(index)

        } else {
            data!!.add(d)
            notifyItemInserted(start)
        }

    }

}