package com.example.petshopper.features.bottomnavigation.home.domain.usecase

import com.example.petshopper.core.di.IoDispatcher
import com.example.petshopper.core.di.NetworkHelper
import com.example.petshopper.core.presentation.Resource
import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.bottomnavigation.home.data.repository.InventoryRepository
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class InventoryUseCase @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val networkHelper: NetworkHelper,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(
        categoryId: String,
        page: Int = Constants.DEFAULT_PAGE_START
    ): Flow<Resource<List<Pet>>> = flow {
        val dbSource = inventoryRepository.getInventoryFromLocal(categoryId)

        emitAll(dbSource.map { pets ->
            if(pets.isNotEmpty()){
                Resource.Success(pets)
            } else {
                Resource.Loading()
            }
        }.onStart {
            if(networkHelper.isNetworkAvailable()){
                try {
                    val remotePets = inventoryRepository.getInventoryByCategoryFromNetwork(categoryId, page)
                    inventoryRepository.saveInventoryToLocal(categoryId, remotePets)
                } catch (ex: Exception){
                    emit(Resource.Error("Failed to update inventory :  ${ex.localizedMessage}"))
                }
            } else {
                emit(Resource.Error("No Internet Connection"))
            }
        })
    }.flowOn(ioDispatcher)
}