package com.github.mlefeb01.spigotutils.customitem;

import com.github.mlefeb01.spigotutils.customitem.eventwrapper.*;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a custom item with unspecified functionality
 * @author Matt Lefebvre
 */
public abstract class AbstractCustomItem {
    public static final String CUSTOM_ITEM_NBT = "su-citem";
    @Getter
    private final String identifier;

    /**
     * Constructor
     * @param identifier identifier
     */
    public AbstractCustomItem(String identifier) {
        this.identifier = identifier;
    }

    // Cant override

    /**
     * Applies the identifier nbt tag to the item, should be called in the getItem() implementation
     * @param nbtItem nbtItem
     */
    protected final void applyIdentifierTag(NBTItem nbtItem) {
        nbtItem.setString(CUSTOM_ITEM_NBT, identifier);
    }

    // Must override

    /**
     * Creates an instance of this custom item. If you require more parameters for this method (e.g. - you need to add
     * additional tags to the item) you should have this method throw {@link UnsupportedOperationException} and create
     * your own method
     * @return item or UnsupportedOperationException
     */
    public abstract ItemStack getItem();

    // Can optionally override to give the item specific functionality

    /**
     * Setting to prevent this item from being enchanted via enchantment table
     * @return isEnchantable
     */
    public boolean isEnchantable() {
        return false;
    }

    /**
     * Setting to prevent this item from despawning via burn (e.g. - lava, fire, etc.)
     * @return isBurnable
     */
    public boolean isBurnable() {
        return true;
    }

    /**
     * Setting to prevent this item from being used as fuel in a furnace
     * @return isFuel
     */
    public boolean isFuel() {
        return false;
    }

    /**
     * Setting to prevent this item from being placed in a non-result anvil slot
     * @return isRepairable
     */
    public boolean isRepairable() {
        return false;
    }

    /**
     * Setting to prevent this item from being used in a crafting recipe
     * @return isRecipeItem
     */
    public boolean isRecipeItem() {
        return false;
    }

    /**
     * Setting to prevent this item from being picked up by hoppers
     * @return isHopperCollectible
     */
    public boolean isHopperCollectible() {
        return true;
    }

    /**
     * When a block is broken by a player holding this item
     * @param wrapper wrapper
     */
    public void onBlockBreak(BlockBreakEventWrapper wrapper) {}

    /**
     * When this item is enchanted via enchantment table
     * @param wrapper wrapper
     */
    public void onItemEnchant(EnchantItemEventWrapper wrapper) {
        if (isEnchantable()) {
            return;
        }
        wrapper.getEvent().setCancelled(true);
    }

    /**
     * When this item merges with another item
     * @param wrapper wrapper
     */
    public void onItemMerge(ItemMergeEventWrapper wrapper) {}

    /**
     * When this item despawns
     * @param wrapper wrapper
     */
    public void onItemDespawn(ItemDespawnEventWrapper wrapper) {}

    /**
     * When a player joins holding this item
     * @param wrapper wrapper
     */
    public void onPlayerJoin(PlayerJoinEventWrapper wrapper) {}

    /**
     * When a player quits holding this item
     * @param wrapper wrapper
     */
    public void onPlayerQuit(PlayerQuitEventWrapper wrapper) {}

    /**
     * When a player dies holding this item
     * @param wrapper wrapper
     */
    public void onPlayerDeath(PlayerDeathEventWrapper wrapper) {}

    /**
     * When a furnace uses this item as fuel
     * @param wrapper wrapper
     */
    public void onFurnaceBurn(FurnaceBurnEventWrapper wrapper) {
        if (isFuel()) {
            return;
        }
        wrapper.getEvent().setCancelled(true);
    }

    /**
     * When a hopper collects this item
     * @param wrapper wrapper
     */
    public void onHopperPickup(InventoryPickupItemEventWrapper wrapper) {
        if (isHopperCollectible()) {
            return;
        }
        wrapper.getEvent().setCancelled(true);
    }

    /**
     * When a player drops this item
     * @param wrapper wrapper
     */
    public void onPlayerItemDrop(PlayerDropItemEventWrapper wrapper) {}

    /**
     * When a player uses this item and it breaks (e.g. - any tool)
     * @param wrapper wrapper
     */
    public void onPlayerItemBreak(PlayerItemBreakEventWrapper wrapper) {}

    /**
     * When a player uses this item and its durability decreases (e.g. - any tool)
     * @param wrapper wrapper
     */
    public void onPlayerItemDamage(PlayerItemDamageEventWrapper wrapper) {}

    /**
     * When a player equips or unequips this item
     * @param wrapper wrapper
     */
    public void onPlayerItemHeld(PlayerItemHeldEventWrapper wrapper) {}

    /**
     * When a player picks this item up
     * @param wrapper wrapper
     */
    public void onPlayerItemPickup(PlayerPickupItemEventWrapper wrapper) {}

    /**
     * When a player sneaks while holding this item
     * @param wrapper wrapper
     */
    public void onPlayerSneak(PlayerToggleSneakEventWrapper wrapper) {}

    /**
     * When a player interacts with this item
     * @param wrapper wrapper
     */
    public void onPlayerInteract(PlayerInteractEventWrapper wrapper) {}

    /**
     * When this item is damaged (e.g. - cactus, lava, fire, explosion, etc.)
     * @param wrapper wrapper
     */
    public void onItemDamage(EntityDamageEventWrapper wrapper) {
        if (isBurnable()) {
            return;
        }

        final EntityDamageEvent event = wrapper.getEvent();
        final EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause != EntityDamageEvent.DamageCause.FIRE && cause != EntityDamageEvent.DamageCause.FIRE_TICK) {
            return;
        }

        event.setCancelled(true);
    }

    /**
     * When this item is used as a recipe item while crafting
     * @param wrapper wrapper
     */
    public void onItemCraft(PrepareItemCraftEventWrapper wrapper) {
        if (isRecipeItem()) {
            return;
        }
        wrapper.getEvent().getInventory().setResult(null);
    }

    /**
     * When this item is placed in a non-result anvil slot
     * @param wrapper
     */
    public void onItemRepair(AnvilEventWrapper wrapper) {
        if (isRepairable()) {
            return;
        }
        wrapper.getEvent().setCancelled(true);
    }

}
