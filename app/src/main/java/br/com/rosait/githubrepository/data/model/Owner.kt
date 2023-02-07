package br.com.rosait.githubrepository.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: String,
    @SerializedName("avatar_url") val imageUrl: String
) : Parcelable