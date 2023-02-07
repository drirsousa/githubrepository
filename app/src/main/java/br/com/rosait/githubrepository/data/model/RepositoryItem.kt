package br.com.rosait.githubrespositories.data.model

import android.os.Parcelable
import br.com.rosait.githubrepository.data.model.Owner
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RepositoryItem(
    @SerializedName("name") val name: String,
    @SerializedName("stargazers_count") val stargazers_count: Int,
    @SerializedName("forks_count") val forks_count: Int,
    @SerializedName("owner") val ownerLogin: Owner
) : Parcelable {
    constructor() : this("", 0, 0, Owner("", "",""))
}