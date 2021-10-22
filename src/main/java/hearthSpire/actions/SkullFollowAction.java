package hearthSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.Iterator;

public class SkullFollowAction extends AbstractGameAction {
    public SkullFollowAction() {
        this.duration = 0.001F;
    }

    public void update() {
        Iterator var1 = DrawCardAction.drawnCards.iterator();

        while (var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
            if (c.costForTurn != 0 && !c.freeToPlayOnce && c.cost >= 0) {
                c.costForTurn = 0;
            }
        }
        isDone = true;
    }
}
