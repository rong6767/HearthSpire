package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.powers.TheDemonSeedPower;

import static hearthSpire.DefaultMod.makeCardPath;

public class TheDemonSeedRarePower extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(TheDemonSeedRarePower.class.getSimpleName());
    public static final String IMG = makeCardPath("TheDemonSeed.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    public static final AbstractCard.CardColor COLOR = CardColor.RED;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 1;

    private static final int MAGIC = 1;

    // /STAT DECLARATION/


    public TheDemonSeedRarePower() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        this.isInnate = true;



    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!p.hasPower("hearthSpire:TheDemonSeedPower")&&!p.hasPower("hearthSpire:EstablishtheLinkPower")&&!p.hasPower("hearthSpire:CompletetheRitualPower")&&!p.hasPower("hearthSpire:BlightbornTamsinPower")) {
            this.addToBot(new ApplyPowerAction(p, p, new TheDemonSeedPower(p), 1));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}