package hearthSpire.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.actions.SilentsSchemeAction;

import static hearthSpire.DefaultMod.makeCardPath;

public class SilentsSchemeCommonSkill extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(SilentsSchemeCommonSkill.class.getSimpleName());
    public static final String IMG = makeCardPath("SilentsScheme.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.GREEN;

    private static final int COST = 1;


    // /STAT DECLARATION/


    public SilentsSchemeCommonSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber =1;
        exhaust = true;
        selfRetain = true;
    }

    public void onRetained() {
        this.upgradeMagicNumber(1);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new SilentsSchemeAction(p, magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(0);
            initializeDescription();
        }
    }

}