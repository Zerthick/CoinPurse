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
