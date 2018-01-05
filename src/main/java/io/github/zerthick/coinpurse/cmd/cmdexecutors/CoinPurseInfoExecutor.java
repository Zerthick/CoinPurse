/*
 * Copyright (C) 2018  Zerthick
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

package io.github.zerthick.coinpurse.cmd.cmdexecutors;

import io.github.zerthick.coinpurse.CoinPurse;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CoinPurseInfoExecutor extends AbstractCommandExecutor{

    public CoinPurseInfoExecutor(CoinPurse plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        src.sendMessage(Text.of(TextColors.GOLD, container.getName(),
                TextColors.YELLOW, " version: ", TextColors.GOLD,
                container.getVersion().orElse(""), TextColors.YELLOW, " by ",
                TextColors.GOLD, "Zerthick"));

        return CommandResult.success();
    }
}
