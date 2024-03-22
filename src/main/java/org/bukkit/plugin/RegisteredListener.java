package org.bukkit.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * Stores relevant information for plugin listeners
 */
public class RegisteredListener implements Comparable<RegisteredListener>{
    private final Listener listener;
    private final Event.Priority priority;
    private final Plugin plugin;
    private final EventExecutor executor;
    private final Class<? extends Event> type;

    public RegisteredListener(final Listener pluginListener, final EventExecutor eventExecutor, final Event.Priority eventPriority, final Plugin registeredPlugin, final Class<? extends Event> type) {
        listener = pluginListener;
        priority = eventPriority;
        plugin = registeredPlugin;
        executor = eventExecutor;
        this.type = type;
    }

    public void registerAll() {

    }

    /**
     * Gets the listener for this registration
     * @return Registered Listener
     */
    public Listener getListener() {
        return listener;
    }

    /**
     * Gets the plugin for this registration
     * @return Registered Plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Gets the priority for this registration
     * @return Registered Priority
     */
    public Event.Priority getPriority() {
        return priority;
    }

    /**
     * Gets the type of event for this registration
     * @return Registered Event Type
     */
    public Class<? extends Event> getType() {
        return type;
    }

    /**
     * Calls the event executor
     * @return Registered Priority
     */
    public void callEvent(Event event) {
        executor.execute(listener, event);
    }

    @Override
    public int compareTo(RegisteredListener o) {
        int result = this.getPriority().compareTo(o.getPriority());

        if ((result == 0) && (this != o)) {
            result = 1;
        }

        return result;
    }
}
