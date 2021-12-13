package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;

import static hearthSpire.DefaultMod.makeCardPath;

public class PurifiedShardRareSkill extends AbstractDynamicCard{
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(PurifiedShardRareSkill.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("PurifiedShard.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 3;
    private static boolean UPGRADED = false;

    // /STAT DECLARATION/


    public PurifiedShardRareSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int size = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        for(int i=0;i<size;i++){
            if(!((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDeadOrEscaped()) {
                this.addToBot(new InstantKillAction(AbstractDungeon.getCurrRoom().monsters.monsters.get(i)));
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}