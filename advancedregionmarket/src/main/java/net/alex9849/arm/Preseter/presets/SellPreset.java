package net.alex9849.arm.Preseter.presets;

import net.alex9849.arm.Preseter.commands.*;
import net.alex9849.arm.regions.RegionKind;
import net.alex9849.arm.regions.price.Autoprice.AutoPrice;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SellPreset extends Preset{

    public SellPreset(String name, boolean hasPrice, double price, RegionKind regionKind, boolean autoReset, boolean isHotel, boolean doBlockReset, boolean isUserResettable, int allowedSubregions, AutoPrice autoPrice, List<String> setupCommands){
        super(name, hasPrice, price, regionKind, autoReset, isHotel, doBlockReset, isUserResettable, allowedSubregions, autoPrice, setupCommands);
    }

    @Override
    public void getAdditionalInfo(Player player) {
    }

    @Override
    public PresetType getPresetType() {
        return PresetType.SELLPRESET;
    }

    public SellPreset getCopy(){
        List<String> newsetupCommands = new ArrayList<>();
        for(String cmd : setupCommands) {
            newsetupCommands.add(cmd);
        }
        return new SellPreset(this.name, this.hasPrice, this.price, this.regionKind, this.autoReset, this.isHotel, this.doBlockReset, this.isUserResettable, this.allowedSubregions, this.autoPrice, newsetupCommands);
    }

    @Override
    public boolean canPriceLineBeLetEmpty() {
        return this.hasPrice() || this.hasAutoPrice();
    }

}
