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
