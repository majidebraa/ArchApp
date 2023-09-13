package com.majidebrahimi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import java.util.concurrent.TimeUnit

@Entity
data class User (
    @PrimaryKey @SerializedName("id") var id: Int,
    @SerializedName("login") var login: String?,
    @SerializedName("avatar_url") var avatarUrl: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("company") var company: String?,
    @SerializedName("blog") var blog: String?,
    var lastRefreshed: Date?
): Serializable {
    /**
     * We consider that an [User] is outdated when the last time
     * we fetched it was more than 10 minutes
     */
    constructor(): this(
        0,"","","","","",Date()
    )
    fun haveToRefreshFromNetwork() : Boolean
            = TimeUnit.MILLISECONDS.toMinutes(Date().time - (lastRefreshed?.time ?: 0)) >= 10
}