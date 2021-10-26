package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;

import static hearthSpire.DefaultMod.makeCardPath;

public class WatchersSchemeCommonAttack extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(WatchersSchemeCommonAttack.class.getSimpleName());
    public static final String IMG = makeCardPath("WatchersScheme.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.PURPLE;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/

    public WatchersSchemeCommonAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        selfRetain = true;
        baseDamage = DAMAGE;
        magicNumber = 4;
    }

    public void onRetained() {
        this.upgradeDamage(UPGRADE_PLUS_DMG);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, baseDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(magicNumber);
            initializeDescription();
        }
    }
}