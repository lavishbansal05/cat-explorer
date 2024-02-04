package com.assignment.catexplorer.domain.usecase

import com.assignment.catexplorer.domain.CatsRepository
import com.assignment.catexplorer.domain.base.BaseUseCase
import com.assignment.catexplorer.domain.model.CatBreedEntity
import com.assignment.catexplorer.domain.model.GenericError
import com.assignment.catexplorer.domain.model.Response
import java.lang.Exception
import javax.inject.Inject

class GetCatBreedDetailUseCase @Inject constructor(
    private val catsRepository: CatsRepository,
) : BaseUseCase<GetCatBreedDetailUseCase.Params, CatBreedEntity, GenericError>() {

    override suspend fun execute(parameters: GetCatBreedDetailUseCase.Params): Response<CatBreedEntity, GenericError> {
        return try {
            Response.Success(catsRepository.getCatBreedDetail(id = parameters.catId))
        } catch (exception: Exception) {
            Response.Error(error = GenericError(errorMessage = exception.message.toString()))
        }
    }

    data class Params(val catId: String)
}