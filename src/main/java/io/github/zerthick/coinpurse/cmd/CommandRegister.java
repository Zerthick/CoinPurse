/*
 * Copyright (C) 2017  Zerthick
 *
 * This file is part of CoinPurse.
 *
 * CoinPurse is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * CoinPurse is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CoinPurse.  If not, see <http://www.gnu.org/licenses/>.
 */

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
