package me.capitainecat0.multimaintenance.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class PluginCore<T extends PluginCore<?>> extends JavaPlugin {

    private static PluginCore<?> INSTANCE;
    private boolean DEBUG_ENABLED = false;
    private static String NAME;

    private static void setName(String name) {
        PluginCore.NAME = name;
    }

    /**
     * Method to run when the plugin is starting.
     * Returning false wil make the plugin stop by itself.
     */
    protected abstract boolean start(T instance);

    /**
     * Method to run when the plugin is disabling.
     */
    protected abstract void stop();

    /**
     * You should override this for static-based event listener.
     * <br> You can however register yourself events in the run time using {@link PluginCore#registerEvent(Listener)}
     */
    protected List<Listener> getPluginListeners() {
        return new ArrayList<>();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        final boolean shouldContinue = start((T) this);
        if (!shouldContinue)
            getServer().getPluginManager().disablePlugin(this);
        NAME = getName();
        for (Listener listener : getPluginListeners())
            registerEvent(listener);
    }

    /**
     * Register a new Bukkit command.
     * <br> This method will check by itself if the desired command exist in the <code>plugin.yml</code> file to avoid {@link NullPointerException}
     * @param executor The command executor related to the command
     * @param codeName The <code>plugin.yml</code> command name to use
     */
    public void registerCommand(CommandExecutor executor, String codeName) {
        PluginCommand command = getCommand(codeName);
        if (command == null)
            return;
        command.setExecutor(executor);
    }

    private static SimpleCommandMap injectCommandMap() {
        final SimpleCommandMap map;
        try {
            final Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            map = (SimpleCommandMap) commandMapField.get(Bukkit.getPluginManager());
        } catch (Exception ex) {
            return null;
        }
        return map;
    }

    /**
     * <bold>This method is not the default one!!</bold>
     * <br> This will use reflection in order to register into Bukkit custom command without adding them to the <code>plugin.yml</code> file.
     * <br> I however recommend using {@link PluginCore#registerCommand(CommandExecutor, String)} for registering a command.
     * @param name The in-game command name
     * @param executor The command executor to bind
     * @return True if everything worked correctly, else false
     */
    protected boolean injectCommand(String name, CommandExecutor executor) {
        final SimpleCommandMap map = injectCommandMap();
        if (map == null)
            return false;
        map.register(name, new Command(name) {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] strings) {
                return executor.onCommand(commandSender, this, s, strings);
            }
        });
        return true;
    }

    /**
     * Register manually a new event, even if {@link PluginCore#getPluginListeners()} should be used for static-based listener.
     * @param listener the listener to register
     */
    protected void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        stop();
        INSTANCE = null;
    }

    /**
     * Get the HOGPlugin instance of the desired plugin.
     * You should override a real 'getInstance' method and cast the return by your java plugin class
     */
    public static PluginCore<?> instance() {
        return INSTANCE;
    }

    /**
     * Everything related to the loggers
     */

    public static String colored(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static void send(String message, String first, String second, String third) {
        instance().getServer().getConsoleSender().sendMessage(colored(first + "[" + second + NAME + first + "] " + third + message));
    }

    public static void log(String message) {
        send(message, "&9", "&3", "&b");
    }
    public static void info(String message) {
        send(message, "&9", "&3", "&b");
    }
    public static void warn(String message) {
        send(message, "&e", "&6", "&e");
    }
    public static void error(String message) {
        send(message, "&c", "&4", "&c");
    }
    public static void success(String message) {
        send(message, "&a", "&2", "&a");
    }

    /**
     * Pass a boolean to enable or disable the debug mode.
     * <br> If enabled, you can use {@link PluginCore#debug(String)} in order to debug messages withint your plugin.
     */
    protected void setDebugEnabled(boolean enabled) {
        DEBUG_ENABLED = enabled;
    }

    /**
     * Enable the debug mode.
     * <br> If enabled, you can use {@link PluginCore#debug(String)} in order to debug messages withint your plugin.
     */
    protected void enableDebug() {
        setDebugEnabled(true);
    }

    /**
     * Disable the debug mode.
     * <br> If enabled, you can use {@link PluginCore#debug(String)} in order to debug messages withint your plugin.
     */
    protected void disableDebug() {
        setDebugEnabled(false);
    }

    /**
     * Debug a specific message while the debug mode is enabled.
     * @see PluginCore#setDebugEnabled(boolean)
     * @see PluginCore#enableDebug()
     * @see PluginCore#disableDebug()
     * @param message the message to send, if the debug mode is enabled.
     */
    public static void debug(String message) {
        if (instance().DEBUG_ENABLED)
            send(message, "&8", "&7", "&f");
    }

    private static List<Recipe> RECIPES = null;

    /**
     * Refresh the registered recipes cache that PluginCore will fill at first use of {@link PluginCore#getRegisteredRecipes()}
     */
    public static void refreshRegisteredRecipes() {
        RECIPES = new ArrayList<>();
        final Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while (iterator.hasNext())
            RECIPES.add(iterator.next());
    }

    /**
     * Get a list of every registered Bukkit's recipes.
     * <br> Cache them in the loading to avoid a re-load of recipes every times.
     * <br> Even if PluginCore will load it at first time, use {@link PluginCore#refreshRegisteredRecipes()} to force a recipes refresh.
     * @return The list of every recipes on Bukkit
     */
    public static List<Recipe> getRegisteredRecipes() {
        if (RECIPES == null) refreshRegisteredRecipes();
        return RECIPES;
    }

    /**
     * The same as {@link PluginCore#getRegisteredRecipes()} but you can pass a predicate in order to have only some sort of recipes (like cooking, or by key name)
     * @param verificator The desired verificator
     * @return The (possibly empty) list of every Bukkit's recipe that match the verificator.
     */
    public static List<Recipe> getRegisteredRecipes(Predicate<Recipe> verificator) {
        if (RECIPES == null) refreshRegisteredRecipes();
        return RECIPES.stream().filter(verificator).collect(Collectors.toList());
    }
}
