package com.example.chatgut.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String, val profileImageURL: String): Parcelable {
    constructor() : this("", "", "")
}