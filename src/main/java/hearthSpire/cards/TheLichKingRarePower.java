package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.powers.TheLichKingPower;

import static hearthSpire.DefaultMod.makeCardPath;

public class TheLichKingRarePower extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(TheLichKingRarePower.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("TheLichKing.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE; //稀有度
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;   //指向什么对方 ，若有就指向地方或自己，若无就是无目标释放
    private static final AbstractCard.CardType TYPE = CardType.POWER;       //类型
    public static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.COLORLESS;     //职业


    private static final int COST = 3;
    // /STAT DECLARATION/


    public TheLichKingRarePower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new TheLichKingPower(p, 1), 1));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            isInnate = true;
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
