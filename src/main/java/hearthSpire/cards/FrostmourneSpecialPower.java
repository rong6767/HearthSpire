package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.powers.FrostmournePower;
import hearthSpire.variables.CustomTags;

import static hearthSpire.DefaultMod.makeCardPath;

public class FrostmourneSpecialPower extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(FrostmourneSpecialPower.class.getSimpleName());

    public static final String IMG = makeCardPath("Frostmourne.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL; //稀有度
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;   //指向什么对方 ，若有就指向地方或自己，若无就是无目标释放
    private static final AbstractCard.CardType TYPE = CardType.POWER;       //类型
    public static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.COLORLESS;     //职业

    public static final AbstractCard.CardTags TAGS = CustomTags.DEATH_KNIGHT;

    private static final int COST = 3;
    // /STAT DECLARATION/


    public FrostmourneSpecialPower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isEthereal = true;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower("hearthSpire:FrostmournePower")) {
            this.addToBot(new ApplyPowerAction(p, p, new FrostmournePower(p), 1));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }
}
