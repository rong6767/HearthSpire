package hearthSpire.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hearthSpire.DefaultMod;
import hearthSpire.cards.*;
import hearthSpire.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hearthSpire.DefaultMod.makePowerPath;

public class TheLichKingPower extends AbstractPower {
    public static final String POWER_ID = DefaultMod.makeID("TheLichKingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private List<AbstractCard> cards = new ArrayList<>();


    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("TheLichKing_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("TheLichKing_power32.png"));

    public TheLichKingPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        cards.add(new DeathGripSpecialSkill());
        cards.add(new ObliterateSpecialSkill());
        cards.add(new DeathCoilSpecialAttack());
        cards.add(new DeathAndDecaySpecialAttack());
        cards.add(new AntiMagicShellSpecialSkill());
        cards.add(new DoomPactSpecialAttack());
        cards.add(new ArmyOfTheDeadSpecialSkill());
        cards.add(new FrostmourneSpecialPower());

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            for (int i = 0; i < amount; i++) {
                AbstractCard card = cards.get(new Random().nextInt(cards.size()));
                this.addToBot(new MakeTempCardInHandAction(card.makeCopy(), 1));
            }
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }


}
