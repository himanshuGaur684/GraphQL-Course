package gaur.himanshu.graphql.domain.repository

import src.main.graphql.ContinentFetchingQuery
import src.main.graphql.FetchDetailsQuery

interface CountryRepo {

    suspend fun getContinents():Result<ContinentFetchingQuery.Data>


    suspend fun getContinentDetails(code:String) : Result<FetchDetailsQuery.Data>

}