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

package io.github.zerthick.coinpurse.data.immutable;

import io.github.zerthick.coinpurse.data.CoinPurseKeys;
import io.github.zerthick.coinpurse.data.mutable.CoinPurseData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableBoundedValue;

/**
 * Created by chase on 7/18/17.
 */
public class ImmutableCoinPurseData extends AbstractImmutableData<ImmutableCoinPurseData, CoinPurseData>{

    private final double amount;

    public ImmutableCoinPurseData() {
        this(0);
    }

    public ImmutableCoinPurseData(double amount) {
        this.amount = amount;
        registerGetters();
    }

    protected ImmutableBoundedValue<Double> amount() {
        return Sponge.getRegistry().getValueFactory().createBoundedValueBuilder(CoinPurseKeys.COIN_PURSE_AMOUNT)
                .defaultValue(0.0)
                .minimum(0.0)
                .maximum(Double.MAX_VALUE)
                .actualValue(this.amount)
                .build().asImmutable();
    }

    private double getAmount() {
        return amount;
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(CoinPurseKeys.COIN_PURSE_AMOUNT, this::getAmount);
        registerKeyValue(CoinPurseKeys.COIN_PURSE_AMOUNT, this::amount);
    }

    @Override
    public CoinPurseData asMutable() {
        return new CoinPurseData(amount);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(CoinPurseKeys.COIN_PURSE_AMOUNT, amount);
    }
}
