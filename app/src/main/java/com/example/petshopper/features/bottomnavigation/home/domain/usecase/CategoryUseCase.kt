package com.example.petshopper.features.bottomnavigation.home.domain.usecase

import com.example.petshopper.core.di.IoDispatcher
import com.example.petshopper.core.di.NetworkHelper
import com.example.petshopper.core.presentation.Resource
import com.example.petshopper.features.bottomnavigation.home.data.repository.CategoryRepository
import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val networkHelper: NetworkHelper,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<Resource<List<Categories>>> = flow {
        emit(Resource.Loading())

        val localCategories = repository.getCategoriesFromLocal()

        if (!networkHelper.isNetworkAvailable()) {
            emitCategoriesOrError(localCategories, "No Internet connection and no local data to retrieve")
            return@flow
        }

        if (localCategories.isNotEmpty()) {
            emit(Resource.Success(localCategories))
        }

        try {
            val remoteCategories = repository.getCategoriesFromNetwork()
            repository.saveCategoriesToLocal(remoteCategories)
            emit(Resource.Success(remoteCategories))
        } catch (ex: Exception) {
            emitCategoriesOrError(localCategories, ex.message ?: "Unknown error occurred")
        }
    }.flowOn(ioDispatcher)

    private suspend fun FlowCollector<Resource<List<Categories>>>.emitCategoriesOrError(
        categories: List<Categories>,
        errorMessage: String
    ) {
        if (categories.isNotEmpty()) {
            emit(Resource.Error(errorMessage, categories))
        } else {
            emit(Resource.Error(errorMessage))
        }
    }
}