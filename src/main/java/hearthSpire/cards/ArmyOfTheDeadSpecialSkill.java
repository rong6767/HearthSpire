package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.variables.CustomTags;

import static hearthSpire.DefaultMod.makeCardPath;

public class ArmyOfTheDeadSpecialSkill extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ArmyOfTheDeadSpecialSkill.class.getSimpleName());

    public static final String IMG = makeCardPath("ArmyOfTheDead.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL; //稀有度
    private static final AbstractCard.CardTarget TARGET = CardTarget.NONE;   //指向什么对方 ，若有就指向地方或自己，若无就是无目标释放
    private static final AbstractCard.CardType TYPE = CardType.SKILL;       //类型
    public static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.COLORLESS;     //职业

    public static final AbstractCard.CardTags TAGS = CustomTags.DEATH_KNIGHT;

    private static final int COST = 3;
    private static final int magicNumber = 5;
    // /STAT DECLARATION/


    public ArmyOfTheDeadSpecialSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isEthereal = true;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; ++i) {
            this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster( null, true, AbstractDungeon.cardRandomRng), false));
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
