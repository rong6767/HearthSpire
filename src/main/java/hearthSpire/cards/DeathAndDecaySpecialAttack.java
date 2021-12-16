package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;
import hearthSpire.variables.CustomTags;

import static hearthSpire.DefaultMod.makeCardPath;

public class DeathAndDecaySpecialAttack extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(DeathAndDecaySpecialAttack.class.getSimpleName());

    public static final String IMG = makeCardPath("DeathAndDecay.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL; //稀有度
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;   //指向什么对方 ，若有就指向地方或自己，若无就是无目标释放
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;       //类型
    public static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.COLORLESS;     //职业

    public static final AbstractCard.CardTags TAGS = CustomTags.DEATH_KNIGHT;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    // /STAT DECLARATION/


    public DeathAndDecaySpecialAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isEthereal = true;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, DAMAGE, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }
}
