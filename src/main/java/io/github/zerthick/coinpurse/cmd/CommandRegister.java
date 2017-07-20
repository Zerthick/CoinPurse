package io.github.zerthick.coinpurse.cmd;

import io.github.zerthick.coinpurse.CoinPurse;
import io.github.zerthick.coinpurse.cmd.cmdexecutors.CoinPurseAdminCreateExecutor;
import io.github.zerthick.coinpurse.cmd.cmdexecutors.CoinPurseCreateExecutor;
import io.github.zerthick.coinpurse.cmd.cmdexecutors.CoinPurseInfoExecutor;
import io.github.zerthick.coinpurse.cmd.cmdexecutors.CommandArgs;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;

public class CommandRegister {

    public static void registerCommands(CoinPurse plugin) {

        CommandSpec info = CommandSpec.builder()
                .permission("coinpurse.command.info")
                .executor(new CoinPurseInfoExecutor(plugin))
                .build();

        CommandSpec admin = CommandSpec.builder()
                .permission("coinpurse.command.admin.create")
                .executor(new CoinPurseAdminCreateExecutor(plugin))
                .arguments(GenericArguments.doubleNum(CommandArgs.AMOUNT), GenericArguments.optional(GenericArguments.player(CommandArgs.PLAYER)))
                .build();

        CommandSpec coinPurse = CommandSpec.builder()
                .permission("coinpurse.command.create")
                .executor(new CoinPurseCreateExecutor(plugin))
                .arguments(GenericArguments.doubleNum(CommandArgs.AMOUNT))
                .child(info, "info")
                .child(admin, "admin")
                .build();

        Sponge.getCommandManager().register(plugin, coinPurse, "coinpurse", "cp");
    }
}
