package hearthSpire.orbs;

import basemod.abstracts.CustomOrb;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class BetterOrb extends CustomOrb implements ClickableOrb{


    public BetterOrb(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription, String evokeDescription, String imgPath) {
        super(ID, NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription, imgPath);
    }

    @Override
    public void onEvoke() {

    }

    @Override
    public AbstractOrb makeCopy() {
        return null;
    }

    @Override
    public void playChannelSFX() {

    }

    @Override
    public void onRightClick() {

    }
}
