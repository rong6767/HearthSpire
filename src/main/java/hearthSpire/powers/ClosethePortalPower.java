package hearthSpire.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hearthSpire.DefaultMod;
import hearthSpire.cards.DemonslayerKurtrusSpecialPower;
import hearthSpire.util.TextureLoader;

import static hearthSpire.DefaultMod.makePowerPath;

public class ClosethePortalPower extends AbstractPower {
    public static final String POWER_ID = DefaultMod.makeID("ClosethePortalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int counter = 0;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ClosethePortal_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ClosethePortal_power32.png"));

    public ClosethePortalPower(AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;


        type = PowerType.BUFF;
        isTurnBased = false;
        this.owner = owner;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        counter = 0;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (counter < 7) {
            counter++;
        }
        if(counter >= 7){

            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            counter = 0;
            addToBot(new MakeTempCardInDrawPileAction(new DemonslayerKurtrusSpecialPower(), 1, true, true));
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }





}