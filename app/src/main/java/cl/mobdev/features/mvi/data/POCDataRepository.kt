package cl.mobdev.features.mvi.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class POCDataRepository {

    fun getListUser(): Flow<List<String>> = flow {
        val listUser = listOf<String>("Juan", "Carlos", "Nicolle", "María")
        delay(6000)
        //throw RuntimeException() //Descomentar para generar error y probar.
        emit(listUser)
    }
}
