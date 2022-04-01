package pt.rfsfernandes.pocketdex.model

import com.google.gson.annotations.SerializedName

/**
 * Class SimpleModelData created at 1/16/21 15:00 for the project PoketDex
 * By: rodrigofernandes
 */
open class SimpleModelData(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String
) {

    val urlId: String
        get() {
            val id = StringBuilder()
            var counter = 0
            var i = url.length - 1
            while (i < url.length) {
                if (url[i] == '/') {
                    counter++
                } else {
                    id.append(url[i])
                }
                if (counter == 2) {
                    break
                }
                i--
            }
            return StringBuilder(id.toString()).reverse().toString()
        }

    override fun toString(): String {
        return "SimpleModelData{" +
                "name='" + name + '\'' +
                '}'
    }
}