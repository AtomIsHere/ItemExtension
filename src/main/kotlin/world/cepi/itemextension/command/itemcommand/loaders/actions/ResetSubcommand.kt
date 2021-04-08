package world.cepi.itemextension.command.itemcommand.loaders.actions

import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import net.minestom.server.item.Material
import world.cepi.itemextension.item.Item
import world.cepi.itemextension.item.checkIsItem
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.addSyntax

object ResetSubcommand : Command("reset") {
    init {
        addSyntax { commandSender ->
            val player = commandSender as Player
            val itemStack = player.itemInMainHand

            if (itemStack.material == Material.AIR) {
                player.sendFormattedTranslatableMessage("mob", "main.required")
                return@addSyntax
            }

            val isCepiItem = checkIsItem(itemStack)

            if (isCepiItem) {
                val item = itemStack.data!!.get<Item>(Item.key)!!
                item.removeAllTraits()
                player.itemInMainHand = item.renderItem(itemStack.amount)
                player.sendFormattedTranslatableMessage("item", "reset")
            } else
                player.sendFormattedTranslatableMessage("mob", "formatted.required")
        }

    }
}