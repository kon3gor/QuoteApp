package org.eshendo.quoteapp

interface RequestCallback {
    fun onResult(quote: Quote)
    fun onError(description: String)
}