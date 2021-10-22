package hearthSpire.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

import static basemod.BaseMod.logger;

public class DefileAction extends AbstractGameAction {
    AbstractPlayer p;
    int amt;

    public DefileAction(AbstractPlayer p, int amt) {
        this.duration = 0.001F;
        this.p = p;
        this.amt = amt;
    }

    public void defile() {
        MonsterGroup monsters = AbstractDungeon.getCurrRoom().monsters;
        monsters.update();
        int size = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            return;
        }
        for (int i = 0; i < size; ++i) {
            if (!((AbstractMonster) AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDeadOrEscaped()) {
                if (this.attackEffect == AttackEffect.POISON) {
                    ((AbstractMonster) AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.color.set(Color.CHARTREUSE);
                    ((AbstractMonster) AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                } else if (this.attackEffect == AttackEffect.FIRE) {
                    ((AbstractMonster) AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.color.set(Color.RED);
                    ((AbstractMonster) AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                }

                ((AbstractMonster) AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).damage(new DamageInfo(this.source, amt, this.damageType));
            }
        }
        for (int i = 0; i < size; i++) {
            AbstractMonster m = monsters.monsters.get(i);
            logger.info(m.currentHealth);
            if (m.currentBlock != 0 && m.currentBlock % 10 == 0) {
                logger.info("Defile: I found it!");
                defile();
            }
            if (m.currentBlock == 0 && m.currentHealth % 10 == 0 && m.currentHealth != 0) {
                logger.info("Defile: I found it!");
                defile();
            }
        }


    }

    public void update() {
        defile();

        this.isDone = true;
    }
}
