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
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayRandomCardsAction extends AbstractGameAction {
    private boolean exhaustCards;
    private int times;
    public static CardGroup divColorlessCardPool;
    public static CardGroup divCurseCardPool;
    public static CardGroup divCommonCardPool;
    public static CardGroup divUncommonCardPool;
    public static CardGroup divRareCardPool;

    public PlayRandomCardsAction(AbstractCreature target, boolean exhausts, int times) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
        this.times = times;
    }

    public static void initializeCardPools() {
        long startTime = System.currentTimeMillis();
        ArrayList<AbstractCard> tmpPool = new ArrayList();
        CardLibrary.addRedCards(tmpPool);
        CardLibrary.addGreenCards(tmpPool);
        CardLibrary.addBlueCards(tmpPool);
        if (!UnlockTracker.isCharacterLocked("Watcher")) {
            CardLibrary.addPurpleCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Colorless Cards")) {
            CardLibrary.addColorlessCards(tmpPool);
        }

        CardLibrary.addColorlessCards(tmpPool);
        Iterator var4 = tmpPool.iterator();

        AbstractCard c;
        CardGroup commonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        CardGroup uncommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        CardGroup rareCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        CardGroup curseCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        while(var4.hasNext()) {
            c = (AbstractCard)var4.next();
            switch(c.rarity) {
                case COMMON:
                    commonCardPool.addToTop(c);
                    break;
                case UNCOMMON:
                    uncommonCardPool.addToTop(c);
                    break;
                case RARE:
                    rareCardPool.addToTop(c);
                    break;
                case CURSE:
                    curseCardPool.addToTop(c);
                    break;
                default:
            }
        }

        divColorlessCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        divCurseCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        divRareCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        divUncommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        divCommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);


        var4 = rareCardPool.group.iterator();

        while(var4.hasNext()) {
            c = (AbstractCard)var4.next();
            divRareCardPool.addToBottom(c);
        }

        var4 = uncommonCardPool.group.iterator();

        while(var4.hasNext()) {
            c = (AbstractCard)var4.next();
            divUncommonCardPool.addToBottom(c);
        }

        var4 = commonCardPool.group.iterator();

        while(var4.hasNext()) {
            c = (AbstractCard)var4.next();
            divCommonCardPool.addToBottom(c);
        }
    }

    public static AbstractCard returnTrulyDiverseRandomCardInCombat() {
        ArrayList<AbstractCard> list = new ArrayList();
        initializeCardPools();
        Iterator var1 = divCommonCardPool.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }

        var1 = divUncommonCardPool.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }

        var1 = divRareCardPool.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }

        return (AbstractCard)list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }

    
    public void update() {
        for(int i=0;i<times;i++) {
            AbstractCard card = null;
            boolean random = false;
            try {
                SpireConfig config = new SpireConfig("hearthSpire", "theDefaultConfig");
                config.load();
                random = config.getBool("enableTureRandom");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(random) {

                card = returnTrulyDiverseRandomCardInCombat().makeCopy();
            }
            else{
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

