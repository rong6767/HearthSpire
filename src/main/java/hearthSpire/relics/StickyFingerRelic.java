package hearthSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import hearthSpire.DefaultMod;
import hearthSpire.util.TextureLoader;

import static hearthSpire.DefaultMod.makeRelicOutlinePath;
import static hearthSpire.DefaultMod.makeRelicPath;

public class StickyFingerRelic extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 Strength (i.e. Vajra)
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("StickyFingerRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StickyFinger.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("StickyFinger.png"));

    public StickyFingerRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public void atPreBattle() {
        this.flash();
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
