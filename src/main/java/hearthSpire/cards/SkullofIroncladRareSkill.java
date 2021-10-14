package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import hearthSpire.DefaultMod;
import hearthSpire.actions.SkullFollowAction;
import hearthSpire.characters.TheDefault;

import static hearthSpire.DefaultMod.makeCardPath;

public class SkullofIroncladRareSkill extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SkullofIroncladRareSkill.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.RED;

    private static final int COST = 3;
    private static final int UPGRADE_REDUCED_COST = 3;

    private static final int DRAW = 2;

    private static final int BLOCK = 1;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    // /STAT DECLARATION/


    public SkullofIroncladRareSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DRAW;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hand.getBottomCard().cardID.equals(ID) || p.hand.getTopCard().cardID.equals(ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new DrawCardAction(DRAW, new SkullFollowAction()));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(
                    new DrawCardAction(p,DRAW));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}