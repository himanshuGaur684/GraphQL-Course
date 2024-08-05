package gaur.himanshu.graphql.domain.use_cases

import gaur.himanshu.graphql.domain.repository.CountryRepo
import gaur.himanshu.graphql.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import src.main.graphql.FetchDetailsQuery
import javax.inject.Inject

class FetchContinentsUseCase @Inject constructor(
    private val countryRepo: CountryRepo
) {

    operator fun invoke(code: String): Flow<NetworkResult<FetchDetailsQuery.Data>> =
        flow<NetworkResult<FetchDetailsQuery.Data>> {
            emit(NetworkResult.Loading())
            val response = countryRepo.getContinentDetails(code)
            if (response.isSuccess) {
                emit(NetworkResult.Success(data = response.getOrThrow()))
            } else {
                emit(NetworkResult.Error(message = response.exceptionOrNull()?.message))
            }
        }.catch {
            emit(NetworkResult.Error(it.message))
        }.flowOn(Dispatchers.IO)

}