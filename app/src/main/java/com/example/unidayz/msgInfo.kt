package com.example.unidayz
data class msgInfo(
    var uid : String? = "",
    var username : String? = "",
    var msg : String? = "",
    var uni : String? = "",
    var profileImageUrl: String? = "",
    var place : String? = ""

){
    constructor() : this("", "", "", "", "", "")
}
