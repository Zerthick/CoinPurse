package io.github.zerthick.coinpurse.cmd.cmdexecutors;

import com.google.common.collect.ImmutableList;
import io.github.zerthick.coinpurse.CoinPurse;
import io.github.zerthick.coinpurse.data.CoinPurseKeys;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.math.BigDecimal;
import java.util.Optional;

public class CoinPurseCreateExecutor extends AbstractCommandExecutor{

    public CoinPurseCreateExecutor(CoinPurse plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Optional<Double> amountOptional = args.getOne(CommandArgs.AMOUNT);

        if(src instanceof Player) {
            Player player = (Player) src;
            amountOptional.ifPresent(amount -> {
                EconomyService economyService = plugin.getEconomyService();
                economyService.getOrCreateAccount(player.getUniqueId()).ifPresent(playerAccount -> {
                    TransactionResult transactionResult = playerAccount.withdraw(economyService.getDefaultCurrency(), BigDecimal.valueOf(amount), Cause.of(NamedCause.owner(container)));
                    switch (transactionResult.getResult()) {
                        case SUCCESS:

                            // Build coin purse item
                            ItemStack coinPurseItem = plugin.buildCoinPurseItem(amount);

                            // Transfer the item to the player's inventory
                            InventoryTransactionResult inventoryTransactionResult = player.getInventory().offer(coinPurseItem);

                            // If the player doesn't have space revert the transaction
                            if(!inventoryTransactionResult.getRejectedItems().isEmpty()) {
                                player.sendMessage(Text.of(TextColors.RED, "You don't have enough space in your inventory!"));
                                playerAccount.deposit(economyService.getDefaultCurrency(), BigDecimal.valueOf(amount), Cause.of(NamedCause.owner(container)));
                            }
                            break;
                        case ACCOUNT_NO_FUNDS:
                            player.sendMessage(Text.of(TextColors.RED, "You don't have enough funds!"));
                            break;
                        default:
                            player.sendMessage(Text.of(TextColors.RED, "Unknown error!"));
                    }
                });
            });
        } else {
            src.sendMessage(Text.of("You can't create a normal Coin Purse from the console!"));
        }

        return CommandResult.success();
    }
}
