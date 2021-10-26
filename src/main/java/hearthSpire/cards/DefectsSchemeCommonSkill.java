package hearthSpire.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.actions.DefectsSchemeAction;

import static hearthSpire.DefaultMod.makeCardPath;

public class DefectsSchemeCommonSkill extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(DefectsSchemeCommonSkill.class.getSimpleName());

    public static final String IMG = makeCardPath("DefectsScheme.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //稀有度
    private static final CardTarget TARGET = CardTarget.NONE;   //指向什么对方 ，若有就指向地方或自己，若无就是无目标释放
    private static final CardType TYPE = CardType.SKILL;       //类型
    public static final CardColor COLOR = CardColor.BLUE;     //职业

    private static final int COST = 1;

    // /STAT DECLARATION/


    public DefectsSchemeCommonSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        selfRetain = true;
        exhaust = true;
        magicNumber = baseMagicNumber = 1;     //可以升级的数字
    }

    public void onRetained() {
        this.upgradeMagicNumber(1);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DefectsSchemeAction(p, magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
}