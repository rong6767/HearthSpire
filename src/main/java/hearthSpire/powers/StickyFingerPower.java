package hearthSpire.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hearthSpire.DefaultMod;
import hearthSpire.util.TextureLoader;

import static hearthSpire.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class StickyFingerPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("StickyFingerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractCard.CardColor color = AbstractDungeon.player.getCardColor();

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("StickyFinger84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("StickyFinger32.png"));

    public StickyFingerPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

    }

    public void onCardDraw(AbstractCard card) {
        if (card.cost >= 1 && card.color != color) {
            int newCost = card.cost - 1;
            if (card.cost != newCost) {
                card.cost = newCost;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }

            card.freeToPlayOnce = false;
        }

    }
    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];

    }

    @Override
    public AbstractPower makeCopy() {
        return new StickyFingerPower(owner, source, amount);
    }
}
