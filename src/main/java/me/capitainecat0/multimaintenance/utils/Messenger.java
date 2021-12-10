package me.capitainecat0.multimaintenance.utils;

import de.leonhard.storage.Yaml;
import me.capitainecat0.multimaintenance.MultiMaintenance;

import java.io.File;
import java.util.Locale;

/**
 * Enum that will handle messages and language files.
 */
public enum Messenger {

    ALLOWED_ALREADY_ADDED(MultiMaintenance.colored("&e%p &cest déja dans la whitelist!")),
    ALLOWED_LIST(MultiMaintenance.colored("&7Joueurs Whitelistés:")),
    ALLOWED_NOT_IN_LIST(MultiMaintenance.colored("&e%p &cn'est pas dans la whitelist !")),
    ALLOWED_PLAYER_NOT_FOUND(MultiMaintenance.colored("&e%p &cn'est pas connecté !")),
    ALLOWED_SUCCESSFULLY_ADDED(MultiMaintenance.colored("&b%p &7à été &aajouté &7à la whitelist !")),
    ALLOWED_SUCCESSFULLY_REMOVED(MultiMaintenance.colored("&b%p &7à été &cretiré &7de la whitelist !")),

    COMMAND_ERROR_OCCURED(MultiMaintenance.colored("&cDésolé, une erreur est survenue lors de l'exécution de la commande '&e%cmd&c'.\n&cMerci de contacter &b"+ MultiMaintenance.getInstance().getDescription().getAuthors()+ "&c.")),

    MAINTENANCE_STATUS(MultiMaintenance.colored("&7La maintenance est %status&7.")),
    MAINTENANCE_ALREADY_DISABLED(MultiMaintenance.colored("&7Désolé, mais la maintenance est déjà &cinactive&7.")),
    MAINTENANCE_ALREADY_ENABLED(MultiMaintenance.colored("&7Désolé, mais la maintenance est déjà &aactive&7.")),
    MAINTENANCE_ALREADY_SCHEDULED(MultiMaintenance.colored("&cUne maintenance est déjà en attente d'activation !\n&cPour l'annuler, exécutez la commande &e/maintenance off&c !")),
    MAINTENANCE_DISABLED(MultiMaintenance.colored("&7La maintenance du &e" + MultiMaintenance.getInstance().getServer().getName() + " &7à été &cdésactivée&7!")),
    MAINTENANCE_ENABLED(MultiMaintenance.colored("&7La maintenance du &e" + MultiMaintenance.getInstance().getServer().getName() + " &7à été &aactivée&7!")),
    MAINTENANCE_ENABLED_BROADCAST(MultiMaintenance.colored("&5&lHall&d&lOf&5&lGames &8&l> &7La maintenance du &e" + MultiMaintenance.getInstance().getServer().getName() + " &7à été &aactivée&7!")),
    MAINTENANCE_ENDED(MultiMaintenance.colored("&5&lHall&d&lOf&5&lGames &8&l> &7La maintenance est désormais &cterminée &7!")),
    MAINTENANCE_KICK(MultiMaintenance.colored("&6&l★  &5&lHall&d&lOf&5&lGames  &6&l★\n&eLa maintenance du &c"
            + MultiMaintenance.getInstance().getServer().getName()
            + " &eà été &aactivée &epar un &cAdministrateur&e.\n&cNous sommes navrés pour la gène occasionnée.\n&7 \n&6&lhallofgames.fr")),

    MAINTENANCE_NO_ARGUMENTS(MultiMaintenance.colored("&cMerci de taper &e/maintenance <on|off|list|status> &cou &e<add|remove> <joueur>&c.")),//|schedule|duration
    MAINTENANCE_NO_PERMISSION(MultiMaintenance.colored("&cVous n'avez pas la permission de gèrer la maintenance!")),

    MAINTENANCE_SERVERLIST(MultiMaintenance.colored("      &e-&6&l{ &5&lHall&d&lOf&5&lGames &6&l}&e-      &cMaintenance active \n         &aSkyBlock&7, &6RPG&7, &cFaction &bet bien plus !")),

    MAINTENANCE_SCHEDULED_BROADCAST_MESSAGE(MultiMaintenance.colored("&5&lHall&d&lOf&5&lGames &8&l> &e&lLe serveur entre en maintenance dans&c&l %s &e&l!")),
    MAINTENANCE_SCHEDULED_CANCELLED(MultiMaintenance.colored("&7Maintenance &cannulée &7avec &asuccès &7!")),
    MAINTENANCE_SCHEDULED_CANCELLED_BROADCAST(MultiMaintenance.colored("&7&lLa maintenance du serveur a été &c&lannulée&7&l!")),
    MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE(MultiMaintenance.colored("&5&lHall&d&lOf&5&lGames &8&l> &e&lLe serveur sort du mode maintenance dans&c&l %s &e&l!")),
    MAINTENANCE_SUCCESSFULLY_ENABLED_DURATION(MultiMaintenance.colored("&7La maintenance a été &aactivée&7 avec succès !\n&aDurée de la maintenance :&e %s &asecondes.")),
    MAINTENANCE_SUCCESSFULLY_SCHEDULED(MultiMaintenance.colored("&7La maintenance a été planifiée avec &asuccès&7 !\n&aActivation dans&e %s &asecondes !")),

    ;

    private final String defaultValue;
    private final String key;

    /**
     * New instance of a {@link Messenger}.
     * <br> Use {@link Messenger#getMessage()} to get the formatted message.
     * @param defaultValue The default value if not present in the <code>lang.yml</code> file.
     */
    Messenger(String defaultValue) {
        this.defaultValue = defaultValue;
        this.key = name().replaceAll("_", ".").toLowerCase(Locale.ROOT);
    }

    private static Yaml getLangFile() {
        return new Yaml(new File(MultiMaintenance.getInstance().getDataFolder(), "lang.yml"));
    }

    public String getMessage() {
        return getLangFile().getOrSetDefault(key, defaultValue);
    }

    String getDefaultValue() {
        return defaultValue;
    }

    String getKey() {
        return key;
    }
}
