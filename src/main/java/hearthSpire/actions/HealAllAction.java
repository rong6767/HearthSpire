package hearthSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class HealAllAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(HealAllAction.class.getName());

    public HealAllAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.duration = 0.01F;
        this.startDuration = this.duration;
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.actionType = ActionType.HEAL;
    }

    @Override
    public void update() {
        this.target.heal(amount);
        ArrayList<AbstractMonster> m = AbstractDungeon.getMonsters().monsters;
        for (AbstractMonster monster : m) {
            monster.heal(amount);
        }
        isDone = true;
    }
}
