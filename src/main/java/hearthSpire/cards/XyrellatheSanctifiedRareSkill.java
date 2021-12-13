package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;

import static hearthSpire.DefaultMod.makeCardPath;

public class XyrellatheSanctifiedRareSkill extends AbstractDynamicCard{
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(XyrellatheSanctifiedRareSkill.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("XyrellatheSanctified.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static boolean UPGRADED = false;

    // /STAT DECLARATION/


    public XyrellatheSanctifiedRareSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInDiscardAction(new PurifiedShardRareSkill(), 1));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}