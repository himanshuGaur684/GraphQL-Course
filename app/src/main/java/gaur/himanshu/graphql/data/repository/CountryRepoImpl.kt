package gaur.himanshu.graphql.data.repository

import com.apollographql.apollo.ApolloClient
import gaur.himanshu.graphql.domain.repository.CountryRepo
import src.main.graphql.ContinentFetchingQuery

class CountryRepoImpl(private val apolloClient: ApolloClient) : CountryRepo {
    override suspend fun getContinents(): Result<ContinentFetchingQuery.Data> {
        return try {
            val response = apolloClient.query(ContinentFetchingQuery()).execute()
            response.data?.let {
                Result.success(it)
            } ?: run { Result.failure(response.exception!!) }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}