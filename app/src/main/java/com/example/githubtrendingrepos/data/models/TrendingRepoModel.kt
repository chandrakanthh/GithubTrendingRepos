package com.example.githubtrendingrepos.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingRepoModel(

    @SerialName("author") var author: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("avatar") var avatar: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("language") var language: String? = null,
    @SerialName("languageColor") var languageColor: String? = null,
    @SerialName("stars") var stars: Int? = null,
    @SerialName("forks") var forks: Int? = null,
    @SerialName("currentPeriodStars") var currentPeriodStars: Int? = null,
    @SerialName("builtBy") var builtBy: ArrayList<BuiltBy> = arrayListOf(),
    var selectedItemPos: Int? =-1

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("builtBy")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(name)
        parcel.writeString(avatar)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(language)
        parcel.writeString(languageColor)
        parcel.writeValue(stars)
        parcel.writeValue(forks)
        parcel.writeValue(currentPeriodStars)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrendingRepoModel> {
        override fun createFromParcel(parcel: Parcel): TrendingRepoModel {
            return TrendingRepoModel(parcel)
        }

        override fun newArray(size: Int): Array<TrendingRepoModel?> {
            return arrayOfNulls(size)
        }
    }

}