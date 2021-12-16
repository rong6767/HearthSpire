package hearthSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.Iterator;

public class DoomPactAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int damage;

    public DoomPactAction(AbstractPlayer p) {
        this.p = p;
    }

    @Override
    public void update() {
        CardGroup drawPile = p.drawPile;
        Iterator<AbstractCard> iterator = drawPile.group.iterator();
        damage = drawPile.group.size() * 5;
        this.addToBot(new DamageAllEnemiesAction(p, this.damage, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
        while (iterator.hasNext()) {
            this.addToBot(new ExhaustSpecificCardAction(iterator.next(), p.drawPile));
        }
        this.isDone = true;
    }
}
