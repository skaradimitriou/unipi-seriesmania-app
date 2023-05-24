package com.stathis.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.TvSeriesMapper
import com.stathis.domain.model.ResultType
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.genres.Genre
import javax.inject.Inject

class TvSeriesPagingSource @Inject constructor(
    private val api: SeriesApi,
    private val genre: Genre? = null,
    private val type: ResultType
) : PagingSource<Int, UiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiModel> {
        val page = params.key ?: 1
        val response = type.getApiCallFromType(page)
        val orderListResp: List<UiModel>
        return if (response.isSuccessful) {
            val mappedData = TvSeriesMapper.toDomainModel(response.body())
            orderListResp = mappedData.results
            LoadResult.Page(
                data = orderListResp,
                prevKey = null,
                nextKey = if (orderListResp.isEmpty()) null else page.plus(1),
            )
        } else {
            LoadResult.Error(Exception())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UiModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private suspend fun ResultType.getApiCallFromType(page: Int) = when (this) {
        ResultType.TOP_RATED -> api.getTopRatedSeries(page)
        ResultType.TRENDING -> api.getTrendingSeries(page)
        ResultType.AIRING_TODAY -> api.getOnTheAirSeries(page)
        ResultType.SPECIFIC_GENRE -> api.getPagedResultsForThisGenre(genre?.id.toString(), page)
    }
}