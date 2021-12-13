package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.powers.SorcerersGambitPower;

import static hearthSpire.DefaultMod.makeCardPath;

public class SorcerersGambitRarePower extends AbstractDynamicCard{


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SorcerersGambitRarePower.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("SorcerersGambit.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    public static final AbstractCard.CardColor COLOR = CardColor.BLUE;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public SorcerersGambitRarePower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isInnate = true;
        selfRetain=true;
        magicNumber = baseMagicNumber = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!p.hasPower("hearthSpire:SorcerersGambitPower")&&!p.hasPower("hearthSpire:StallforTimePower")&&!p.hasPower("hearthSpire:ReachthePortalRoomPower")) {
            this.addToBot(new ApplyPowerAction(p, p, new SorcerersGambitPower(p), 1));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}