package br.com.rosait.githubrespositories.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ResponseResult(
    @SerializedName("total_count") val total_count: Int,
    @SerializedName("incomplete_results") val incomplete_results: Boolean,
    @SerializedName("items") val items: List<RepositoryItem>,
    @SerializedName("message") val message: String
) : Parcelable