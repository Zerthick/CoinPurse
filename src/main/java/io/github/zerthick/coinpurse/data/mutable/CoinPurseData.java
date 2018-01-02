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

package io.github.zerthick.coinpurse.data.mutable;

import io.github.zerthick.coinpurse.data.CoinPurseKeys;
import io.github.zerthick.coinpurse.data.immutable.ImmutableCoinPurseData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;

import java.util.Optional;

public class CoinPurseData extends AbstractData<CoinPurseData, ImmutableCoinPurseData>{

    private double amount;

    public CoinPurseData() {
        this(0);
    }

    public CoinPurseData(double amount) {
        this.amount = amount;
        registerGettersAndSetters();
    }

    protected MutableBoundedValue<Double> amount() {
        return Sponge.getRegistry().getValueFactory().createBoundedValueBuilder(CoinPurseKeys.COIN_PURSE_AMOUNT)
                .defaultValue(0.0)
                .minimum(0.0)
                .maximum(Double.MAX_VALUE)
                .actualValue(amount)
                .build();
    }

    private double getAmount() {
        return amount;
    }

    private void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(CoinPurseKeys.COIN_PURSE_AMOUNT, this::getAmount);
        registerFieldSetter(CoinPurseKeys.COIN_PURSE_AMOUNT, this::setAmount);
        registerKeyValue(CoinPurseKeys.COIN_PURSE_AMOUNT, this::amount);
    }

    @Override
    public Optional<CoinPurseData> fill(DataHolder dataHolder, MergeFunction overlap) {

        dataHolder.get(CoinPurseData.class).ifPresent(data -> {
            CoinPurseData finalData = overlap.merge(this, data);
            setAmount(finalData.getAmount());
        });

        return Optional.of(this);
    }

    @Override
    public Optional<CoinPurseData> from(DataContainer container) {

        container.getDouble(CoinPurseKeys.COIN_PURSE_AMOUNT.getQuery())
                .ifPresent(this::setAmount);

        return Optional.of(this);
    }

    @Override
    public CoinPurseData copy() {
        return new CoinPurseData(amount);
    }

    @Override
    public ImmutableCoinPurseData asImmutable() {
        return new ImmutableCoinPurseData(amount);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(CoinPurseKeys.COIN_PURSE_AMOUNT, getAmount());
    }
}
