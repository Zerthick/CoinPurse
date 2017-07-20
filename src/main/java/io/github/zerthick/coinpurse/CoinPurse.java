package io.github.zerthick.coinpurse;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import io.github.zerthick.coinpurse.data.CoinPurseDataRegister;
import io.github.zerthick.coinpurse.data.CoinPurseKeys;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.Color;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Plugin(
        id = "coinpurse",
        name = "CoinPurse",
        version = "0.0.1",
        description = "A simple plugin to convert money into a physical item",
        authors = {
                "Zerthick"
        }
)
public class CoinPurse {

    private EconomyService economyService;

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer instance;


    public Logger getLogger() {
        return logger;
    }

    public PluginContainer getInstance() {
        return instance;
    }

    public EconomyService getEconomyService() {
        return economyService;
    }

    public ItemStack buildCoinPurseItem(double amount) {

        // Create base itemStack
        ItemStack coinPurseItem = ItemStack.of(ItemTypes.DYE, 1);
        coinPurseItem.offer(Keys.DYE_COLOR, DyeColors.BLACK);

        // Enchant the item to make it glow
        coinPurseItem.offer(Keys.ITEM_ENCHANTMENTS, ImmutableList.of(new ItemEnchantment(Enchantments.UNBREAKING, 1)));

        // Set the name
        coinPurseItem.offer(Keys.DISPLAY_NAME, Text.of(TextColors.GOLD, "[Coin Purse]"));

        // Display coin amount in lore
        coinPurseItem.offer(Keys.ITEM_LORE, ImmutableList.of(Text.of(TextColors.GRAY, economyService.getDefaultCurrency().format(BigDecimal.valueOf(amount)))));

        // Set the coin purse data
        coinPurseItem.offer(CoinPurseKeys.COIN_PURSE_AMOUNT, amount);

        return coinPurseItem;
    }

    @Listener
    public void onGameInit(GameInitializationEvent event) {

        //Register Custom Data Manipulators
        CoinPurseDataRegister.registerData(getInstance());
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

        // Log Start Up to Console
        getLogger().info(
                instance.getName() + " version " + instance.getVersion().orElse("")
                        + " enabled!");
    }

    @Listener
    public void onItemInteract(InteractItemEvent.Primary event, @Root Player player) {

        ItemStackSnapshot item = event.getItemStack();
        if(item.getType() == ItemTypes.DYE) {
            item.get(Keys.DYE_COLOR).ifPresent(dyeColor -> {

                // Check if this is an ink sac
                if (dyeColor == DyeColors.BLACK) {
                    item.get(CoinPurseKeys.COIN_PURSE_AMOUNT).ifPresent(amount -> {

                        if(player.hasPermission("coinpurse.use")) {

                            // Deposit the funds
                            economyService.getOrCreateAccount(player.getUniqueId()).ifPresent(playerAccount -> {
                                TransactionResult result = playerAccount.deposit(economyService.getDefaultCurrency(), BigDecimal.valueOf(amount), Cause.of(NamedCause.owner(getInstance())));
                                if(result.getResult() == ResultType.SUCCESS) {

                                    // If the have more that one item in the stack, decrement it, otherwise remove the item
                                    if(item.getCount() > 1) {
                                        ItemStack newItem = item.createStack();
                                        newItem.setQuantity(newItem.getQuantity() - 1);
                                        player.setItemInHand(event.getHandType(), newItem);
                                    } else {
                                        player.setItemInHand(event.getHandType(),null);
                                    }
                                }
                            });
                        }

                        // Cancel interacting with the ink sac
                        event.setCancelled(true);
                    });
                }
            });
        }
    }

    @Listener
    public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
        //Hook into economy service
        if (event.getService().equals(EconomyService.class)) {
            economyService = (EconomyService) event.getNewProviderRegistration().getProvider();
        }
    }
}
