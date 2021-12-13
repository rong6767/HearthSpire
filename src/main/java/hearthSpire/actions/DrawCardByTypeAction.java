package hearthSpire.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class DrawCardByTypeAction extends AbstractGameAction {
    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(DrawCardAction.class.getName());
    public static ArrayList<AbstractCard> drawnCards = new ArrayList<>();
    private boolean clearDrawHistory;
    private AbstractGameAction followUpAction;
    private AbstractCard.CardType type;

    public DrawCardByTypeAction(AbstractCard.CardType type,int amount) {
        this.type = type;
        this.amount = amount;
        this.shuffleCheck = false;
        this.clearDrawHistory = true;
        this.followUpAction = null;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    public void update() {
        if (this.clearDrawHistory) {
            this.clearDrawHistory = false;
            drawnCards.clear();
        }
        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.endActionWithFollowUp();
        } else if (this.amount <= 0) {
            this.endActionWithFollowUp();
        } else {
            int deckSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            if (!SoulGroup.isActive()) {
                if (deckSize + discardSize == 0) {
                    this.endActionWithFollowUp();
                } else if (AbstractDungeon.player.hand.size() == 10) {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.endActionWithFollowUp();
                } else {
                    if (!this.shuffleCheck) {
                        int tmp;
                        if (this.amount + AbstractDungeon.player.hand.size() > 10) {
                            tmp = 10 - (this.amount + AbstractDungeon.player.hand.size());
                            this.amount += tmp;
                            AbstractDungeon.player.createHandIsFullDialog();
                        }

                        if (this.amount > deckSize) {
                            tmp = this.amount - deckSize;
                            this.addToTop(new DrawCardAction(tmp, this.followUpAction, false));
                            this.addToTop(new EmptyDeckShuffleAction());
                            if (deckSize != 0) {
                                this.addToTop(new DrawCardAction(deckSize, false));
                            }

                            this.amount = 0;
                            this.isDone = true;
                            return;
                        }

                        this.shuffleCheck = true;
                    }

                    this.duration -= Gdx.graphics.getDeltaTime();
                    if (this.amount != 0 && this.duration < 0.0F) {
                        if (Settings.FAST_MODE) {
                            this.duration = Settings.ACTION_DUR_XFAST;
                        } else {
                            this.duration = Settings.ACTION_DUR_FASTER;
                        }

                        --this.amount;
                        if (!AbstractDungeon.player.drawPile.isEmpty()) {
                            drawnCards.add(AbstractDungeon.player.drawPile.getRandomCard(type,false));
                            AbstractDungeon.player.draw();
                            AbstractDungeon.player.hand.refreshHandLayout();
                        } else {
                            logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                            this.endActionWithFollowUp();
                        }

                        if (this.amount == 0) {
                            this.endActionWithFollowUp();
                        }
                    }

                }
            }
        }
    }

    private void endActionWithFollowUp() {
        this.isDone = true;
        if (this.followUpAction != null) {
            this.addToTop(this.followUpAction);
        }

    }
}