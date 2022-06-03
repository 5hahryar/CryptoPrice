package com.shahryar.shared.data.model

interface Mapper<I, O> {

    fun mapTo(input: I): O

    fun mapFrom(input: O): I
}