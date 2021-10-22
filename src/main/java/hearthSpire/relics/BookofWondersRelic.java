package hearthSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import hearthSpire.DefaultMod;
import hearthSpire.cards.ScrollofWonderCard;
import hearthSpire.util.TextureLoader;

import static hearthSpire.DefaultMod.makeRelicOutlinePath;
import static hearthSpire.DefaultMod.makeRelicPath;

public class BookofWondersRelic extends CustomRelic {



    public static final String ID = DefaultMod.makeID("BookofWondersRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BookofWonders.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BookofWonders.png"));

    public BookofWondersRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    public void atPreBattle() {
        this.flash();
        this.addToBot(new MakeTempCardInDrawPileAction(new ScrollofWonderCard(),10, true, true, false));
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
