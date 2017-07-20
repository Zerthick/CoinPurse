package io.github.zerthick.coinpurse.data;

import io.github.zerthick.coinpurse.data.builder.CoinPurseDataManipulatorBuilder;
import io.github.zerthick.coinpurse.data.immutable.ImmutableCoinPurseData;
import io.github.zerthick.coinpurse.data.mutable.CoinPurseData;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.plugin.PluginContainer;

public class CoinPurseDataRegister {
    public static void registerData(PluginContainer container) {
        DataRegistration.<CoinPurseData, ImmutableCoinPurseData>builder()
                .dataClass(CoinPurseData.class)
                .immutableClass(ImmutableCoinPurseData.class)
                .builder(new CoinPurseDataManipulatorBuilder())
                .manipulatorId("coin_purse")
                .dataName("Coin Purse Data")
                .buildAndRegister(container);
    }
}
