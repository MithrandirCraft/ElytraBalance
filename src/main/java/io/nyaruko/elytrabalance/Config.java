package io.nyaruko.elytrabalance;

public class Config {
    public int configVersion = 1;
    public boolean removeElytraOnBreak = false;
    public int elytraDamageOnRocketUse = 40;
    public boolean playerDamageOnNoStarRocketUse = true;
    String NoStar = "The damage dealt to a player when they boost with a rocket that is not equipped with a firework star, assuming damage for this is enabled in this plugin.";
    public double damagePerNoStarRocketUse = 7.0D;
    String Star = "Additional damage dealt to a player when they boost with a rocket that is equipped with a firework star on top of the default 2 and a half hearts.";
    public double additionalDamagePerStarRocketUse = 0.0D;
    public int elytraDamageOnRiptideUseWithElytra = 40;
    public int tridentDamageOnRiptideUseWithElytra = 10;
    public boolean canConsumeFoodInFlight = true;
    public boolean canRepairElytra = true;
    public boolean canMendElytra = true;

    public String consumableBlockedMessage = "You can't eat/drink while gliding!";
    public String elytraDestroyedAndRemovedMessage = "Your elytra has shattered into a million pieces!";
    public String repairAttemptBlockedMessage = "You can't fix an elytra with an anvil!";
    public String riptideDamagedElytraMessage = "Riptide damaged your elytra!.";
    public String riptideDamagedTrident = "Riptide damaged your trident due to usage in conjunction with elytra!.";

    public Config(int configVersion) {
        this.configVersion = configVersion;
    }
}
