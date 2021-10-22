
package hearthSpire.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class ReduceCostFromOtherClassAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ReduceCostFromOtherClassAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            boolean betterPossible = false;
            boolean possible = false;
            Iterator var3 = this.p.hand.group.iterator();

            while (var3.hasNext()) {
                AbstractCard c = (AbstractCard) var3.next();
                if (c.costForTurn > 0) {
                    betterPossible = true;
                } else if (c.cost > 0) {
                    possible = true;
                }
            }

            if (betterPossible || possible) {
                this.findAndModifyCard(betterPossible);
            }
        }

        this.tickDuration();
    }

    private void findAndModifyCard(boolean better) {
        Iterator var4 = this.p.hand.group.iterator();
        AbstractCard.CardColor color = p.getCardColor();

        while (var4.hasNext()) {
            AbstractCard c = (AbstractCard) var4.next();
            if (c.color != color) {

                if (0 < c.costForTurn && c.costForTurn <= 2) {
                    c.cost = 0;
                    c.costForTurn = 0;
                    c.isCostModified = true;
                    c.superFlash(Color.GOLD.cpy());
                } else if (c.costForTurn > 2) {
                    c.cost -= 2;
                    c.costForTurn -= 2;
                    c.isCostModified = true;
                    c.superFlash(Color.GOLD.cpy());
                }
            }
        }


    }
}
