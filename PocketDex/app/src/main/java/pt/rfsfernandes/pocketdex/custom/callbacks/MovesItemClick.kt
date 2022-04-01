package pt.rfsfernandes.pocketdex.custom.callbacks

import pt.rfsfernandes.pocketdex.custom.Constants.MOVES_ITEM

/**
 * Interface MovesItemClick created at 1/19/21 23:02 for the project PoketDex
 * By: rodrigofernandes
 */
interface MovesItemClick {
    fun clickedMovesItem(title: String?, text: String?, moves_item: MOVES_ITEM?)
}