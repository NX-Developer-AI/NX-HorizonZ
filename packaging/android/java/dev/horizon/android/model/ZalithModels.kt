package dev.horizon.android.model

import dev.horizon.android.R
import dev.horizon.android.i18n.LauncherText

enum class ZalithNavTab(val title: String) {
    HOME(LauncherText.get(R.string.dashboard)),
    VERSIONS(LauncherText.get(R.string.game_assets)),
    STORE(LauncherText.get(R.string.store)),
    FILES(LauncherText.get(R.string.files_mods)),
    ACCOUNTS(LauncherText.get(R.string.account_skins)),
    LOGS(LauncherText.get(R.string.system_logs)),
    SETTINGS(LauncherText.get(R.string.settings))
}

enum class StoreCategory(
    val label: String,
    val headerTitle: String,
    val kindFilter: String,
    val targetFolder: String,
    val badgeLabel: String
) {
    ALL(LauncherText.get(R.string.all), LauncherText.get(R.string.horizon_mods_all), "all", "horizon-mods", "ALL"),
    MOD(LauncherText.get(R.string.mods), LauncherText.get(R.string.horizon_mods_mods), "mod", "horizon-mods", "MOD"),
    NATIVE_MOD(LauncherText.get(R.string.native_mods), LauncherText.get(R.string.horizon_mods_native_mods), "native-mod", "horizon-native-mods", "NATIVE MOD"),
    MODPACK(LauncherText.get(R.string.modpacks), LauncherText.get(R.string.horizon_mods_modpacks), "modpack", "horizon-packs", "MODPACK"),
    RESOURCE_PACK(LauncherText.get(R.string.resource_packs), LauncherText.get(R.string.horizon_mods_resource_packs), "resourcepack", "resourcepacks", "RESOURCE PACK"),
    SHADER_PACK(LauncherText.get(R.string.shader_packs), LauncherText.get(R.string.horizon_mods_shader_packs), "shader", "shaderpacks", "SHADER PACK")
}

enum class StoreSortOption(val label: String) {
    MOST_DOWNLOADED(LauncherText.get(R.string.most_downloaded)),
    BEST_RATED(LauncherText.get(R.string.best_rated)),
    RECENTLY_UPDATED(LauncherText.get(R.string.recently_updated)),
    NAME_AZ(LauncherText.get(R.string.name_a_z))
}

enum class StoreStatusFilter(val label: String) {
    ALL(LauncherText.get(R.string.all_projects)),
    INSTALLED(LauncherText.get(R.string.installed_only)),
    UPDATES(LauncherText.get(R.string.updates_available)),
    OFFICIAL_SIGNED(LauncherText.get(R.string.official_signed))
}

enum class StoreInstallState {
    NOT_INSTALLED,
    INSTALLED,
    UPDATE_AVAILABLE
}

data class StoreVersionRelease(
    val id: String,
    val name: String,
    val versionNumber: String,
    val mcVersions: List<String>,
    val kindLabel: String = "MOD",
    val releaseType: String = "release",
    val downloadsCount: Long = 0L,
    val datePublished: String = "Recently",
    val fileName: String = "",
    val downloadUrl: String = "",
    val sizeBytes: Long = 0L,
    val sha256: String = "",
    val signed: Boolean = true
) {
    val primaryMcVersion: String
        get() = mcVersions.firstOrNull() ?: "26.3"

    val formattedDownloads: String
        get() = when {
            downloadsCount >= 1_000_000L -> String.format(java.util.Locale.US, LauncherText.get(R.string.text_1fm), downloadsCount / 1_000_000.0)
            downloadsCount >= 1_000L -> String.format(java.util.Locale.US, LauncherText.get(R.string.text_1fk), downloadsCount / 1_000.0)
            downloadsCount > 0L -> downloadsCount.toString()
            else -> "1.2k"
        }
}

data class StoreCatalogItem(
    val id: String,
    val slug: String = "",
    val title: String,
    val summary: String,
    val author: String,
    val version: String,
    val supportedMcVersions: List<String>,
    val downloadUrl: String,
    val fileName: String,
    val sizeBytes: Long,
    val sha256: String = "",
    val category: StoreCategory,
    val kindBadge: String = "MOD",
    val pageUrl: String = "",
    val sourceCodeUrl: String = "",
    val iconUrl: String = "",
    val downloadsCount: Long = 1200L,
    val ratingScore: Float = 4.8f,
    val ratingCount: Int = 12,
    val isSignedOrOfficial: Boolean = true,
    val side: String = "client",
    val license: String = "MIT",
    val displayTags: List<String> = emptyList(),
    val requiredModIds: List<String> = emptyList(),
    val allVersions: List<StoreVersionRelease> = emptyList()
) {
    val isCompatibleWith26_3: Boolean
        get() = supportedMcVersions.any {
            it.contains("26.3") || it.equals("all", ignoreCase = true)
        }

    val versionBadge: String
        get() = if (supportedMcVersions.isEmpty()) "26.3" else supportedMcVersions.first()

    val formattedDownloads: String
        get() = when {
            downloadsCount >= 1_000_000L -> String.format(java.util.Locale.US, LauncherText.get(R.string.text_1fm_downloads), downloadsCount / 1_000_000.0)
            downloadsCount >= 1_000L -> String.format(java.util.Locale.US, LauncherText.get(R.string.text_1fk_downloads), downloadsCount / 1_000.0)
            else -> LauncherText.get(R.string.downloads, downloadsCount)
        }

    val targetInstallFolder: String
        get() = when (category) {
            StoreCategory.RESOURCE_PACK -> "resourcepacks"
            StoreCategory.SHADER_PACK -> "shaderpacks"
            StoreCategory.MODPACK -> "horizon-packs"
            else -> "horizon-mods"
        }
}

data class StoreSearchPageResult(
    val items: List<StoreCatalogItem>,
    val currentPage: Int,
    val totalPages: Int,
    val totalHits: Int
)

data class WorldSaveDetailInfo(
    val folderName: String,
    val displayName: String,
    val gameMode: String,
    val difficulty: String,
    val playTimeFormatted: String,
    val worldSeed: String,
    val lastPlayedFormatted: String
)

data class ResourcePackDetailInfo(
    val title: String,
    val description: String,
    val packType: String,
    val fileName: String,
    val fileSizeFormatted: String,
    val formatVersion: Int
)

data class GithubReleaseUpdateInfo(
    val latestVersion: String,
    val releaseTitle: String,
    val downloadUrl: String,
    val htmlUrl: String
)

enum class AccountType(val badge: String) {
    OFFLINE(LauncherText.get(R.string.offline)),
    MICROSOFT(LauncherText.get(R.string.microsoft)),
    ELY_BY(LauncherText.get(R.string.ely_by))
}

/**
 * Matches the four touch schemes in `Vasysik/horizon-android` (`docs/android.md`):
 * Joystick/D-pad movement combined with Crosshair or Tap-to-interact aiming.
 */
enum class HorizonTouchScheme(val serdeKey: String, val label: String) {
    JOYSTICK_CROSSHAIR("joystick-crosshair", LauncherText.get(R.string.joystick_crosshair_aiming)),
    JOYSTICK_TAP("joystick-tap", LauncherText.get(R.string.joystick_tap_to_interact)),
    DPAD_CROSSHAIR("dpad-crosshair", LauncherText.get(R.string.d_pad_crosshair_aiming)),
    DPAD_TAP("dpad-tap", LauncherText.get(R.string.d_pad_tap_to_interact))
}

enum class HorizonRendererMode(val serdeKey: String, val label: String) {
    VULKAN_COMPAT("vulkan-compatibility", LauncherText.get(R.string.vulkan_compatibility_default)),
    VULKAN("vulkan", LauncherText.get(R.string.vulkan_high_performance)),
    OPENGL("open-gl", LauncherText.get(R.string.opengl_es_fallback))
}

enum class HorizonInputHost(val serdeKey: String, val label: String) {
    AUTO("auto", LauncherText.get(R.string.auto_detect)),
    TOUCH("touch", LauncherText.get(R.string.touch_screen)),
    KEYBOARD_MOUSE("keyboard-mouse", LauncherText.get(R.string.keyboard_mouse)),
    CONTROLLER("controller", LauncherText.get(R.string.gamepad_controller))
}

enum class HorizonOrientation(val serdeKey: String, val label: String) {
    LANDSCAPE("landscape", LauncherText.get(R.string.landscape)),
    REVERSE_LANDSCAPE("reverse-landscape", LauncherText.get(R.string.reverse_landscape_flipped)),
    PORTRAIT("portrait", LauncherText.get(R.string.portrait)),
    REVERSE_PORTRAIT("reverse-portrait", LauncherText.get(R.string.reverse_portrait)),
    AUTO("auto", LauncherText.get(R.string.auto_sensor))
}

enum class HorizonContentOrigin(val serdeKey: String, val label: String) {
    HORIZON("horizon", LauncherText.get(R.string.horizon_playhorizon_dev)),
    MODRINTH("modrinth", LauncherText.get(R.string.modrinth))
}

enum class HorizonScreenAspect(val serdeKey: String, val label: String) {
    NATIVE("native", LauncherText.get(R.string.native_fullscreen_device_ratio)),
    WIDE("wide", LauncherText.get(R.string.wide_16_9_presentation)),
    CLASSIC("classic", LauncherText.get(R.string.classic_4_3_presentation)),
    SQUARE("square", LauncherText.get(R.string.square_1_1_presentation))
}

enum class HorizonControllerNavMode(val serdeKey: String, val label: String) {
    FOCUS("focus", LauncherText.get(R.string.focus_highlight_console_d_pad_stick)),
    POINTER("pointer", LauncherText.get(R.string.virtual_pointer_cursor_stick_cursor))
}

data class ModPackageDetailInfo(
    val modId: String,
    val displayName: String,
    val version: String,
    val description: String,
    val author: String,
    val apiLine: String,
    val side: String,
    val capabilities: List<String>,
    val hasWasmBinary: Boolean,
    val fileName: String,
    val fileSizeFormatted: String
)

enum class HorizonBackgroundMode(val serdeKey: String, val label: String) {
    WALLPAPER("wallpaper", LauncherText.get(R.string.static_hd_minecraft_wallpaper_16_9)),
    YOUTUBE_TRAILER("youtube-trailer", LauncherText.get(R.string.auto_play_minecraft_youtube_trailer))
}

data class MinecraftTrailerVideo(
    val videoId: String,
    val title: String,
    val publishedDate: String,
    val watchUrl: String = "https://www.youtube.com/watch?v=$videoId",
    val directMp4Url: String = ""
)

data class SavedCustomSkinItem(
    val id: String,
    val fileName: String,
    val displayName: String,
    val filePath: String,
    val slimModel: Boolean,
    val sourceLabel: String = "Custom Skin",
    val addedAt: Long = System.currentTimeMillis()
)

/**
 * Launcher Theme Palettes (Default: `MATERIAL_YOU` "Monet / Material You (Default)").
 */
enum class ZalithThemePalette(val label: String, val primaryHex: Long, val secondaryHex: Long, val surfaceHex: Long) {
    MATERIAL_YOU(LauncherText.get(R.string.monet_material_you_default), 0xFF64B5F6, 0xFF81C784, 0xFF10131A),
    EMBERMIRE(LauncherText.get(R.string.embermire_dark), 0xFFFF7043, 0xFFFFB74D, 0xFF12151E),
    HORIZON_RUST(LauncherText.get(R.string.horizon_rust), 0xFFFF5722, 0xFF26A69A, 0xFF0E1218),
    AMOLED_EMERALD(LauncherText.get(R.string.emerald_amoled), 0xFF66BB6A, 0xFFA5D6A7, 0xFF090C10)
}

data class ZalithAccount(
    val id: String,
    val username: String,
    val uuid: String,
    val accountType: AccountType,
    val isActive: Boolean = false,
    val skinPath: String = "",
    val slimSkinModel: Boolean = false,
    val skinSourceLabel: String = "Default (Classic Steve)",
    val capePath: String = "",
    val hideCape: Boolean = false
) {
    val providerQualifiedKey: String
        get() = "${accountType.name.lowercase(java.util.Locale.US)}:${
            if (accountType == AccountType.OFFLINE) username.lowercase(java.util.Locale.US)
            else uuid.lowercase(java.util.Locale.US)
        }"
}

data class SkinDetectionResult(
    val success: Boolean,
    val skinFile: java.io.File?,
    val slimModel: Boolean,
    val sourceLabel: String,
    val message: String,
    val savedFileName: String = ""
)

enum class HorizonAiAgentProvider(val serdeKey: String, val label: String) {
    GEMINI_API("gemini", LauncherText.get(R.string.google_ai_studio_gemini_api)),
    LOCAL_SOCKET("local", LauncherText.get(R.string.local_ai_control_socket_horizon_control_json))
}

data class GeminiModelOption(
    val modelId: String,
    val displayName: String,
    val badge: String,
    val description: String
)

/**
 * Clean, canonical list of Google AI Studio / Gemini models with exact 1-to-1 alignment
 * between `displayName` and `modelId` (no duplicate bracket strings or mismatched IDs).
 */
val DEFAULT_GEMINI_MODELS = listOf(
    // === Gemini 4 Series ===
    GeminiModelOption(
        modelId = "gemini-4-argon",
        displayName = "Gemini 4 Argon",
        badge = "GEMINI 4 • ARGON",
        description = LauncherText.get(R.string.flagship_autonomous_minecraft_26_3_agent_with)
    ),
    GeminiModelOption(
        modelId = "gemini-4.0-ultra",
        displayName = "Gemini 4.0 Ultra",
        badge = "GEMINI 4 • ULTRA",
        description = LauncherText.get(R.string.deepest_reasoning_for_large_builds_redstone_automation)
    ),
    GeminiModelOption(
        modelId = "gemini-4.0-pro",
        displayName = "Gemini 4.0 Pro",
        badge = "GEMINI 4 • PRO",
        description = LauncherText.get(R.string.high_precision_multi_step_planning_inventory_management)
    ),
    GeminiModelOption(
        modelId = "gemini-4.0-flash",
        displayName = "Gemini 4.0 Flash",
        badge = "GEMINI 4 • FLASH",
        description = LauncherText.get(R.string.low_latency_4_0_agent_for_rapid)
    ),
    GeminiModelOption(
        modelId = "gemini-4.0-flash-lite",
        displayName = "Gemini 4.0 Flash-Lite",
        badge = "GEMINI 4 • LITE",
        description = LauncherText.get(R.string.ultra_lightweight_4_0_model_with_instant)
    ),

    // === Gemini 3.8 & 3.5 Series ===
    GeminiModelOption(
        modelId = "gemini-3.8-pro",
        displayName = "Gemini 3.8 Pro",
        badge = "GEMINI 3.8 • PRO",
        description = LauncherText.get(R.string.tactical_survival_world_navigation_and_command_sequence)
    ),
    GeminiModelOption(
        modelId = "gemini-3.8-flash",
        displayName = "Gemini 3.8 Flash",
        badge = "GEMINI 3.8 • FLASH",
        description = LauncherText.get(R.string.fast_multimodal_3_8_agent_for_instant)
    ),
    GeminiModelOption(
        modelId = "gemini-3.8-flash-lite",
        displayName = "Gemini 3.8 Flash-Lite",
        badge = "GEMINI 3.8 • LITE",
        description = LauncherText.get(R.string.lightweight_3_8_model_for_instant_in)
    ),
    GeminiModelOption(
        modelId = "gemini-3.5-pro",
        displayName = "Gemini 3.5 Pro",
        badge = "GEMINI 3.5 • PRO",
        description = LauncherText.get(R.string.advanced_command_block_structure_and_world_editing)
    ),
    GeminiModelOption(
        modelId = "gemini-3.5-flash",
        displayName = "Gemini 3.5 Flash",
        badge = "GEMINI 3.5 • FLASH",
        description = LauncherText.get(R.string.balanced_speed_and_accuracy_for_continuous_in)
    ),
    GeminiModelOption(
        modelId = "gemini-3.5-flash-lite",
        displayName = "Gemini 3.5 Flash-Lite",
        badge = "GEMINI 3.5 • LITE",
        description = LauncherText.get(R.string.ultra_fast_3_5_flash_lite_model)
    ),

    // === Gemini 3.1 & 3.0 Series ===
    GeminiModelOption(
        modelId = "gemini-3.1-pro-preview",
        displayName = "Gemini 3.1 Pro Preview",
        badge = "GEMINI 3.1 • PRO",
        description = LauncherText.get(R.string.latest_3_1_pro_preview_model_for)
    ),
    GeminiModelOption(
        modelId = "gemini-3.1-flash-lite-preview",
        displayName = "Gemini 3.1 Flash-Lite Preview",
        badge = "GEMINI 3.1 • LITE",
        description = LauncherText.get(R.string.high_efficiency_3_1_flash_lite_preview)
    ),
    GeminiModelOption(
        modelId = "gemini-3.0-pro",
        displayName = "Gemini 3.0 Pro",
        badge = "GEMINI 3.0 • PRO",
        description = LauncherText.get(R.string.stable_3_0_pro_reasoning_model_for)
    ),
    GeminiModelOption(
        modelId = "gemini-3.0-flash",
        displayName = "Gemini 3.0 Flash",
        badge = "GEMINI 3.0 • FLASH",
        description = LauncherText.get(R.string.responsive_3_0_flash_model_for_in)
    ),

    // === Gemini 2.5 & 2.0 Series (Google AI Studio Direct) ===
    GeminiModelOption(
        modelId = "gemini-2.5-pro",
        displayName = "Gemini 2.5 Pro",
        badge = "GEMINI 2.5 • PRO",
        description = LauncherText.get(R.string.google_ai_studio_2_5_pro_thinking)
    ),
    GeminiModelOption(
        modelId = "gemini-2.5-flash",
        displayName = "Gemini 2.5 Flash",
        badge = "GEMINI 2.5 • FLASH",
        description = LauncherText.get(R.string.google_ai_studio_2_5_flash_high)
    ),
    GeminiModelOption(
        modelId = "gemini-2.5-flash-lite",
        displayName = "Gemini 2.5 Flash-Lite",
        badge = "GEMINI 2.5 • LITE",
        description = LauncherText.get(R.string.google_ai_studio_2_5_flash_lite)
    ),
    GeminiModelOption(
        modelId = "gemini-2.0-flash",
        displayName = "Gemini 2.0 Flash",
        badge = "GEMINI 2.0 • FLASH",
        description = LauncherText.get(R.string.google_ai_studio_2_0_flash_general)
    ),
    GeminiModelOption(
        modelId = "gemini-2.0-flash-lite",
        displayName = "Gemini 2.0 Flash-Lite",
        badge = "GEMINI 2.0 • LITE",
        description = LauncherText.get(R.string.google_ai_studio_2_0_flash_lite)
    ),

    // === Gemma 3 Series ===
    GeminiModelOption(
        modelId = "gemma-3-27b-it",
        displayName = "Gemma 3 27B Instruct",
        badge = "GEMMA 3 • 27B",
        description = LauncherText.get(R.string.open_weights_gemma_3_27b_instruct_served)
    )
)

data class HorizonConfigState(
    val profile: Int = 0,
    val renderer: HorizonRendererMode = HorizonRendererMode.VULKAN_COMPAT,
    val renderScalePercent: Int = 75,
    val orientation: HorizonOrientation = HorizonOrientation.LANDSCAPE,
    val screenAspect: HorizonScreenAspect = HorizonScreenAspect.NATIVE,
    val screenImmersive: Boolean = true,
    val screenAvoidCutout: Boolean = true,
    val displayRefreshHz: Int = 0,
    val performanceHintsAdpf: Boolean = true,
    val gpuStageProfiling: Boolean = false,
    val inputHost: HorizonInputHost = HorizonInputHost.AUTO,
    val touchScheme: HorizonTouchScheme = HorizonTouchScheme.JOYSTICK_CROSSHAIR,
    val touchSensitivityXPercent: Int = 100,
    val touchSensitivityYPercent: Int = 100,
    val touch360Joystick: Boolean = false,
    val touchSwapHandsButton: Boolean = false,
    val inventoryVirtualMouse: Boolean = true,
    val keypadControls: Boolean = false,
    val sprintToggle: Boolean = false,
    val inventoryTouchControls: Boolean = true,
    val gyroAimingEnabled: Boolean = false,
    val gyroSensitivityPercent: Int = 100,
    val controllerVibration: Boolean = true,
    val controllerNavMode: HorizonControllerNavMode = HorizonControllerNavMode.FOCUS,
    val controllerSensitivityPercent: Int = 100,
    val controllerDeadzonePercent: Int = 15,
    val extraFullbright: Boolean = false,
    val extraDynamicLights: Boolean = false,
    val extraAutoReconnect: Boolean = true,
    val extraMouseTweaks: Boolean = true,
    val extraInventorySort: Boolean = true,
    val recordPerformance: Boolean = false,
    val modSource: HorizonContentOrigin = HorizonContentOrigin.HORIZON,
    val shaderSource: HorizonContentOrigin = HorizonContentOrigin.HORIZON,
    val defaultServer: String = "",
    val proxy: String = "",
    val mcRenderDistance: Int = 8,
    val mcSimulationDistance: Int = 8,
    val mcMaxFps: Int = 120,
    val mcGuiScale: Int = 0,
    val mcFov: Int = 70,
    val mcVsync: Boolean = true,
    val mcFullscreen: Boolean = true,
    val themePalette: ZalithThemePalette = ZalithThemePalette.MATERIAL_YOU,
    val useDynamicColor: Boolean = true,
    val enableSystemLog: Boolean = true,
    val disableAudioSafeMode: Boolean = false,
    val discordRichPresence: Boolean = true,
    val publicOnDeviceAssetDownload: Boolean = true,
    val backgroundMode: HorizonBackgroundMode = HorizonBackgroundMode.WALLPAPER,
    val useYoutubeTrailerWallpaper: Boolean = false,
    val muteTrailerVideo: Boolean = false,
    val ramAllocationMb: Int = 0,
    val aiAgentEnabled: Boolean = false,
    val aiAgentProvider: HorizonAiAgentProvider = HorizonAiAgentProvider.GEMINI_API,
    val geminiModel: String = "gemini-4-argon",
    val aiAgentAutoAssistChat: Boolean = true,
    val aiAgentAutonomousActions: Boolean = true,
    val aiAgentTaskPrompt: String = LauncherText.get(R.string.help_the_player_do_anything_inside_minecraft)
)

val MINECRAFT_SPLASH_QUOTES = listOf(
    LauncherText.get(R.string.never_dig_straight_down),
    LauncherText.get(R.string.punching_trees_since_day_one),
    LauncherText.get(R.string.creeper_aww_man),
    LauncherText.get(R.string.text_100_native_rust_zero_jvm_lag),
    LauncherText.get(R.string.watch_out_for_wardens_in_the_deep),
    LauncherText.get(R.string.always_carry_a_water_bucket_in_the),
    LauncherText.get(R.string.diamonds_are_best_found_deep_in_deepslate),
    LauncherText.get(R.string.redstone_engineers_build_computers_inside_blocks),
    LauncherText.get(R.string.don_t_look_an_enderman_in_the),
    LauncherText.get(R.string.a_stack_of_64_torches_keeps_the),
    LauncherText.get(R.string.villager_trading_halls_1_emerald_for_mending),
    LauncherText.get(R.string.sleeping_in_the_nether_is_an_explosive),
    LauncherText.get(R.string.enchanting_table_speaks_standard_galactic_alphabet),
    LauncherText.get(R.string.elytra_firework_rockets_infinite_skies),
    LauncherText.get(R.string.beware_of_trapped_chests_in_desert_temples),
    LauncherText.get(R.string.craft_a_shield_before_your_first_skeleton),
    LauncherText.get(R.string.silk_touch_saves_the_bee_nest_with),
    LauncherText.get(R.string.fortune_iii_turns_one_diamond_ore_into),
    LauncherText.get(R.string.netherite_floats_in_lava_your_items_are),
    LauncherText.get(R.string.copper_golems_and_trial_chambers_await),
    LauncherText.get(R.string.build_a_portal_with_10_obsidian_blocks),
    LauncherText.get(R.string.golden_carrots_give_the_best_saturation_in),
    LauncherText.get(R.string.crouch_near_sculk_sensors_to_walk_silently),
    LauncherText.get(R.string.boats_on_blue_ice_travel_faster_than),
    LauncherText.get(R.string.feed_a_dolphin_raw_cod_to_find),
    LauncherText.get(R.string.name_a_sheep_jeb_for_a_rainbow),
    LauncherText.get(R.string.name_any_mob_dinnerbone_to_flip_it),
    LauncherText.get(R.string.cats_scare_creepers_away_from_your_front),
    LauncherText.get(R.string.snow_golems_leave_a_trail_of_snow),
    LauncherText.get(R.string.axolotls_grant_regeneration_when_you_help_them),
    LauncherText.get(R.string.totem_of_undying_saves_you_from_fatal),
    LauncherText.get(R.string.soul_speed_iii_makes_soul_sand_valleys),
    LauncherText.get(R.string.bartering_gold_ingots_with_piglins_for_ender),
    LauncherText.get(R.string.conduits_give_water_breathing_and_night_vision),
    LauncherText.get(R.string.every_world_seed_holds_60_million_blocks),
    LauncherText.get(R.string.minecraft_26_3_running_natively_in_rust)
)
