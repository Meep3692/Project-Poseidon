package org.bukkit.event;

import java.io.Serializable;

import org.bukkit.entity.Projectile;
import org.bukkit.generator.BlockPopulator;

/**
 * Represents an event
 */
public abstract class Event implements Serializable {

    protected Event() {
    }

    protected Event(String name){
        
    }

    /**
     * Represents an events priority in execution
     */
    public enum Priority {

        /**
         * Event call is of very low importance and should be ran first, to allow
         * other plugins to further customise the outcome
         */
        Lowest,
        /**
         * Event call is of low importance
         */
        Low,
        /**
         * Event call is neither important or unimportant, and may be ran normally
         */
        Normal,
        /**
         * Event call is of high importance
         */
        High,
        /**
         * Event call is critical and must have the final say in what happens
         * to the event
         */
        Highest,
        /**
         * Event is listened to purely for monitoring the outcome of an event.
         * <p>
         * No modifications to the event should be made under this priority
         */
        Monitor
    }

    /**
     * Represents a category used by Type
     */
    public enum Category {

        /**
         * POSEIDON CATEGORIES
         */
        PACKET,

        /**
         * Represents Player-based events
         *
         * @see Category.LIVING_ENTITY
         */
        PLAYER,
        /**
         * Represents Entity-based events
         */
        ENTITY,
        /**
         * Represents Block-based events
         */
        BLOCK,
        /**
         * Represents LivingEntity-based events
         */
        LIVING_ENTITY,
        /**
         * Represents Weather-based events
         */
        WEATHER,
        /**
         * Represents Vehicle-based events
         */
        VEHICLE,
        /**
         * Represents World-based events
         */
        WORLD,
        /**
         * Represents Server and Plugin based events
         */
        SERVER,
        /**
         * Represents Inventory-based events
         */
        INVENTORY,
        /**
         * Represents any miscellaneous events
         */
        MISCELLANEOUS;
    }

    /**
     * Provides a lookup for legacy events
     *
     * @see org.bukkit.event.
     */
    @Deprecated
    public enum Type {

        /**
         * POSEIDON EVENTS
         */

        PLAYER_RECEIVE_PACKET(Category.PACKET),

        PLAYER_SEND_PACKET(Category.PACKET),

        PACKET_RECEIVED(Category.PACKET),


        /**
         * Called when a player first starts their connection. Called before UUID is known.
         *
         * @see org.bukkit.event.player.PlayerConnectionInitializationEvent
         */
        Player_Connection_Initialization(Category.PLAYER),


        /**
         * PLAYER EVENTS
         */

        /**
         * Called when a player enters the world on a server
         *
         * @see org.bukkit.event.player.PlayerJoinEvent
         */
        PLAYER_JOIN(Category.PLAYER),
        /**
         * Called when a player is attempting to connect to the server
         *
         * @see org.bukkit.event.player.PlayerLoginEvent
         */
        PLAYER_LOGIN(Category.PLAYER),
        /**
         * Called when a player has just been authenticated
         *
         * @see org.bukkit.event.player.PlayerPreLoginEvent
         */
        PLAYER_PRELOGIN(Category.PLAYER),
        /**
         * Called when a player respawns
         *
         * @see org.bukkit.event.player.PlayerRespawnEvent
         */
        PLAYER_RESPAWN(Category.PLAYER),
        /**
         * Called when a player gets kicked from the server
         *
         * @see org.bukkit.event.player.PlayerKickEvent
         */
        PLAYER_KICK(Category.PLAYER),
        /**
         * Called when a player sends a chat message
         *
         * @see org.bukkit.event.player.PlayerChatEvent
         */
        PLAYER_CHAT(Category.PLAYER),
        /**
         * Called when a player uses a command (early in the command handling process)
         *
         * @see org.bukkit.event.player.PlayerCommandPreprocessEvent
         */
        PLAYER_COMMAND_PREPROCESS(Category.PLAYER),
        /**
         * Called when a player leaves the server
         *
         * @see org.bukkit.event.player.PlayerQuitEvent
         */
        PLAYER_QUIT(Category.PLAYER),
        /**
         * Called when a player moves position in the world
         *
         * @see org.bukkit.event.player.PlayerMoveEvent
         */
        PLAYER_MOVE(Category.PLAYER),
        /**
         * Called before a player gets a velocity vector sent, which will instruct him to
         * get "pushed" into a specific direction, e.g. after an explosion
         *
         * @see org.bukkit.event.player.PlayerVelocityEvent
         */
        PLAYER_VELOCITY(Category.PLAYER),
        /**
         * Called when a player undergoes an animation (Arm Swing is the only animation currently supported)
         *
         * @see org.bukkit.event.player.PlayerAnimationEvent
         */
        PLAYER_ANIMATION(Category.PLAYER),
        /**
         * Called when a player toggles sneak mode
         *
         * @see org.bukkit.event.player.PlayerToggleSneakEvent
         */
        PLAYER_TOGGLE_SNEAK(Category.PLAYER),
        /**
         * Called when a player interacts with an object or air
         *
         * @see org.bukkit.event.player.PlayerInteractEvent
         */
        PLAYER_INTERACT(Category.PLAYER),
        /**
         * Called when a player right clicks an entity
         *
         * @see org.bukkit.event.player.PlayerInteractEntityEvent
         */
        PLAYER_INTERACT_ENTITY(Category.PLAYER),
        /**
         * Called when a player throws an egg
         *
         * @see org.bukkit.event.player.PlayerEggThrowEvent
         */
        PLAYER_EGG_THROW(Category.PLAYER),
        /**
         * Called when a player teleports from one position to another
         *
         * @see org.bukkit.event.player.PlayerTeleportEvent
         */
        PLAYER_TELEPORT(Category.PLAYER),
        /**
         * Called when a player completes the portaling process by standing in a portal
         *
         * @see org.bukkit.event.player.PlayerPortalEvent
         */
        PLAYER_PORTAL(Category.PLAYER),
        /**
         * Called when a player changes their held item
         *
         * @see org.bukkit.event.player.PlayerItemHeldEvent
         */
        PLAYER_ITEM_HELD(Category.PLAYER),
        /**
         * Called when a player drops an item
         *
         * @see org.bukkit.event.player.PlayerDropItemEvent
         */
        PLAYER_DROP_ITEM(Category.PLAYER),
        /**
         * Called when a player picks an item up off the ground
         *
         * @see org.bukkit.event.player.PlayerPickupItemEvent
         */
        PLAYER_PICKUP_ITEM(Category.PLAYER),
        /**
         * Called after a player has changed to a new world
         *
         * @see org.bukkit.event.player.PlayerChangedWorldEvent
         */
        PLAYER_CHANGED_WORLD(Category.PLAYER),
        /**
         * Called when a player empties a bucket
         *
         * @see org.bukkit.event.player.PlayerBucketEmptyEvent
         */
        PLAYER_BUCKET_EMPTY(Category.PLAYER),
        /**
         * Called when a player fills a bucket
         *
         * @see org.bukkit.event.player.PlayerBucketFillEvent
         */
        PLAYER_BUCKET_FILL(Category.PLAYER),
        /**
         * Called when a player interacts with the inventory
         *
         * @see org.bukkit.event.player.PlayerInventoryEvent
         */
        PLAYER_INVENTORY(Category.PLAYER),
        /**
         * Called when a player enter a bed
         *
         * @see org.bukkit.event.player.PlayerBedEnterEvent
         */
        PLAYER_BED_ENTER(Category.PLAYER),
        /**
         * Called when a player leaves a bed
         *
         * @see org.bukkit.event.player.PlayerBedLeaveEvent
         */
        PLAYER_BED_LEAVE(Category.PLAYER),
        /**
         * Called when a player is fishing
         *
         * @see org.bukkit.event.player.PlayerFishEvent
         */
        PLAYER_FISH(Category.PLAYER),
        /**
         * Called when a player used item is damaged
         *
         * @see org.bukkit.event.player.PlayerItemDamageEvent
         */
        PLAYER_ITEM_DAMAGE(Category.PLAYER),

        /**
         * BLOCK EVENTS
         */

        /**
         * Called when a block is damaged (hit by a player)
         *
         * @see org.bukkit.event.block.BlockDamageEvent
         */
        BLOCK_DAMAGE(Category.BLOCK),
        /**
         * Called when a block is undergoing a universe physics
         * check on whether it can be built
         * <p>
         * For example, cacti cannot be built on grass unless overridden here
         *
         * @see org.bukkit.event.block.BlockCanBuildEvent
         */
        BLOCK_CANBUILD(Category.BLOCK),
        /**
         * Called when a block of water or lava attempts to flow into another
         * block
         *
         * @see org.bukkit.event.block.BlockFromToEvent
         */
        BLOCK_FROMTO(Category.BLOCK),
        /**
         * Called when a block is being set on fire from another block, such as
         * an adjacent block of fire attempting to set fire to wood
         *
         * @see org.bukkit.event.block.BlockIgniteEvent
         */
        BLOCK_IGNITE(Category.BLOCK),
        /**
         * Called when a block undergoes a physics check
         * <p>
         * A physics check is commonly called when an adjacent block changes
         * type
         *
         * @see org.bukkit.event.block.BlockPhysicsEvent
         */
        BLOCK_PHYSICS(Category.BLOCK),
        /**
         * Called when a player is attempting to place a block
         *
         * @see org.bukkit.event.block.BlockPlaceEvent
         */
        BLOCK_PLACE(Category.BLOCK),
        /**
         * Called when a block dispenses something
         *
         * @see org.bukkit.event.block.BlockDispenseEvent
         */
        BLOCK_DISPENSE(Category.BLOCK),
        /**
         * Called when a block is destroyed from being burnt by fire
         *
         * @see org.bukkit.event.block.BlockBurnEvent
         */
        BLOCK_BURN(Category.BLOCK),
        /**
         * Called when leaves are decaying naturally
         *
         * @see org.bukkit.event.block.LeavesDecayEvent
         */
        LEAVES_DECAY(Category.BLOCK),
        /**
         * Called when a sign is changed
         *
         * @see org.bukkit.event.block.SignChangeEvent
         */
        SIGN_CHANGE(Category.BLOCK),
        /**
         * Called when a block changes redstone current. Only triggered on blocks
         * that are actually capable of transmitting or carrying a redstone
         * current
         *
         * @see org.bukkit.event.block.BlockRedstoneEvent
         */
        REDSTONE_CHANGE(Category.BLOCK),
        /**
         * Called when a block is broken by a player
         *
         * @see org.bukkit.event.block.BlockBreakEvent
         */
        BLOCK_BREAK(Category.BLOCK),
        /**
         * Called when a block is formed based on world conditions
         *
         * @see org.bukkit.event.block.BlockFormEvent
         */
        BLOCK_FORM(Category.BLOCK),
        /**
         * Called when a block spreads based on world conditions
         *
         * @see org.bukkit.event.block.BlockSpreadEvent
         */
        BLOCK_SPREAD(Category.BLOCK),
        /**
         * Called when a block fades, melts or disappears based on world conditions
         *
         * @see org.bukkit.event.block.BlockFadeEvent
         */
        BLOCK_FADE(Category.BLOCK),
        /**
         * Called when a piston extends
         *
         * @see org.bukkit.event.block.PistonExtendEvent
         */
        BLOCK_PISTON_EXTEND(Category.BLOCK),
        /**
         * Called when a piston retracts
         *
         * @see org.bukkit.event.block.PistonRetractEvent
         */
        BLOCK_PISTON_RETRACT(Category.BLOCK),

        /**
         * INVENTORY EVENTS
         */

        /**
         * Called when a player opens an inventory
         *
         * @todo: add javadoc see comment
         */
        INVENTORY_OPEN(Category.INVENTORY),
        /**
         * Called when a player closes an inventory
         *
         * @todo: add javadoc see comment
         */
        INVENTORY_CLOSE(Category.INVENTORY),
        /**
         * Called when a player clicks on an inventory slot
         *
         * @todo: add javadoc see comment
         */
        INVENTORY_CLICK(Category.INVENTORY),
        /**
         * Called when an inventory slot changes values or type
         *
         * @todo: add javadoc see comment
         */
        INVENTORY_CHANGE(Category.INVENTORY),
        /**
         * Called when a player is attempting to perform an inventory transaction
         *
         * @todo: add javadoc see comment
         */
        INVENTORY_TRANSACTION(Category.INVENTORY),
        /**
         * Called when an ItemStack is successfully smelted in a furnace.
         *
         * @see org.bukkit.event.inventory.FurnaceSmeltEvent
         */
        FURNACE_SMELT(Category.INVENTORY),
        /**
         * Called when an ItemStack is successfully burned as fuel in a furnace.
         *
         * @see org.bukkit.event.inventory.FurnaceBurnEvent
         */
        FURNACE_BURN(Category.INVENTORY),

        /**
         * SERVER EVENTS
         */

        /**
         * Called when a plugin is enabled
         *
         * @see org.bukkit.event.server.PluginEnableEvent
         */
        PLUGIN_ENABLE(Category.SERVER),
        /**
         * Called when a plugin is disabled
         *
         * @see org.bukkit.event.server.PluginDisableEvent
         */
        PLUGIN_DISABLE(Category.SERVER),
        /**
         * Called when a server command is called
         *
         * @see org.bukkit.event.server.ServerCommandEvent
         */
        SERVER_COMMAND(Category.SERVER),
        /**
         * Called when a map is initialized (created or loaded into memory)
         *
         * @see org.bukkit.event.server.MapInitializeEvent
         */
        MAP_INITIALIZE(Category.SERVER),

        /**
         * WORLD EVENTS
         */

        /**
         * Called when a chunk is loaded
         * <p>
         * If a new chunk is being generated for loading, it will call
         * Type.CHUNK_GENERATION and then Type.CHUNK_LOADED upon completion
         *
         * @see org.bukkit.event.world.ChunkLoadEvent
         */
        CHUNK_LOAD(Category.WORLD),
        /**
         * Called when a chunk is unloaded
         *
         * @see org.bukkit.event.world.ChunkUnloadEvent
         */
        CHUNK_UNLOAD(Category.WORLD),
        /**
         * Called when a newly created chunk has been populated.
         * <p>
         * If your intent is to populate the chunk using this event, please see {@link BlockPopulator}
         *
         * @see org.bukkit.event.world.ChunkPopulateEvent
         */
        CHUNK_POPULATED(Category.WORLD),
        /**
         * Called when an ItemEntity spawns in the world
         *
         * @see org.bukkit.event.entity.ItemSpawnEvent
         */
        ITEM_SPAWN(Category.WORLD),
        /**
         * Called when a World's spawn is changed
         *
         * @see org.bukkit.event.world.SpawnChangeEvent
         */
        SPAWN_CHANGE(Category.WORLD),
        /**
         * Called when a world is saved
         *
         * @see org.bukkit.event.world.WorldSaveEvent
         */
        WORLD_SAVE(Category.WORLD),
        /**
         * Called when a World is initializing
         *
         * @see org.bukkit.event.world.WorldInitEvent
         */
        WORLD_INIT(Category.WORLD),
        /**
         * Called when a World is loaded
         *
         * @see org.bukkit.event.world.WorldLoadEvent
         */
        WORLD_LOAD(Category.WORLD),
        /**
         * Called when a World is unloaded
         *
         * @see org.bukkit.event.world.WorldUnloadEvent
         */
        WORLD_UNLOAD(Category.WORLD),
        /**
         * Called when world attempts to create a matching end to a portal
         *
         * @see org.bukkit.event.world.PortalCreateEvent
         */
        PORTAL_CREATE(Category.WORLD),

        /**
         * ENTITY EVENTS
         */

        /**
         * Called when an item despawns
         *
         * @see org.bukkit.event.entity.ItemDespawnEvent
         */
        ITEM_DESPAWN(Category.ENTITY),
        /**
         * Called when a painting is placed by player
         *
         * @see org.bukkit.event.painting.PaintingPlaceEvent
         */
        PAINTING_PLACE(Category.ENTITY),
        /**
         * Called when a painting is removed
         *
         * @see org.bukkit.event.painting.PaintingBreakEvent
         */
        PAINTING_BREAK(Category.ENTITY),
        /**
         * Called when an entity touches a portal block
         *
         * @see org.bukkit.event.entity.EntityPortalEnterEvent
         */
        ENTITY_PORTAL_ENTER(Category.ENTITY),

        /**
         * LIVING_ENTITY EVENTS
         */

        /**
         * Called when a creature, either hostile or neutral, attempts to spawn
         * in the world "naturally"
         *
         * @see org.bukkit.event.entity.CreatureSpawnEvent
         */
        CREATURE_SPAWN(Category.LIVING_ENTITY),
        /**
         * Called when a LivingEntity is damaged with no source.
         *
         * @see org.bukkit.event.entity.EntityDamageEvent
         */
        ENTITY_DAMAGE(Category.LIVING_ENTITY),
        // Project Poseidon Start
        ENTITY_DAMAGE_BY_ENTITY(Category.LIVING_ENTITY),
        ENTITY_DAMAGE_BY_BLOCK(Category.LIVING_ENTITY),
        // Project Poseidon End
        /**
         * Called when a LivingEntity dies
         *
         * @see org.bukkit.event.entity.EntityDeathEvent
         */
        ENTITY_DEATH(Category.LIVING_ENTITY),
        /**
         * Called when a Skeleton or Zombie catch fire due to the sun
         *
         * @see org.bukkit.event.entity.EntityCombustEvent
         */
        ENTITY_COMBUST(Category.LIVING_ENTITY),
        /**
         * Called when an entity explodes, either TNT, Creeper, or Ghast Fireball
         *
         * @see org.bukkit.event.entity.EntityExplodeEvent
         */
        ENTITY_EXPLODE(Category.LIVING_ENTITY),
        /**
         * Called when an entity has made a decision to explode.
         * <p>
         * Provides an opportunity to act on the entity, change the explosion radius,
         * or to change the fire-spread flag.
         * <p>
         * Canceling the event negates the entity's decision to explode.
         * For EntityCreeper, this resets the fuse but does not kill the Entity.
         * For EntityFireball and EntityTNTPrimed....?
         *
         * @see org.bukkit.event.entity.ExplosionPrimeEvent
         */
        EXPLOSION_PRIME(Category.LIVING_ENTITY),
        /**
         * Called when an entity targets another entity
         *
         * @see org.bukkit.event.entity.EntityTargetEvent
         */
        ENTITY_TARGET(Category.LIVING_ENTITY),
        /**
         * Called when an entity interacts with a block
         * This event specifically excludes player entities
         *
         * @see org.bukkit.event.entity.EntityInteractEvent
         */
        ENTITY_INTERACT(Category.LIVING_ENTITY),
        /**
         * Called when a creeper gains or loses a power shell
         *
         * @see org.bukkit.event.entity.CreeperPowerEvent
         */
        CREEPER_POWER(Category.LIVING_ENTITY),
        /**
         * Called when a pig is zapped, zombifying it
         *
         * @see org.bukkit.event.entity.PigZapEvent
         */
        PIG_ZAP(Category.LIVING_ENTITY),
        /**
         * Called when a LivingEntity is tamed
         *
         * @see org.bukkit.event.entity.EntityTameEvent
         */
        ENTITY_TAME(Category.LIVING_ENTITY),
        /**
         * Called when a {@link Projectile} hits something
         *
         * @see org.bukkit.event.entity.ProjectileHitEvent
         */
        PROJECTILE_HIT(Category.ENTITY),

        /**
         * Called when a LivingEntity is regains health
         *
         * @see org.bukkit.event.entity.EntityRegainHealthEvent
         */
        ENTITY_REGAIN_HEALTH(Category.LIVING_ENTITY),

        /**
         * WEATHER EVENTS
         */

        /**
         * Called when a lightning entity strikes somewhere
         *
         * @see org.bukkit.event.weather.LightningStrikeEvent
         */
        LIGHTNING_STRIKE(Category.WEATHER),
        /**
         * Called when the weather in a world changes
         *
         * @see org.bukkit.event.weather.WeatherChangeEvent
         */
        WEATHER_CHANGE(Category.WEATHER),
        /**
         * Called when the thunder state in a world changes
         *
         * @see org.bukkit.event.weather.ThunderChangeEvent
         */
        THUNDER_CHANGE(Category.WEATHER),

        /**
         * VEHICLE EVENTS
         */

        /**
         * Called when a vehicle is placed by a player
         *
         * @see org.bukkit.event.vehicle.VehicleCreateEvent
         */
        VEHICLE_CREATE(Category.VEHICLE),
        /**
         * Called when a vehicle is destroyed
         *
         * @see org.bukkit.event.vehicle.VehicleDestroyEvent
         */
        VEHICLE_DESTROY(Category.VEHICLE),
        /**
         * Called when a vehicle is damaged by a LivingEntity
         *
         * @see org.bukkit.event.vehicle.VehicleDamageEvent
         */
        VEHICLE_DAMAGE(Category.VEHICLE),
        /**
         * Called when a vehicle collides with an Entity
         *
         * @see org.bukkit.event.vehicle.VehicleCollisionEvent
         */
        VEHICLE_COLLISION_ENTITY(Category.VEHICLE),
        /**
         * Called when a vehicle collides with a Block
         *
         * @see org.bukkit.event.vehicle.VehicleBlockCollisionEvent
         */
        VEHICLE_COLLISION_BLOCK(Category.VEHICLE),
        /**
         * Called when a vehicle is entered by a LivingEntity
         *
         * @see org.bukkit.event.vehicle.VehicleEnterEvent
         */
        VEHICLE_ENTER(Category.VEHICLE),
        /**
         * Called when a vehicle is exited by a LivingEntity
         *
         * @see org.bukkit.event.vehicle.VehicleExitEvent
         */
        VEHICLE_EXIT(Category.VEHICLE),
        /**
         * Called when a vehicle moves position in the world
         *
         * @see org.bukkit.event.vehicle.VehicleMoveEvent
         */
        VEHICLE_MOVE(Category.VEHICLE),
        /**
         * Called when a vehicle is going through an update cycle, rechecking itself
         *
         * @see org.bukkit.event.vehicle.VehicleUpdateEvent
         */
        VEHICLE_UPDATE(Category.VEHICLE),
        /**
         * MISCELLANEOUS EVENTS
         */

        /**
         * Represents a custom event, isn't actually used
         */
        CUSTOM_EVENT(Category.MISCELLANEOUS);

        private final Category category;

        private Type(Category category) {
            this.category = category;
        }

        // Project Poseidon Start
        public static Type getTypeByName(String name) {
            for (Type type : Type.values()) {
                String typeName = type.name();
                String typeName_ = typeName.replace("_", "");
                if (name.equalsIgnoreCase(typeName))
                    return type;
                if (name.equalsIgnoreCase(typeName_))
                    return type;
            }
            return null;
        }
        // Project Poseidon End

        /**
         * Gets the Category assigned to this event
         *
         * @return Category of this Event.Type
         */
        public Category getCategory() {
            return category;
        }

        public Class<? extends Event> getEventClass(){
            switch(this){
                case BLOCK_BREAK:
                    return org.bukkit.event.block.BlockBreakEvent.class;
                case BLOCK_BURN:
                    return org.bukkit.event.block.BlockBurnEvent.class;
                case BLOCK_CANBUILD:
                    return org.bukkit.event.block.BlockCanBuildEvent.class;
                case BLOCK_DAMAGE:
                    return org.bukkit.event.block.BlockDamageEvent.class;
                case BLOCK_DISPENSE:
                    return org.bukkit.event.block.BlockDispenseEvent.class;
                case BLOCK_FADE:
                    return org.bukkit.event.block.BlockFadeEvent.class;
                case BLOCK_FORM:
                    return org.bukkit.event.block.BlockFormEvent.class;
                case BLOCK_FROMTO:
                    return org.bukkit.event.block.BlockFromToEvent.class;
                case BLOCK_IGNITE:
                    return org.bukkit.event.block.BlockIgniteEvent.class;
                case BLOCK_PHYSICS:
                    return org.bukkit.event.block.BlockPhysicsEvent.class;
                case BLOCK_PISTON_EXTEND:
                    return org.bukkit.event.block.BlockPistonExtendEvent.class;
                case BLOCK_PISTON_RETRACT:
                    return org.bukkit.event.block.BlockPistonRetractEvent.class;
                case BLOCK_PLACE:
                    return org.bukkit.event.block.BlockPlaceEvent.class;
                case BLOCK_SPREAD:
                    return org.bukkit.event.block.BlockSpreadEvent.class;
                case CHUNK_LOAD:
                    return org.bukkit.event.world.ChunkLoadEvent.class;
                case CHUNK_POPULATED:
                    return org.bukkit.event.world.ChunkPopulateEvent.class;
                case CHUNK_UNLOAD:
                    return org.bukkit.event.world.ChunkUnloadEvent.class;
                case CREATURE_SPAWN:
                    return org.bukkit.event.entity.CreatureSpawnEvent.class;
                case CREEPER_POWER:
                    return org.bukkit.event.entity.CreeperPowerEvent.class;
                case CUSTOM_EVENT:
                    return org.bukkit.event.Event.class;
                case ENTITY_COMBUST:
                    return org.bukkit.event.entity.EntityCombustEvent.class;
                case ENTITY_DAMAGE:
                    return org.bukkit.event.entity.EntityDamageEvent.class;
                case ENTITY_DAMAGE_BY_BLOCK:
                    return org.bukkit.event.entity.EntityDamageByBlockEvent.class;
                case ENTITY_DAMAGE_BY_ENTITY:
                    return org.bukkit.event.entity.EntityDamageByEntityEvent.class;
                case ENTITY_DEATH:
                    return org.bukkit.event.entity.EntityDeathEvent.class;
                case ENTITY_EXPLODE:
                    return org.bukkit.event.entity.EntityExplodeEvent.class;
                case ENTITY_INTERACT:
                    return org.bukkit.event.entity.EntityInteractEvent.class;
                case ENTITY_PORTAL_ENTER:
                    return org.bukkit.event.entity.EntityPortalEnterEvent.class;
                case ENTITY_REGAIN_HEALTH:
                    return org.bukkit.event.entity.EntityRegainHealthEvent.class;
                case ENTITY_TAME:
                    return org.bukkit.event.entity.EntityTameEvent.class;
                case ENTITY_TARGET:
                    return org.bukkit.event.entity.EntityTargetEvent.class;
                case EXPLOSION_PRIME:
                    return org.bukkit.event.entity.ExplosionPrimeEvent.class;
                case FURNACE_BURN:
                    return org.bukkit.event.inventory.FurnaceBurnEvent.class;
                case FURNACE_SMELT:
                    return org.bukkit.event.inventory.FurnaceSmeltEvent.class;
                case INVENTORY_TRANSACTION:
                    return org.bukkit.event.inventory.InventoryTransactionEvent.class;
                case ITEM_DESPAWN:
                    return org.bukkit.event.entity.ItemDespawnEvent.class;
                case ITEM_SPAWN:
                    return org.bukkit.event.entity.ItemSpawnEvent.class;
                case LEAVES_DECAY:
                    return org.bukkit.event.block.LeavesDecayEvent.class;
                case LIGHTNING_STRIKE:
                    return org.bukkit.event.weather.LightningStrikeEvent.class;
                case MAP_INITIALIZE:
                    return org.bukkit.event.server.MapInitializeEvent.class;
                case PACKET_RECEIVED:
                    return org.bukkit.event.packet.PacketReceivedEvent.class;
                case PAINTING_BREAK:
                    return org.bukkit.event.painting.PaintingBreakEvent.class;
                case PAINTING_PLACE:
                    return org.bukkit.event.painting.PaintingPlaceEvent.class;
                case PIG_ZAP:
                    return org.bukkit.event.entity.PigZapEvent.class;
                case PLAYER_ANIMATION:
                    return org.bukkit.event.player.PlayerAnimationEvent.class;
                case PLAYER_BED_ENTER:
                    return org.bukkit.event.player.PlayerBedEnterEvent.class;
                case PLAYER_BED_LEAVE:
                    return org.bukkit.event.player.PlayerBedLeaveEvent.class;
                case PLAYER_BUCKET_EMPTY:
                    return org.bukkit.event.player.PlayerBucketEmptyEvent.class;
                case PLAYER_BUCKET_FILL:
                    return org.bukkit.event.player.PlayerBucketFillEvent.class;
                case PLAYER_CHANGED_WORLD:
                    return org.bukkit.event.player.PlayerChangedWorldEvent.class;
                case PLAYER_CHAT:
                    return org.bukkit.event.player.PlayerChatEvent.class;
                case PLAYER_COMMAND_PREPROCESS:
                    return org.bukkit.event.player.PlayerCommandPreprocessEvent.class;
                case PLAYER_DROP_ITEM:
                    return org.bukkit.event.player.PlayerDropItemEvent.class;
                case PLAYER_EGG_THROW:
                    return org.bukkit.event.player.PlayerEggThrowEvent.class;
                case PLAYER_FISH:
                    return org.bukkit.event.player.PlayerFishEvent.class;
                case PLAYER_INTERACT:
                    return org.bukkit.event.player.PlayerInteractEvent.class;
                case PLAYER_INTERACT_ENTITY:
                    return org.bukkit.event.player.PlayerInteractEntityEvent.class;
                case PLAYER_INVENTORY:
                    return org.bukkit.event.player.PlayerInventoryEvent.class;
                case PLAYER_ITEM_DAMAGE:
                    return org.bukkit.event.player.PlayerItemDamageEvent.class;
                case PLAYER_ITEM_HELD:
                    return org.bukkit.event.player.PlayerItemHeldEvent.class;
                case PLAYER_JOIN:
                    return org.bukkit.event.player.PlayerJoinEvent.class;
                case PLAYER_KICK:
                    return org.bukkit.event.player.PlayerKickEvent.class;
                case PLAYER_LOGIN:
                    return org.bukkit.event.player.PlayerLoginEvent.class;
                case PLAYER_MOVE:
                    return org.bukkit.event.player.PlayerMoveEvent.class;
                case PLAYER_PICKUP_ITEM:
                    return org.bukkit.event.player.PlayerPickupItemEvent.class;
                case PLAYER_PORTAL:
                    return org.bukkit.event.player.PlayerPortalEvent.class;
                case PLAYER_PRELOGIN:
                    return org.bukkit.event.player.PlayerPreLoginEvent.class;
                case PLAYER_QUIT:
                    return org.bukkit.event.player.PlayerQuitEvent.class;
                case PLAYER_RECEIVE_PACKET:
                    return com.legacyminecraft.poseidon.event.PlayerReceivePacketEvent.class;
                case PLAYER_RESPAWN:
                    return org.bukkit.event.player.PlayerRespawnEvent.class;
                case PLAYER_SEND_PACKET:
                    return com.legacyminecraft.poseidon.event.PlayerSendPacketEvent.class;
                case PLAYER_TELEPORT:
                    return org.bukkit.event.player.PlayerTeleportEvent.class;
                case PLAYER_TOGGLE_SNEAK:
                    return org.bukkit.event.player.PlayerToggleSneakEvent.class;
                case PLAYER_VELOCITY:
                    return org.bukkit.event.player.PlayerVelocityEvent.class;
                case PLUGIN_DISABLE:
                    return org.bukkit.event.server.PluginDisableEvent.class;
                case PLUGIN_ENABLE:
                    return org.bukkit.event.server.PluginEnableEvent.class;
                case PORTAL_CREATE:
                    return org.bukkit.event.world.PortalCreateEvent.class;
                case PROJECTILE_HIT:
                    return org.bukkit.event.entity.ProjectileHitEvent.class;
                case Player_Connection_Initialization:
                    return org.bukkit.event.player.PlayerConnectionInitializationEvent.class;
                case REDSTONE_CHANGE:
                    return org.bukkit.event.block.BlockRedstoneEvent.class;
                case SERVER_COMMAND:
                    return org.bukkit.event.server.ServerCommandEvent.class;
                case SIGN_CHANGE:
                    return org.bukkit.event.block.SignChangeEvent.class;
                case SPAWN_CHANGE:
                    return org.bukkit.event.world.SpawnChangeEvent.class;
                case THUNDER_CHANGE:
                    return org.bukkit.event.weather.ThunderChangeEvent.class;
                case VEHICLE_COLLISION_BLOCK:
                    return org.bukkit.event.vehicle.VehicleBlockCollisionEvent.class;
                case VEHICLE_COLLISION_ENTITY:
                    return org.bukkit.event.vehicle.VehicleEntityCollisionEvent.class;
                case VEHICLE_CREATE:
                    return org.bukkit.event.vehicle.VehicleCreateEvent.class;
                case VEHICLE_DAMAGE:
                    return org.bukkit.event.vehicle.VehicleDamageEvent.class;
                case VEHICLE_DESTROY:
                    return org.bukkit.event.vehicle.VehicleDestroyEvent.class;
                case VEHICLE_ENTER:
                    return org.bukkit.event.vehicle.VehicleEnterEvent.class;
                case VEHICLE_EXIT:
                    return org.bukkit.event.vehicle.VehicleExitEvent.class;
                case VEHICLE_MOVE:
                    return org.bukkit.event.vehicle.VehicleMoveEvent.class;
                case VEHICLE_UPDATE:
                    return org.bukkit.event.vehicle.VehicleUpdateEvent.class;
                case WEATHER_CHANGE:
                    return org.bukkit.event.weather.WeatherChangeEvent.class;
                case WORLD_INIT:
                    return org.bukkit.event.world.WorldInitEvent.class;
                case WORLD_LOAD:
                    return org.bukkit.event.world.WorldLoadEvent.class;
                case WORLD_SAVE:
                    return org.bukkit.event.world.WorldSaveEvent.class;
                case WORLD_UNLOAD:
                    return org.bukkit.event.world.WorldUnloadEvent.class;
                default:
                    throw new IllegalArgumentException("Unable to convert legacy Event.Type to event class: " + this);

            }
        }
    }

    public enum Result {

        /**
         * Deny the event.
         * Depending on the event, the action indicated by the event will either not take place or will be reverted.
         * Some actions may not be denied.
         */
        DENY,
        /**
         * Neither deny nor allow the event.
         * The server will proceed with its normal handling.
         */
        DEFAULT,
        /**
         * Allow / Force the event.
         * The action indicated by the event will take place if possible, even if the server would not normally allow the action.
         * Some actions may not be allowed.
         */
        ALLOW;
    }
}
