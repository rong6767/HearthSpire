

package hearthSpire.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ShadowVIsionsAction extends AbstractGameAction {
    private AbstractPlayer p;
    private final boolean upgrade;
    private ArrayList<AbstractCard> exhumes = new ArrayList();

    public ShadowVIsionsAction(boolean upgrade) {
        this.upgrade = upgrade;
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {


            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
            } else if (this.p.drawPile.size() == 1) {
                    AbstractCard card = this.p.drawPile.getTopCard().makeCopy();
                    card.unfadeOut();
                    this.p.hand.addToHand(card);
                    if (this.upgrade && card.canUpgrade()) {
                        card.upgrade();
                    }
                    this.isDone = true;

            } else {
                AbstractCard card = this.p.drawPile.getRandomCard(false).makeCopy();
                this.p.hand.addToHand(card);
                if (this.upgrade && card.canUpgrade()) {
                    card.upgrade();
                }

                this.isDone = true;
            }
    }


}
