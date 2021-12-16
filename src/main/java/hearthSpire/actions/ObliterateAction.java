package hearthSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ObliterateAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;

    public ObliterateAction(AbstractPlayer p, AbstractMonster m) {
        this.p = p;
        this.m = m;
    }

    @Override
    public void update() {
        int currentHealth = m.currentHealth / 2;
        m.currentHealth = 0;
        this.m.healthBarUpdatedEvent();
        m.damage(new DamageInfo(null, 0, DamageInfo.DamageType.HP_LOSS));
        this.addToBot(new DamageAction(p, new DamageInfo(p, currentHealth, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        this.isDone = true;
    }
}
