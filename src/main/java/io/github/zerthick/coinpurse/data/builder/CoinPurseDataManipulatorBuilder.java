package io.github.zerthick.coinpurse.data.builder;

import io.github.zerthick.coinpurse.data.CoinPurseKeys;
import io.github.zerthick.coinpurse.data.immutable.ImmutableCoinPurseData;
import io.github.zerthick.coinpurse.data.mutable.CoinPurseData;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

public class CoinPurseDataManipulatorBuilder extends AbstractDataBuilder<CoinPurseData> implements DataManipulatorBuilder<CoinPurseData, ImmutableCoinPurseData>{

    public CoinPurseDataManipulatorBuilder() {
        super(CoinPurseData.class, 1);
    }

    @Override
    public CoinPurseData create() {
        return new CoinPurseData();
    }

    @Override
    public Optional<CoinPurseData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(CoinPurseData.class).orElse(new CoinPurseData()));
    }

    @Override
    protected Optional<CoinPurseData> buildContent(DataView container) throws InvalidDataException {
        if(container.contains(CoinPurseKeys.COIN_PURSE_AMOUNT.getQuery())) {
            final double amount = container.getDouble(CoinPurseKeys.COIN_PURSE_AMOUNT.getQuery()).get();

            return Optional.of(new CoinPurseData(amount));
        }

        return Optional.empty();
    }
}
