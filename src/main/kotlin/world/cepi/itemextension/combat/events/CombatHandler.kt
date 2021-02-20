package world.cepi.combat.events

import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor
import net.minestom.server.chat.ColoredText
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.LivingEntity
import net.minestom.server.entity.Player
import net.minestom.server.entity.damage.DamageType
import net.minestom.server.entity.hologram.Hologram
import net.minestom.server.event.entity.EntityAttackEvent
import net.minestom.server.item.Material
import net.minestom.server.utils.time.TimeUnit
import world.cepi.combat.util.applyKnockback
import java.text.NumberFormat


object CombatHandler : Handler {

    override fun register(playerInit: Player) {
        MinecraftServer.getGlobalEventHandler().addEventCallback(EntityAttackEvent::class.java) { entityAttackEvent ->
            with(entityAttackEvent) {

                if (target is Player && (target as Player).gameMode == GameMode.CREATIVE) {
                    return@addEventCallback
                }

                val livingEntitySource = entity as LivingEntity

                if (DeathHandler.isDead.contains(livingEntitySource) || DeathHandler.isDead.contains(target))
                    return@addEventCallback

                val item = livingEntitySource.itemInMainHand
                if (target is LivingEntity) {
                    val entity = target as LivingEntity
                    val damage: Double = if (item.material == Material.AIR)
                        1.0
                    else
                        item.data?.get<Double>("damage") ?: 1.0

                    entity.damage(DamageType.fromEntity(livingEntitySource), damage.toFloat())
                    applyKnockback(entity, livingEntitySource)

                    val format = NumberFormat.getInstance().format(-damage)

                    val hologram = Hologram(
                            target.instance,
                            target.position.clone().add(0.0, 1.0, 0.0),
                            ColoredText.of("${ChatColor.RED}$format"),
                            true
                    )

                    MinecraftServer.getSchedulerManager().buildTask {
                        hologram.remove()
                    }.delay(1, TimeUnit.SECOND).schedule()

                }
            }
        }

    }

}