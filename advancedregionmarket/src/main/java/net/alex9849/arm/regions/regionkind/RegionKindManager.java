package net.alex9849.arm.regions.regionkind;

import net.alex9849.arm.AdvancedRegionMarket;
import net.alex9849.arm.util.YamlFileManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegionKindManager extends YamlFileManager<RegionKind> {

    public RegionKindManager(File savepath, InputStream resourceStream) {
        super(savepath, resourceStream);
    }

    @Override
    public List<RegionKind> loadSavedObjects(YamlConfiguration yamlConfiguration) {
        List<RegionKind> regionKindList = new ArrayList<>();

        FileConfiguration defaultConf = AdvancedRegionMarket.getARM().getConfig();

        RegionKind.DEFAULT.setName(defaultConf.getString("DefaultRegionKind.DisplayName"));
        RegionKind.DEFAULT.setMaterial(Material.getMaterial(defaultConf.getString("DefaultRegionKind.Item")));
        RegionKind.DEFAULT.setDisplayInGUI(defaultConf.getBoolean("DefaultRegionKind.DisplayInGUI"));
        RegionKind.DEFAULT.setDisplayInLimits(defaultConf.getBoolean("DefaultRegionKind.DisplayInLimits"));
        RegionKind.DEFAULT.setPaybackPercentage(defaultConf.getDouble("DefaultRegionKind.PaypackPercentage"));
        List<String> defaultlore = defaultConf.getStringList("DefaultRegionKind.Lore");
        RegionKind.DEFAULT.setLore(defaultlore);

        RegionKind.SUBREGION.setName(defaultConf.getString("SubregionRegionKind.DisplayName"));
        RegionKind.SUBREGION.setMaterial(Material.getMaterial(defaultConf.getString("SubregionRegionKind.Item")));
        RegionKind.SUBREGION.setDisplayInGUI(defaultConf.getBoolean("SubregionRegionKind.DisplayInGUI"));
        RegionKind.SUBREGION.setDisplayInLimits(defaultConf.getBoolean("SubregionRegionKind.DisplayInLimits"));
        RegionKind.SUBREGION.setPaybackPercentage(defaultConf.getDouble("SubregionRegionKind.PaypackPercentage"));
        List<String> subregionlore = defaultConf.getStringList("SubregionRegionKind.Lore");
        RegionKind.SUBREGION.setLore(subregionlore);

        if(yamlConfiguration.get("RegionKinds") != null) {
            ConfigurationSection regionKindsSection = yamlConfiguration.getConfigurationSection("RegionKinds");
            List<String> regionKinds = new ArrayList<>(regionKindsSection.getKeys(false));
            if(regionKinds != null) {
                for(String regionKindID : regionKinds) {
                    ConfigurationSection rkConfSection = regionKindsSection.getConfigurationSection(regionKindID);
                    if(rkConfSection != null) {
                        regionKindList.add(RegionKind.parse(rkConfSection, regionKindID));
                    }
                }
            }
        }
        return regionKindList;
    }

    @Override
    public void saveObjectToYamlObject(RegionKind regionKind, YamlConfiguration yamlConfiguration) {
        yamlConfiguration.set("RegionKinds." + regionKind.getName(), regionKind.toConfigureationSection());
    }

    @Override
    public void removeObjectFromYamlObject(RegionKind regionKind, YamlConfiguration yamlConfiguration) {
        yamlConfiguration.set("RegionKinds." + regionKind.getName(), null);
    }

    @Override
    public void addStaticSettings(YamlConfiguration yamlConfiguration) {

    }

    public List<String> completeTabRegionKinds(String arg, String prefix) {
        List<String> returnme = new ArrayList<>();

        List<RegionKind> regionKinds = this.getObjectListCopy();

        for (RegionKind regionkind : regionKinds) {
            if ((prefix + regionkind.getName()).toLowerCase().startsWith(arg)) {
                returnme.add(prefix + regionkind.getName());
            }
        }
        if ((prefix + "default").startsWith(arg)) {
            returnme.add(prefix + "default");
        }
        if ((prefix + "subregion").startsWith(arg)) {
            returnme.add(prefix + "subregion");
        }

        return returnme;
    }

    public boolean kindExists(String kind){

        List<RegionKind> regionKinds = this.getObjectListCopy();
        for(RegionKind regionKind : regionKinds) {
            if(regionKind.getName().equalsIgnoreCase(kind)){
                return true;
            }
        }

        if(kind.equalsIgnoreCase("default")) {
            return true;
        }
        if(kind.equalsIgnoreCase(RegionKind.DEFAULT.getDisplayName())){
            return true;
        }
        if(kind.equalsIgnoreCase("subregion")) {
            return true;
        }
        if(kind.equalsIgnoreCase(RegionKind.SUBREGION.getDisplayName())){
            return true;
        }
        return false;
    }

    public RegionKind getRegionKind(String name){

        List<RegionKind> regionKinds = this.getObjectListCopy();
        for(RegionKind regionKind : regionKinds) {
            if(regionKind.getName().equalsIgnoreCase(name)){
                return regionKind;
            }
        }

        if(name.equalsIgnoreCase("default") || name.equalsIgnoreCase(RegionKind.DEFAULT.getDisplayName())){
            return RegionKind.DEFAULT;
        }
        if(name.equalsIgnoreCase("subregion") || name.equalsIgnoreCase(RegionKind.SUBREGION.getDisplayName())){
            return RegionKind.SUBREGION;
        }
        return null;
    }
}