package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.powers.BlightbornTamsinPower;

import static hearthSpire.DefaultMod.makeCardPath;

public class BlightbornTamsinRarePower extends AbstractDynamicCard{

    // /TEXT DECLARATION/
    public static final String ID = DefaultMod.makeID(BlightbornTamsinRarePower.class.getSimpleName());
    public static final String IMG = makeCardPath("BlightbornTamsin.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;//稀有度
    private static final CardTarget TARGET = CardTarget.NONE;   //指向什么对方 ，若有就指向地方或自己，若无就是无目标释放
    private static final CardType TYPE = CardType.POWER;       //类型
    public static final CardColor COLOR = CardColor.RED;     //职业

    private static final int COST = 2;

    // /STAT DECLARATION/


    public BlightbornTamsinRarePower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        selfRetain = true;
        this.magicNumber = baseMagicNumber =1;     //可以升级的数字
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        System.out.println(this.magicNumber);
        this.addToBot(new ApplyPowerAction(p, p, new BlightbornTamsinPower(p), 1));
    }

    public void onRetained() {
        this.upgradeMagicNumber(1);
        System.out.println(this.magicNumber);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}