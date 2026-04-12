package it.proud.api;

import it.proud.api.managers.ICharManager;
import it.proud.api.managers.IClanKillsManager;
import it.proud.api.managers.IClanManager;
import it.proud.api.managers.IEconomyManager;
import it.proud.api.managers.IGodManager;
import it.proud.api.managers.IHomeManager;
import it.proud.api.managers.INotificationService;
import it.proud.api.managers.IPlayerManager;
import it.proud.api.managers.IPlayerStatsManager;
import it.proud.api.managers.ISchematicsManager;
import it.proud.api.managers.IScoreboardManager;
import it.proud.api.managers.IScoreboardRegistry;
import it.proud.api.managers.ISnapshotManager;
import it.proud.api.managers.ISpawnManager;
import it.proud.api.managers.ITpaManager;
import it.proud.api.managers.IVanishManager;
import it.proud.api.managers.IWarpManager;
import it.proud.api.module.IModuleRegistry;
import it.proud.api.module.IProudModule;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Central access point for ProudCore services.
 */
@RequiredArgsConstructor
public final class ProudCoreAPI {

    private static final Logger log = LogManager.getLogger("ProudCore/API");

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String GRAY = "\u001B[90m";
    private static final String PREFIX = BOLD + CYAN + "[API]" + RESET + " ";

    private static volatile ProudCoreAPI instance;

    private final IClanManager clanManager;
    private final ICharManager charManager;
    private final IPlayerManager playerManager;
    private final IScoreboardManager scoreboardManager;
    private final IScoreboardRegistry scoreboardRegistry;
    private final ISchematicsManager schematicsManager;
    private final IClanKillsManager clanKillsManager;
    private final IPlayerStatsManager playerStatsManager;
    private final IEconomyManager economyManager;
    private final IModuleRegistry moduleRegistry;
    private final IHomeManager homeManager;
    private final IWarpManager warpManager;
    private final ISpawnManager spawnManager;
    private final ITpaManager tpaManager;
    private final IVanishManager vanishManager;
    private final IGodManager godManager;
    private final INotificationService notificationService;
    private final ISnapshotManager snapshotManager;

    public static void register(ProudCoreAPI api) {
        instance = api;
        log.info("{}{}{}{}", PREFIX, GREEN, "API registered successfully.", RESET);
        log.info("{}ClanManager        -> {}", PREFIX, simpleName(api.clanManager));
        log.info("{}CharManager        -> {}", PREFIX, simpleName(api.charManager));
        log.info("{}PlayerManager      -> {}", PREFIX, simpleName(api.playerManager));
        log.info("{}PlayerStatsManager -> {}", PREFIX, simpleName(api.playerStatsManager));
        log.info("{}EconomyManager     -> {}", PREFIX, simpleName(api.economyManager));
        log.info("{}ScoreboardManager  -> {}", PREFIX, simpleName(api.scoreboardManager));
        log.info("{}ScoreboardRegistry -> {}", PREFIX, simpleName(api.scoreboardRegistry));
        log.info("{}SchematicsManager  -> {}", PREFIX, simpleName(api.schematicsManager));
        log.info("{}ClanKillsManager   -> {}", PREFIX, simpleName(api.clanKillsManager));
        log.info("{}ModuleRegistry     -> {}", PREFIX, simpleName(api.moduleRegistry));
        log.info("{}HomeManager        -> {}", PREFIX, simpleName(api.homeManager));
        log.info("{}WarpManager        -> {}", PREFIX, simpleName(api.warpManager));
        log.info("{}SpawnManager       -> {}", PREFIX, simpleName(api.spawnManager));
        log.info("{}TpaManager         -> {}", PREFIX, simpleName(api.tpaManager));
        log.info("{}VanishManager      -> {}", PREFIX, simpleName(api.vanishManager));
        log.info("{}GodManager         -> {}", PREFIX, simpleName(api.godManager));
        log.info("{}NotificationService-> {}", PREFIX, simpleName(api.notificationService));
        log.info("{}SnapshotManager    -> {}", PREFIX, simpleName(api.snapshotManager));
        log.info("{}{}{}{}", PREFIX, GREEN, "Ready - external plugins can now call ProudCoreAPI.get()", RESET);
    }

    public static void unregister() {
        if (instance != null) {
            log.info("{}{}API unregistered - ProudCore is shutting down.{}", PREFIX, GRAY, RESET);
            instance = null;
        }
    }

    public static ProudCoreAPI get() {
        if (instance == null) {
            log.warn("{}{}API access requested before registration. Check load order or plugin version.{}",
                    PREFIX, YELLOW, RESET);
            log.error("{}{}get() called but API is not registered. Is ProudCore loaded?{}",
                    PREFIX, RED, RESET);
            throw new IllegalStateException("ProudCoreAPI not available - is ProudCore loaded?");
        }
        return instance;
    }

    public static ProudCoreAPI getOrNull() {
        return instance;
    }

    public IClanManager getClanManager() {
        return requireService("ClanManager", clanManager);
    }

    public ICharManager getCharManager() {
        return requireService("CharManager", charManager);
    }

    public IPlayerManager getPlayerManager() {
        return requireService("PlayerManager", playerManager);
    }

    public IScoreboardManager getScoreboardManager() {
        return requireService("ScoreboardManager", scoreboardManager);
    }

    public IScoreboardRegistry getScoreboardRegistry() {
        return requireService("ScoreboardRegistry", scoreboardRegistry);
    }

    public ISchematicsManager getSchematicsManager() {
        return requireService("SchematicsManager", schematicsManager);
    }

    public IClanKillsManager getClanKillsManager() {
        return requireService("ClanKillsManager", clanKillsManager);
    }

    public IPlayerStatsManager getPlayerStatsManager() {
        return requireService("PlayerStatsManager", playerStatsManager);
    }

    public IEconomyManager getEconomyManager() {
        return requireService("EconomyManager", economyManager);
    }

    public IModuleRegistry getModuleRegistry() {
        return requireService("ModuleRegistry", moduleRegistry);
    }

    public IHomeManager getHomeManager() {
        return requireService("HomeManager", homeManager);
    }

    public IWarpManager getWarpManager() {
        return requireService("WarpManager", warpManager);
    }

    public ISpawnManager getSpawnManager() {
        return requireService("SpawnManager", spawnManager);
    }

    public ITpaManager getTpaManager() {
        return requireService("TpaManager", tpaManager);
    }

    public IVanishManager getVanishManager() {
        return requireService("VanishManager", vanishManager);
    }

    public IGodManager getGodManager() {
        return requireService("GodManager", godManager);
    }

    public INotificationService getNotificationService() {
        return requireService("NotificationService", notificationService);
    }

    public ISnapshotManager getSnapshotManager() {
        return requireService("SnapshotManager", snapshotManager);
    }

    public <T extends IProudModule> Optional<T> getModule(String moduleId, Class<T> type) {
        return getModuleRegistry().getModule(moduleId, type);
    }

    private <T> T requireService(String serviceName, T service) {
        if (service != null) {
            return service;
        }

        log.warn("{}{}API service '{}' is unavailable. Possible cause: disabled module or updated API contract.{}",
                PREFIX, YELLOW, serviceName, RESET);
        log.error("{}{}API access failed for service '{}'.{}", PREFIX, RED, serviceName, RESET);

        throw new IllegalStateException(
                "ProudCoreAPI service '" + serviceName + "' not available (disabled module or updated API).");
    }

    private static String simpleName(Object service) {
        return service != null ? service.getClass().getSimpleName() : "disabled";
    }
}
