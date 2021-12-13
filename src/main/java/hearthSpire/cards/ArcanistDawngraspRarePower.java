package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import hearthSpire.DefaultMod;

import static hearthSpire.DefaultMod.makeCardPath;

public class ArcanistDawngraspRarePower extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ArcanistDawngraspRarePower.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("ArcanistDawngrasp.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static boolean UPGRADED = false;

    // /STAT DECLARATION/


    public ArcanistDawngraspRarePower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new FocusPower(p, 5)));
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