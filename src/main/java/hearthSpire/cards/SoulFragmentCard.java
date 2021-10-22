package hearthSpire.cards;


import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;

import static hearthSpire.DefaultMod.makeCardPath;

public class SoulFragmentCard extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(SoulFragmentCard.class.getSimpleName());

    public static final String IMG = makeCardPath("SoulFragment.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.STATUS;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public SoulFragmentCard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        this.isEthereal = true;
    }

    public void triggerWhenDrawn() {
        this.addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
        this.addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        this.addToBot(new DrawCardAction(1));
    }

    @Override
    public void triggerOnExhaust() {
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }


}
