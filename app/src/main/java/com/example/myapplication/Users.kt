package com.example.myapplication

data class Users(
    var name : String,
    var email : String,
    var uid : String
){
    constructor(): this ("", "", "")
}
