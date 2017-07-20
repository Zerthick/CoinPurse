package io.github.zerthick.coinpurse.data;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;

import java.math.BigInteger;

public class CoinPurseKeys {

    public static final Key<MutableBoundedValue<Double>> COIN_PURSE_AMOUNT = KeyFactory.makeSingleKey(TypeToken.of(Double.class),
            new TypeToken<MutableBoundedValue<Double>>(){}, DataQuery.of("CoinPurseAmount"),"coinpurse:coin_purse_amount", "Coin Purse Amount");
}
