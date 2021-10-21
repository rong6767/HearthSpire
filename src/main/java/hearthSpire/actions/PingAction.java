package hearthSpire.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hearthSpire.util.TargetArrow;
import hearthSpire.util.TargetArrowScreen;

import static hearthSpire.DefaultMod.logger;

public class PingAction extends AbstractGameAction implements TargetArrowScreen.TargetArrowScreenSubscriber {

    private int amt;


    public PingAction( int amt) {
        this.amt = amt;
        this.duration = DEFAULT_DURATION;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if(this.duration == DEFAULT_DURATION) {
                TargetArrowScreen.Inst.open(this, "damage"+amt, true);

                this.tickDuration();
            }

            if(TargetArrowScreen.Inst.isActive && !TargetArrow.isActive) {
                this.isDone = true;
            }
        }
    }

    @Override
    public void receiveScreenTargetCreature(AbstractCreature source, AbstractCreature target) {
        logger.info(target);
        addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, amt)));

        this.isDone = true;
    }

    @Override
    public void receiveEnd() {
        this.isDone = true;
    }
}
