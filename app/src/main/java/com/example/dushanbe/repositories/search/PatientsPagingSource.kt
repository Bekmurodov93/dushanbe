package com.example.dushanbe.repositories.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.dushanbe.repositories.register.RegisterModel
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import retrofit2.HttpException

class PatientsPagingSource  constructor(
    private val searchApi: SearchApi,
    private val offset:Int,private val limit:Int,private val jsonString: String
) : PagingSource<Int, RegisterModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RegisterModel> {
        val position = params.key ?: 1
       val body = jsonString.toRequestBody()
        return try {
            val response = searchApi.getPatients(offset=offset,limit=limit,body)
            val repos = response.payload
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / 10)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, RegisterModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}