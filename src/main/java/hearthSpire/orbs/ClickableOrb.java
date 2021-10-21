package hearthSpire.orbs;

import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface ClickableOrb {
    default String[] CLICKABLE_DESCRIPTIONS() {
        return ClickableOrb.data.DESCRIPTION;
    }

    void onRightClick();

    default void clickUpdate() {
        if (this instanceof AbstractOrb) {
            AbstractOrb orb = (AbstractOrb)this;
            if ((Boolean)HitboxRightClick.rightClicked.get(orb.hb)) {
                this.onRightClick();
            }

        } else {
            throw new NotImplementedException();
        }
    }

    default boolean hovered() {
        if (this instanceof AbstractOrb) {
            AbstractOrb orb = (AbstractOrb)this;
            return orb.hb.hovered;
        } else {
            throw new NotImplementedException();
        }
    }

    public static class data {
        private static final String ID = "clickableorb:Clickable";
        private static final String[] DESCRIPTION;

        public data() {
        }

        static {
            OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString("clickableorb:Clickable");
            DESCRIPTION = orbStrings.DESCRIPTION;
        }
    }
}
