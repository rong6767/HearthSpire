package hearthSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class ReduceCertainCostAction extends AbstractGameAction {
    int reducedCost = 0;
    private static final Logger logger = LogManager.getLogger(DrawCardAction.class.getName());
    public ReduceCertainCostAction(int reducedCost) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.reducedCost = reducedCost;
    }

    public void update() {
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();
        logger.info("reduce cost:"+reducedCost);
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if(c.cost>=reducedCost){
                c.cost = c.cost - reducedCost;
                c.costForTurn = c.cost;
                c.isCostModified = true;
            }
            else if (c.cost>=0&&c.cost<reducedCost){
                c.cost = 0;
                c.costForTurn = c.cost;
                c.isCostModified = true;
            }
        }

        this.isDone = true;
    }
}