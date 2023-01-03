package cl.mobdev.features.mvi.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class POCDataRepository {
    val listShopping = mutableListOf("Tomates", "Arroz", "Pollo", "Arvejas")

    fun getListShopping(): Flow<List<String>> = flow {
        delay(3000)
        emit(listShopping)
    }
    fun addItemShopping(itemShopping: String): Flow<List<String>> = flow {
        listShopping.add(itemShopping)
        emit(listShopping)
    }
}
