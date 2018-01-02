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

package io.github.zerthick.coinpurse.data;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;

public class CoinPurseKeys {

    public static Key<MutableBoundedValue<Double>> COIN_PURSE_AMOUNT;

    public static void init() {
        COIN_PURSE_AMOUNT = Key.builder()
                .type(new TypeToken<MutableBoundedValue<Double>>(){})
                .query(DataQuery.of("CoinPurseAmount"))
                .id("coinpurse:coin_purse_amount")
                .name("Coin Purse Amount")
                .build();
    }

}
