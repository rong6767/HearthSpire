package hearthSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DefectsSchemeAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int count = 0;

    public DefectsSchemeAction(AbstractPlayer p, int i) {
        this.p = p;
        this.count = i;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasOrb()) {
            if (count > 0) {
                for (int i = 0; i < count - 1; ++i) {
                    this.addToBot(new EvokeWithoutRemovingOrbAction(1));
                }
                this.addToBot(new AnimateOrbAction(1));
                this.addToBot(new EvokeOrbAction(1));
            }
        }
        this.isDone = true;
    }
}