
package hearthSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import hearthSpire.DefaultMod;

import java.util.ArrayList;
import java.util.Iterator;

public class DragonsHoardAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private boolean upgraded;

    public DragonsHoardAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (this.upgraded) {
                        disCard.upgrade();
                    }
                    if (AbstractDungeon.player.hasRelic(DefaultMod.makeID("StickyFingerRelic"))) {
                        if (disCard.cost >= 1 && disCard.color != AbstractDungeon.player.getCardColor()) {
                            int newCost = disCard.cost - 1;
                            if (disCard.cost != newCost) {
                                disCard.cost = newCost;
                                disCard.costForTurn = disCard.cost;
                                disCard.isCostModified = true;
                            }
                        }
                    }

                    disCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList derp = new ArrayList();

        while (derp.size() != 3) {
            boolean dupe = false;
            CardRarity cardRarity;
            cardRarity = CardRarity.RARE;

            AbstractCard tmp = CardLibrary.getAnyColorCard(cardRarity);
            Iterator var6 = derp.iterator();

            while (var6.hasNext()) {
                AbstractCard c = (AbstractCard) var6.next();
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                derp.add(tmp.makeCopy());
            }
        }

        return derp;
    }
}
