package org.metamechanists.aircraft.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Private;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.aircraft.items.groups.Aircraft;


@CommandAlias("aircraft")
@SuppressWarnings("unused")
public class AircraftCommand extends BaseCommand {
    @HelpCommand
    @Syntax("")
    @Private
    public static void helpCommand(final CommandSender sender, @NotNull final CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("reload")
    @Syntax("Reloads the plugin")
    @CommandPermission("aircraft.command.reload")
    public static void charge(final Player player, final String @NotNull [] args) {
        Aircraft.reload();
    }
}