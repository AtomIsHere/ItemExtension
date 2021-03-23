package world.cepi.itemextension.command.itemcommand

import net.minestom.server.command.builder.Command
import world.cepi.itemextension.command.itemcommand.loaders.loaders

object ItemCommand : Command("item") {

    init {
        loaders.forEach { it.register(this) }
    }

}