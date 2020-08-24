package de.exercicse.jrossbach.podcaster.feature.search.ui

import android.os.Parcel
import android.os.Parcelable

data class PodcastItemViewModel constructor(
    val id: String?,
    val title: String?,
    val url: String?,
    val imageUrl: String?,
    val category: String?,
    val publishingDate: String?,
    val length: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(imageUrl)
        parcel.writeString(category)
        parcel.writeString(publishingDate)
        parcel.writeInt(length)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PodcastItemViewModel> {
        override fun createFromParcel(parcel: Parcel): PodcastItemViewModel {
            return PodcastItemViewModel(parcel)
        }

        override fun newArray(size: Int): Array<PodcastItemViewModel?> {
            return arrayOfNulls(size)
        }
    }
}
