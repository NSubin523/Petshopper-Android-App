package com.example.petshopper.features.bottomnavigation.home.domain.usecase

import com.example.petshopper.core.di.IoDispatcher
import com.example.petshopper.core.di.NetworkHelper
import com.example.petshopper.core.presentation.Resource
import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.bottomnavigation.home.data.repository.InventoryRepository
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
        emit(Resource.Loading())

        val isFirstPage = page == Constants.DEFAULT_PAGE_START
        var localPets: List<Pet> = emptyList()

        if(isFirstPage){
            localPets = inventoryRepository.getInventoryFromLocal(categoryId = categoryId)
            if(localPets.isNotEmpty()){
                emit(Resource.Success(localPets))
            }
        }

        if(!networkHelper.isNetworkAvailable()){
            if(isFirstPage){
                emitPetsOrError(localPets, "No Internet connection and no local data")
            } else {
                emit(Resource.Error("No Internet Connection.."))
            }
        }

        try {
            val remotePets = inventoryRepository.getInventoryByCategoryFromNetwork(
                categoryId = categoryId,
                page = page
            )

            inventoryRepository.saveInventoryToLocal(
                categoryId = categoryId,
                pets = remotePets
            )
            emit(Resource.Success(remotePets))

        } catch (ex: Exception){
            if(isFirstPage){
                emitPetsOrError(localPets, ex.localizedMessage.toString())
            } else {
                emit(Resource.Error(ex.localizedMessage.toString()))
            }
        }
    }.flowOn(ioDispatcher)

    private suspend fun FlowCollector<Resource<List<Pet>>>.emitPetsOrError(pets: List<Pet>, errorMessage: String){
        if(pets.isNotEmpty()){
            emit(Resource.Error(errorMessage, pets))
        } else {
            emit(Resource.Error(errorMessage))
        }
    }
}