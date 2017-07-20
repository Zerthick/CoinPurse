package io.github.zerthick.coinpurse.cmd.cmdexecutors;

import io.github.zerthick.coinpurse.CoinPurse;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.plugin.PluginContainer;

public abstract class AbstractCommandExecutor implements CommandExecutor {

    protected PluginContainer container;
    protected CoinPurse plugin;

    public AbstractCommandExecutor(CoinPurse plugin) {
        this.plugin = plugin;
        container = plugin.getInstance();
    }
}
