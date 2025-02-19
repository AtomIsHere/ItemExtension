package world.cepi.itemextension.item.traits.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import world.cepi.itemextension.item.Item
import world.cepi.itemextension.item.traits.ItemTrait
import world.cepi.itemextension.item.traits.list.stats.StatTrait

@Serializable
@SerialName("attack_speed")
class AttackSpeedTrait(
        /** The attack speed of the trait. */
        val attackSpeed: Double
) : ItemTrait() {

    override val loreIndex = 5f
    override val taskIndex = 1f

    override fun renderLore(item: Item): List<Component> {
        return arrayListOf(
            Component.text("${attackSpeed}s Attack Speed", NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false)
        ).also {
            if (item.softHasTrait<StatTrait>()) {
                it.add(Component.space())
            }
        }
    }

}