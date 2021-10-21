package hearthSpire.util;

import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class TargetArrowScreen implements TargetArrow.TargetArrowSubscriber {
    private static final Logger logger = LogManager.getLogger(TargetArrowScreen.class);

    public static TargetArrowScreen Inst = new TargetArrowScreen();

    public boolean isActive;
    private String tip;

    private float timer;

    private static ArrayList<TargetArrowScreenSubscriber> subscribers = new ArrayList<>();

    private TargetArrowScreen() {
        TargetArrow.register(this);
    }

    public static void register(TargetArrowScreenSubscriber sub) {
        boolean dupe = false;

        for (TargetArrowScreenSubscriber subscriber : subscribers) {
            if (subscriber == sub) {
                dupe = true;
                break;
            }
        }

        if (!dupe)
            subscribers.add(sub);
    }

    public void open(TargetArrowScreenSubscriber sub, String tip, boolean canBePlayer) {
        timer = 0.1F;
//      AbstractDungeon.player.cardInUse.target_x += 200.0F * Settings.scale;
        register(sub);
        TargetArrow.open(new Vector2(Settings.WIDTH / 2.0F, 0.0F), canBePlayer);
        this.isActive = true;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = Enum.TARGET_SCREEN;
        this.tip = tip;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.endTurnButton.disable();
    }

    private void reopen() {
        this.isActive = true;
        AbstractDungeon.screen = Enum.TARGET_SCREEN;
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.endTurnButton.disable();
    }

    public void close() {
        this.isActive = false;
        AbstractDungeon.overlayMenu.endTurnButton.enable();
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
        AbstractDungeon.isScreenUp = false;
        this.tip = null;
    }

    private void fakeClose() {
        AbstractDungeon.overlayMenu.endTurnButton.disable();
        AbstractDungeon.isScreenUp = false;
    }

//    public void close() {
//        TargetArrow.unsubscribe(this);
//        AbstractDungeon.player.hand = this.savedHand;
//        AbstractDungeon.overlayMenu.endTurnButton.enable();
//        AbstractDungeon.dynamicBanner.hide();
//        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
//        AbstractDungeon.isScreenUp = false;
//        TargetArrow.close();
//    }

    public void update() {
        if(timer > 0.0F) {
            timer -= Gdx.graphics.getDeltaTime();
        }else {
            this.fakeClose();
            if(this.isActive && !TargetArrow.isActive) {
                TargetArrow.close();
            }
        }

        TargetArrow.update();
        TargetArrow.from.x = AbstractDungeon.player.drawX;
        TargetArrow.from.y = AbstractDungeon.player.drawY;
        AbstractDungeon.currMapNode.room.update();

    }

    public void render(SpriteBatch sb) {
        TargetArrow.render(sb);
        if(this.isActive && tip != null) {
            FontHelper.renderDeckViewTip(sb, tip,96.0F * Settings.scale, Settings.CREAM_COLOR);
        }
    }

    @Override
    public void receiveTargetCreature(AbstractCreature source, AbstractCreature target) {
        for(TargetArrowScreenSubscriber subscriber : subscribers) {
            subscriber.receiveScreenTargetCreature(source, target);
        }
        subscribers.clear();
    }

    @Override
    public void end() {
        close();
        for(TargetArrowScreenSubscriber subscriber : subscribers) {
            subscriber.receiveEnd();
        }
        subscribers.clear();
    }

    public static class Enum {
        @SpireEnum
        static AbstractDungeon.CurrentScreen TARGET_SCREEN;

        public Enum() {
        }
    }

    public interface TargetArrowScreenSubscriber extends ISubscriber {
        void receiveScreenTargetCreature(AbstractCreature source, AbstractCreature target);

        void receiveEnd();
    }


    @SpirePatch(
            cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon",
            method = "openPreviousScreen"
    )
    public static class OpenPreviousScreen {
        public OpenPreviousScreen() {
        }

        public static void Postfix(AbstractDungeon.CurrentScreen s) {
            if (s == Enum.TARGET_SCREEN) {
                TargetArrowScreen.Inst.reopen();
            }

        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon",
            method = "render"
    )
    public static class Render {
        public Render() {
        }

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractDungeon __instance, SpriteBatch sb) {
            if (AbstractDungeon.screen == Enum.TARGET_SCREEN) {
                TargetArrowScreen.Inst.render(sb);
            }
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher("com.megacrit.cardcrawl.dungeons.AbstractDungeon", "screen");
                return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon",
            method = "update"
    )
    public static class Update {
        public Update() {
        }

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractDungeon __instance) {
            if (AbstractDungeon.screen == Enum.TARGET_SCREEN) {
                TargetArrowScreen.Inst.update();
            }

        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher("com.megacrit.cardcrawl.dungeons.AbstractDungeon", "screen");
                return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
            }
        }
    }
}
