package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hearthSpire.DefaultMod;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static hearthSpire.DefaultMod.makeCardPath;

public class AcademicEspionageUncommonSkill extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(AcademicEspionageUncommonSkill.class.getSimpleName());

    public static final String IMG = makeCardPath("AcademicEspionage.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //稀有度
    private static final CardTarget TARGET = CardTarget.NONE;   //指向什么对方 ，若有就指向地方或自己，若无就是无目标释放
    private static final CardType TYPE = CardType.SKILL;       //类型
    public static final CardColor COLOR = CardColor.GREEN;     //职业

    private static final int COST = 2;

    // /STAT DECLARATION/


    public AcademicEspionageUncommonSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
           //可以升级的数字
    }

    private AbstractCard.CardRarity randomRarity() {
        int pick = new Random().nextInt(5);
        return AbstractCard.CardRarity.values()[pick];
    }

    public static AbstractCard getAnyColorCard(AbstractCard.CardRarity rarity) {
        CardGroup anyCard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = CardLibrary.cards.entrySet().iterator();
        CardColor color = AbstractDungeon.player.getCardColor();

        while(true) {
            Map.Entry c;
            do {
                do {
                    do {
                        do {
                            do {
                                if (!var2.hasNext()) {
                                    anyCard.shuffle(AbstractDungeon.cardRng);
                                    return anyCard.getRandomCard(true, rarity).makeCopy();
                                }

                                c = (Map.Entry) var2.next();
                            } while(((AbstractCard)c.getValue()).color == color);
                        } while(((AbstractCard)c.getValue()).rarity != rarity);
                    } while(((AbstractCard)c.getValue()).type == AbstractCard.CardType.CURSE);
                } while(((AbstractCard)c.getValue()).type == AbstractCard.CardType.STATUS);
            } while(UnlockTracker.isCardLocked((String)c.getKey()) && !Settings.treatEverythingAsUnlocked());

            anyCard.addToBottom((AbstractCard)c.getValue());
        }
    }

    public AbstractCard returnTrulyDiverseRandomCardInCombat() {
        return  getAnyColorCard(randomRarity());
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < 10; ++i) {
            AbstractCard card = returnTrulyDiverseRandomCardInCombat().makeCopy();
            if (card.cost > 1) {
                card.cost = 1;
                card.costForTurn = 1;
                card.isCostModified = true;
            }
            this.addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
        }

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