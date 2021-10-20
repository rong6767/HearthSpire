package hearthSpire.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class TessAction extends AbstractGameAction {
    private boolean exhaustCards;
    private int times = 0 ;



    public TessAction(AbstractCreature target, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
        this.times = AbstractDungeon.actionManager.cardsPlayedThisCombat.size();
    }



    public void update() {
        Iterator var2 = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();
        while(var2.hasNext()) {
            AbstractCard card = (AbstractCard)var2.next();
            if(card.color != AbstractDungeon.player.getCardColor()) {
                card.exhaustOnUseOnce = this.exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = (float) Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                this.addToTop(new NewQueueCardAction(card, this.target, false, true));
                this.addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
        }
        this.isDone = true;
    }

}

