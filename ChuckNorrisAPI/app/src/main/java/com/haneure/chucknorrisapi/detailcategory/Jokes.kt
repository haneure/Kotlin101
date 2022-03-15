package com.haneure.chucknorrisapi.detailcategory

//{
//    categories: [ ],
//    created_at: "2020-01-05 13:42:29.296379",
//    icon_url: "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
//    id: "-lIRrTwZRY2dSpSnyobA2Q",
//    updated_at: "2020-01-05 13:42:29.296379",
//    url: "https://api.chucknorris.io/jokes/-lIRrTwZRY2dSpSnyobA2Q",
//    value: "A power drill just wouldn't be... unless it had a CHUCK Norris KEY."
//}

//{
//    total: 10,
//    result: [
//    {
//        categories: [ ],
//        created_at: "2020-01-05 13:42:20.262289",
//        icon_url: "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
//        id: "YGnwHk8VRnuCMUmnFQFptA",
//        updated_at: "2020-01-05 13:42:20.262289",
//        url: "https://api.chucknorris.io/jokes/YGnwHk8VRnuCMUmnFQFptA",
//        value: "Chuck Norris can make a banana split after he eats it."
//    },
//    {
//        categories: [ ],
//        created_at: "2020-01-05 13:42:23.880601",
//        icon_url: "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
//        id: "6-1r0ApPTqaw-IoBSWVzHA",
//        updated_at: "2020-01-05 13:42:23.880601",
//        url: "https://api.chucknorris.io/jokes/6-1r0ApPTqaw-IoBSWVzHA",
//        value: "Chuck Norris' idea of a lucious banana slpit consists of a 5 ft cedar log slpit in half lined with 3 five gallon buckets of pure crushed horseradish on top and covered with rusty thumbtacks, crude oil, stagnant swamp mud and topped off with chilled yak whipcream and red maraschino habanero peppers."
//    }
//}

data class Search( val total: Int, val result: ArrayList<Jokes>)

data class Jokes ( val categories: List<String>, val icon_url: String, val id: String, val url: String, val value: String )