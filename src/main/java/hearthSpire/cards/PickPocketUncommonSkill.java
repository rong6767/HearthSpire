package hearthSpire.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
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

public class PickPocketUncommonSkill extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(PickPocketUncommonSkill.class.getSimpleName());

    public static final String IMG = makeCardPath("PickPocket.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.GREEN;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public PickPocketUncommonSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.isEthereal=true;
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
        AbstractCard c1 = returnTrulyDiverseRandomCardInCombat().makeCopy();
        AbstractCard c2 = returnTrulyDiverseRandomCardInCombat().makeCopy();
        if (AbstractDungeon.player.hasRelic(DefaultMod.makeID("StickyFingerRelic"))) {
            if (c1.cost >= 1 && c1.color != AbstractDungeon.player.getCardColor()) {
                int newCost = c1.cost - 1;
                if (c1.cost != newCost) {
                    c1.cost = newCost;
                    c1.costForTurn = c1.cost;
                    c1.isCostModified = true;
                }
            }
            if (c2.cost >= 1 && c2.color != AbstractDungeon.player.getCardColor()) {
                int newCost = c2.cost - 1;
                if (c2.cost != newCost) {
                    c2.cost = newCost;
                    c2.costForTurn = c2.cost;
                    c2.isCostModified = true;
                }
            }

            if (this.upgraded) {
                c1.upgrade();
                c2.upgrade();
                this.addToBot(new MakeTempCardInHandAction(c1, 1));
                this.addToBot(new MakeTempCardInHandAction(c2, 1));
                this.addToBot(new MakeTempCardInHandAction(this, 1));
            } else {
                this.addToBot(new MakeTempCardInHandAction(c1, 1));
                this.addToBot(new MakeTempCardInHandAction(c2, 1));
                this.addToBot(new MakeTempCardInHandAction(this, 1));
            }


        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}