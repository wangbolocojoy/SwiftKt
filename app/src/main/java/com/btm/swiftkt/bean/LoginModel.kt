package com.btm.swiftkt.bean


/**
 * @Auther: hero
 * @datetime: 2020/7/18 00:13
 * @desc:
 * @项目: SwiftKt
 */
data class LoginModel(
    val account: String?,
    val address: String?,
    val birthDay: String?,
    val city: String?,
    val constellation: String?,
    val creatTime: Any?,
    val easyInfo: String?,
    val fances: Int?,
    val follows: Int?,
    val icon: String?,
    val id: Int?,
    val likeStarts: Int?,
    val nickName: String?,
    val phone: String?,
    val postNum: Int?,
    val province: String?,
    val realName: String?,
    val token: String?,
    val userSex: Boolean?

) {
    override fun toString(): String {
        return "LoginModel(account=$account, address=$address, birthDay=$birthDay, city=$city, constellation=$constellation, creatTime=$creatTime, easyInfo=$easyInfo, fances=$fances, follows=$follows, icon=$icon, id=$id, likeStarts=$likeStarts, nickName=$nickName, phone=$phone, postNum=$postNum, province=$province, realName=$realName, token=$token, userSex=$userSex)"
    }
}

data class HomeModel(
    val `data`: List<Data>?,
    val msg: String?,
    val status: Int?
)

data class Data(
    val author: Author?,
    val creatTime: String?,
    val id: Int?,
    val isCollection: Boolean?,
    val isStart: Boolean?,
    val latitude: String?,
    val longitude: String?,
    val msgNum: Int?,
    val postAddress: String?,
    val postDetail: String?,
    val postImages: List<PostImage>?,
    val postMessageNum: Any?,
    val postPublic: Boolean?,
    val postReport: Any?,
    val postReports: Any?,
    val postStarts: Int?,
    val postState: Int?,
    val userId: Int?
)

data class Author(
    val address: String?,
    val icon: String?,
    val id: Int?,
    val nickName: String?
)

data class PostImage(
    val fileType: String?,
    val fileUrl: String?,
    val id: Int?,
    val userId: Any?
)