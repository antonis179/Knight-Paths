package com.example.knight.ui.transformers

import com.example.knight.ui.delegateadapter.Data

interface ViewDataTransformer<M, D: Data> {
    fun transform(model: M): List<D>
}