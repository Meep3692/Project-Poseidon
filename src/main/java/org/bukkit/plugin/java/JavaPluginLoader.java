package org.bukkit.plugin.java;

import com.legacyminecraft.poseidon.event.PoseidonCustomListener;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Represents a Java plugin loader, allowing plugins in the form of .jar
 */
public class JavaPluginLoader implements PluginLoader
{
    private final Server server;
    protected final Pattern[] fileFilters = new Pattern[] { Pattern.compile("\\.jar$"), };
    protected final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
    protected final Map<String, PluginClassLoader> loaders = new HashMap<String, PluginClassLoader>();

    public JavaPluginLoader(Server instance)
    {
        server = instance;
    }

    public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException
    {
        return loadPlugin(file, false);
    }

    public Plugin loadPlugin(File file, boolean ignoreSoftDependencies) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException
    {
        JavaPlugin result = null;
        PluginDescriptionFile description = null;

        if (!file.exists())
        {
            throw new InvalidPluginException(new FileNotFoundException(String.format("%s does not exist", file.getPath())));
        }
        try
        {
            JarFile jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("plugin.yml");

            if (entry == null)
            {
                throw new InvalidPluginException(new FileNotFoundException("Jar does not contain plugin.yml"));
            }

            InputStream stream = jar.getInputStream(entry);

            description = new PluginDescriptionFile(stream);

            stream.close();
            jar.close();
        } catch (IOException ex)
        {
            throw new InvalidPluginException(ex);
        } catch (YAMLException ex)
        {
            throw new InvalidPluginException(ex);
        }

        File dataFolder = new File(file.getParentFile(), description.getName());
        File oldDataFolder = getDataFolder(file);

        // Found old data folder
        if (dataFolder.equals(oldDataFolder))
        {
            // They are equal -- nothing needs to be done!
        } else if (dataFolder.isDirectory() && oldDataFolder.isDirectory())
        {
            server.getLogger().log(Level.INFO, String.format("While loading %s (%s) found old-data folder: %s next to the new one: %s", description.getName(), file, oldDataFolder, dataFolder));
        } else if (oldDataFolder.isDirectory() && !dataFolder.exists())
        {
            if (!oldDataFolder.renameTo(dataFolder))
            {
                throw new InvalidPluginException(new Exception("Unable to rename old data folder: '" + oldDataFolder + "' to: '" + dataFolder + "'"));
            }
            server.getLogger().log(Level.INFO, String.format("While loading %s (%s) renamed data folder: '%s' to '%s'", description.getName(), file, oldDataFolder, dataFolder));
        }

        if (dataFolder.exists() && !dataFolder.isDirectory())
        {
            throw new InvalidPluginException(new Exception(String.format("Projected datafolder: '%s' for %s (%s) exists and is not a directory", dataFolder, description.getName(), file)));
        }

        ArrayList<String> depend;

        try
        {
            depend = (ArrayList) description.getDepend();
            if (depend == null)
            {
                depend = new ArrayList<String>();
            }
        } catch (ClassCastException ex)
        {
            throw new InvalidPluginException(ex);
        }

        for (String pluginName : depend)
        {
            if (loaders == null)
            {
                throw new UnknownDependencyException(pluginName);
            }
            PluginClassLoader current = loaders.get(pluginName);

            if (current == null)
            {
                throw new UnknownDependencyException(pluginName);
            }
        }

        if (!ignoreSoftDependencies)
        {
            ArrayList<String> softDepend;

            try
            {
                softDepend = (ArrayList) description.getSoftDepend();
                if (softDepend == null)
                {
                    softDepend = new ArrayList<String>();
                }
            } catch (ClassCastException ex)
            {
                throw new InvalidPluginException(ex);
            }

            for (String pluginName : softDepend)
            {
                if (loaders == null)
                {
                    throw new UnknownSoftDependencyException(pluginName);
                }
                PluginClassLoader current = loaders.get(pluginName);

                if (current == null)
                {
                    throw new UnknownSoftDependencyException(pluginName);
                }
            }
        }

        PluginClassLoader loader = null;

        try
        {
            URL[] urls = new URL[1];

            urls[0] = file.toURI().toURL();
            loader = new PluginClassLoader(this, urls, getClass().getClassLoader());
            Class<?> jarClass = Class.forName(description.getMain(), true, loader);
            Class<? extends JavaPlugin> plugin = jarClass.asSubclass(JavaPlugin.class);

            Constructor<? extends JavaPlugin> constructor = plugin.getConstructor();

            result = constructor.newInstance();

            result.initialize(this, server, description, dataFolder, file, loader);
        } catch (Throwable ex)
        {
            throw new InvalidPluginException(ex);
        }

        loaders.put(description.getName(), (PluginClassLoader) loader);

        return (Plugin) result;
    }

    // Project Poseidon Start
    private void notNull(Object object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
    }
    @Override
    @NotNull
    public Set<RegisteredListener> createRegisteredListeners(@NotNull Listener listener, @NotNull final Plugin plugin) {
        notNull(plugin, "Plugin can not be null");
        notNull(listener, "Listener can not be null");

        Set<RegisteredListener> ret = new HashSet<>();
        Set<Method> methods;
        try {
            Method[] publicMethods = listener.getClass().getMethods();
            Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet<Method>(publicMethods.length + privateMethods.length, 1.0f);
            Collections.addAll(methods, publicMethods);
            Collections.addAll(methods, privateMethods);
        } catch (NoClassDefFoundError e) {
            if (listener instanceof PoseidonCustomListener) {
                plugin.getServer().getLogger().log(Level.WARNING, "The plugin " + plugin.getDescription().getName() + " has tried to register an unknown event. Please ensure the plugin containing the event is loaded before any plugins that listen.");
            } else {
                plugin.getServer().getLogger().severe("Plugin " + plugin.getDescription().getFullName() + " has failed to register events for " + listener.getClass() + " because " + e.getMessage() + " does not exist.");

            }
            return ret;
        }

        for (final Method method : methods) {
            final EventHandler eh = method.getAnnotation(EventHandler.class);
            if (eh == null)
                continue;
            if (method.isBridge() || method.isSynthetic())
                continue;
            final Class<?> checkClass;
            if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0])) {
                plugin.getServer().getLogger().severe(plugin.getDescription().getFullName() + " attempted to register an invalid EventHandler method signature \"" + method.toGenericString() + "\" in " + listener.getClass());
                continue;
            }
            final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
            method.setAccessible(true);

            for (Class<?> clazz = eventClass; Event.class.isAssignableFrom(clazz); clazz = clazz.getSuperclass()) {
                if (clazz.getAnnotation(Deprecated.class) != null) {
                    plugin.getServer().getLogger().log(
                            Level.WARNING,
                            String.format(
                                    "\"%s\" has registered a listener for %s on method \"%s\", but the event is Deprecated." +
                                            " \"%s\"; please notify the authors %s.",
                                    plugin.getDescription().getFullName(),
                                    clazz.getName(),
                                    method.toGenericString(),
                                    "Server performance will be affected",
                                    Arrays.toString(plugin.getDescription().getAuthors().toArray())),
                            new AuthorNagException(null));
                    break;
                }
            }

            final EventExecutor executor = (listener1, event) -> {
                try {
                    method.invoke(listener1, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            };
            ret.add(new RegisteredListener(listener, executor, eh.priority(), plugin, eventClass));
        }
        return ret;
    }
    // Project Poseidon End

    protected File getDataFolder(File file)
    {
        File dataFolder = null;

        String filename = file.getName();
        int index = file.getName().lastIndexOf(".");

        if (index != -1)
        {
            String name = filename.substring(0, index);

            dataFolder = new File(file.getParentFile(), name);
        } else
        {
            // This is if there is no extension, which should not happen
            // Using _ to prevent name collision

            dataFolder = new File(file.getParentFile(), filename + "_");
        }

        return dataFolder;
    }

    public Pattern[] getPluginFileFilters()
    {
        return fileFilters;
    }

    public Class<?> getClassByName(final String name)
    {
        Class<?> cachedClass = classes.get(name);

        if (cachedClass != null)
        {
            return cachedClass;
        } else
        {
            for (String current : loaders.keySet())
            {
                PluginClassLoader loader = loaders.get(current);

                try
                {
                    cachedClass = loader.findClass(name, false);
                } catch (ClassNotFoundException cnfe)
                {
                }
                if (cachedClass != null)
                {
                    return cachedClass;
                }
            }
        }
        return null;
    }

    public void setClass(final String name, final Class<?> clazz)
    {
        if (!classes.containsKey(name))
        {
            classes.put(name, clazz);
        }
    }

    public void enablePlugin(final Plugin plugin)
    {
        if (!(plugin instanceof JavaPlugin))
        {
            throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
        }

        if (!plugin.isEnabled())
        {
            JavaPlugin jPlugin = (JavaPlugin) plugin;

            String pluginName = jPlugin.getDescription().getName();

            if (!loaders.containsKey(pluginName))
            {
                loaders.put(pluginName, (PluginClassLoader) jPlugin.getClassLoader());
            }

            try
            {
                jPlugin.setEnabled(true);
            } catch (Throwable ex)
            {
                server.getLogger().log(Level.SEVERE, "Error occurred while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
            }

            // Perhaps abort here, rather than continue going, but as it stands,
            // an abort is not possible the way it's currently written
            server.getPluginManager().callEvent(new PluginEnableEvent(plugin));
        }
    }

    public void disablePlugin(Plugin plugin)
    {
        if (!(plugin instanceof JavaPlugin))
        {
            throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
        }

        if (plugin.isEnabled())
        {
            JavaPlugin jPlugin = (JavaPlugin) plugin;
            ClassLoader cloader = jPlugin.getClassLoader();

            try
            {
                jPlugin.setEnabled(false);
            } catch (Throwable ex)
            {
                server.getLogger().log(Level.SEVERE, "Error occurred while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
            }

            server.getPluginManager().callEvent(new PluginDisableEvent(plugin));

            loaders.remove(jPlugin.getDescription().getName());

            if (cloader instanceof PluginClassLoader)
            {
                PluginClassLoader loader = (PluginClassLoader) cloader;
                Set<String> names = loader.getClasses();

                for (String name : names)
                {
                    classes.remove(name);
                }
            }
        }
    }
}
