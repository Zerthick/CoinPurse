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

package io.github.zerthick.coinpurse.cmd.cmdexecutors;

import io.github.zerthick.coinpurse.CoinPurse;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class CoinPurseAdminCreateExecutor extends AbstractCommandExecutor{

    public CoinPurseAdminCreateExecutor(CoinPurse plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Optional<Double> amountOptional = args.getOne(CommandArgs.AMOUNT);
        Optional<Player> targetPlayerOptional = args.getOne(CommandArgs.PLAYER);

        if (targetPlayerOptional.isPresent()) {

            Player targetPlayer = targetPlayerOptional.get();

            amountOptional.ifPresent(amount -> {
                EconomyService economyService = plugin.getEconomyService();

                // Build coin purse item
                ItemStack coinPurseItem = plugin.buildCoinPurseItem(amount);

                // Transfer the item to the player's inventory
                InventoryTransactionResult inventoryTransactionResult = targetPlayer.getInventory().offer(coinPurseItem);

                // If the player doesn't have space tell them
                if (!inventoryTransactionResult.getRejectedItems().isEmpty()) {
                    src.sendMessage(Text.of(TextColors.RED, "The target player doesn't have enough space in their inventory!"));
                }

            });
        } else {

            if (src instanceof Player) {
                Player player = (Player) src;
                amountOptional.ifPresent(amount -> {
                    EconomyService economyService = plugin.getEconomyService();

                    // Build coin purse item
                    ItemStack coinPurseItem = plugin.buildCoinPurseItem(amount);

                    // Transfer the item to the player's inventory
                    InventoryTransactionResult inventoryTransactionResult = player.getInventory().offer(coinPurseItem);

                    // If the player doesn't have space tell them
                    if (!inventoryTransactionResult.getRejectedItems().isEmpty()) {
                        player.sendMessage(Text.of(TextColors.RED, "You don't have enough space in your inventory!"));
                    }

                });
            } else {
                src.sendMessage(Text.of("You must specify a target player from the console!"));
            }
        }

        return CommandResult.success();
    }
}
