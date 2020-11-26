package com.example.githubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitUsers(
    var name: String,
    var followers: String,
    var following: String,
    var photo: String,
    var nameuser: String?,
    var location: String?,
    var reposit: String?,
    var usaha: String?
) : Parcelable