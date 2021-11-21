package com.example.knight.ui.delegateadapter

import androidx.recyclerview.widget.DiffUtil

class DiffItemCallBack<D : Data> : DiffUtil.ItemCallback<D>() {

    override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
        /**
         * Use isModified flag in order to force the diffAdapter to update the elements that
         * we want even if oldItem and newItem are the same.
         */
        val isModified = newItem.isModified
        return !isModified && oldItem == newItem
    }

    /**
     * When [areItemsTheSame] returns false, this method is called.
     * The return of this method is used as payload in onBindViewHolder.
     * We return only oldItem, since in onBindViewHolder we already have the new item and we can do
     * diffing or custom actions.
     * For more info check documentation on [DiffUtil.ItemCallback.getChangePayload].
     */
    override fun getChangePayload(oldItem: D, newItem: D): Any {
        return oldItem
    }

}