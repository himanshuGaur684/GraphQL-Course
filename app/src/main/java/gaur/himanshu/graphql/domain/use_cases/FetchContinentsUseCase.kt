package gaur.himanshu.graphql.domain.use_cases

import gaur.himanshu.graphql.domain.repository.CountryRepo
import gaur.himanshu.graphql.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import src.main.graphql.ContinentFetchingQuery
import javax.inject.Inject

class FetchContinentUseCase @Inject constructor(
    private val countryRepo: CountryRepo
) {

    operator fun invoke(): Flow<NetworkResult<ContinentFetchingQuery.Data>> = flow {
        emit(NetworkResult.Loading())
        val response = countryRepo.getContinents()
        if(response.isSuccess){
            emit(NetworkResult.Success(data = response.getOrThrow()))
        }else{
            emit(NetworkResult.Error(response.exceptionOrNull()?.message.toString()))
        }
    }.catch {
        emit(NetworkResult.Error(message = it.message.toString()))
    }.flowOn(Dispatchers.IO)

}