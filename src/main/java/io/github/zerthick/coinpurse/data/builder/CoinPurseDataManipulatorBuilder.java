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
 */

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
        return create().fill(dataHolder);
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
