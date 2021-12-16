package hearthSpire.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.actions.ObliterateAction;
import hearthSpire.variables.CustomTags;

import static hearthSpire.DefaultMod.makeCardPath;

public class ObliterateSpecialSkill extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ObliterateSpecialSkill.class.getSimpleName());

    public static final String IMG = makeCardPath("Obliterate.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //稀有度
    private static final CardTarget TARGET = CardTarget.ENEMY;   //指向什么对方 ，若有就指向地方或自己，若无就是无目标释放
    private static final CardType TYPE = CardType.SKILL;       //类型
    public static final CardColor COLOR = CardColor.COLORLESS;     //职业

    public static final CardTags TAGS = CustomTags.DEATH_KNIGHT;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public ObliterateSpecialSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isEthereal = true;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ObliterateAction(p, m));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }
}
