package hearthSpire.actions;


import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class PlayRandomCardsAction extends AbstractGameAction {
    private boolean exhaustCards;
    private int times;


    public PlayRandomCardsAction(AbstractCreature target, boolean exhausts, int times) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
        this.times = times;
    }

    private AbstractCard.CardRarity randomRarity() {
        int pick = new Random().nextInt(5);
        return AbstractCard.CardRarity.values()[pick];
    }

    public static AbstractCard getAnyColorCard(AbstractCard.CardRarity rarity) {
        CardGroup anyCard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = CardLibrary.cards.entrySet().iterator();

        while (true) {
            Map.Entry c;
            do {
                do {
                    do {
                        do {
                            if (!var2.hasNext()) {
                                anyCard.shuffle(AbstractDungeon.cardRng);
                                return anyCard.getRandomCard(true, rarity).makeCopy();
                            }

                            c = (Map.Entry) var2.next();
                        } while (((AbstractCard) c.getValue()).rarity != rarity);
                    } while (((AbstractCard) c.getValue()).type == AbstractCard.CardType.CURSE);
                } while (((AbstractCard) c.getValue()).type == AbstractCard.CardType.STATUS);
            } while (UnlockTracker.isCardLocked((String) c.getKey()) && !Settings.treatEverythingAsUnlocked());

            anyCard.addToBottom((AbstractCard) c.getValue());
        }
    }

    public AbstractCard returnTrulyDiverseRandomCardInCombat() {
        return getAnyColorCard(randomRarity());
    }


    public void update() {
        for (int i = 0; i < times; i++) {
            AbstractCard card = null;
            boolean random = false;
            try {
                SpireConfig config = new SpireConfig("hearthSpire", "theDefaultConfig");
                config.load();
                random = config.getBool("enableTureRandom");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (random) {

                card = returnTrulyDiverseRandomCardInCombat().makeCopy();
            } else {
                card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
            }
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
        this.isDone = true;
    }

}

