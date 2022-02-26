package com.rpmtw.rpmtw_platform_mod.gui

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Config(name = "rpmtw_platform_mod")
@Environment(EnvType.CLIENT)
class ConfigScreen : ConfigData {
    @JvmField
    @ConfigEntry.Category("translate")
    @ConfigEntry.Gui.TransitiveObject
    var translate = Translate()

    @JvmField
    @ConfigEntry.Category("cosmicChat")
    @ConfigEntry.Gui.TransitiveObject
    var cosmicChat = CosmicChat()

    class Translate {
        @JvmField
        @ConfigEntry.Gui.Tooltip(count = 1)
        @ConfigEntry.Gui.RequiresRestart
        var autoToggleLanguage = true
    }

    class CosmicChat {
        @JvmField
        @ConfigEntry.Gui.Tooltip(count = 1)
        @ConfigEntry.Gui.RequiresRestart
        var enable = true

        @JvmField
        var enableReceiveMessage = true

        @JvmField
        @ConfigEntry.Gui.Tooltip(count = 1)
        var enableButton = true

        @JvmField
        @ConfigEntry.Gui.Excluded
        var eula = false

        @JvmField
        @ConfigEntry.Gui.Tooltip(count = 1)
        var nickname: String? = null
    }
}