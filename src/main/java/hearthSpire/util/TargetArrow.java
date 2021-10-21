package hearthSpire.util;


import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class TargetArrow {
    private static final Logger logger = LogManager.getLogger(TargetArrow.class);

    public static boolean isActive;

    public static Vector2 from;

    private static AbstractCreature source;
    public static boolean canBePlayer;

    private static AbstractCreature hoveredCreature;
    private static Hitbox hoveredHitbox;
    private static Vector2 controlPoint;
    private static Vector2[] points = new Vector2[20];
    private static float arrowScaleTimer = 0.0F;

    private static ArrayList<TargetArrowSubscriber> subscribers = new ArrayList<>();

    public static void register(TargetArrowSubscriber sub) {
        boolean dupe = false;

        for (TargetArrowSubscriber subscriber : subscribers) {
            if (subscriber.getClass() == sub.getClass()) {
                dupe = true;
                break;
            }
        }

        if (!dupe)
            subscribers.add(sub);
    }

    public static void unsubscribe(TargetArrowSubscriber sub) {
        boolean hasThis = false;

        for (TargetArrowSubscriber subscriber : subscribers) {
            if (subscriber.getClass() == sub.getClass()) {
                hasThis = true;
                break;
            }
        }

        if (hasThis)
            subscribers.remove(sub);
        else
            logger.info(sub.toString() + " didn't subscribe.");
    }

    public static void open(AbstractCreature _source, boolean _canBePlayer) {
        source = _source;
        from = new Vector2(source.drawX, source.drawY);
        canBePlayer = _canBePlayer;
        isActive = true;
        GameCursor.hidden = true;
        for (int i = 0; i < points.length; i++) points[i] = new Vector2();
    }

    public static void open(Vector2 s, boolean _canBePlayer) {
        source = null;
        from = s;
        canBePlayer = _canBePlayer;
        isActive = true;
        GameCursor.hidden = true;
        for (int i = 0; i < points.length; i++) points[i] = new Vector2();
    }

    public static void close() {
        isActive = false;
        for (TargetArrowSubscriber subscriber : subscribers) {
            subscriber.end();
        }
    }

    private static void use(AbstractCreature source, AbstractCreature target) {
        for (TargetArrowSubscriber subscriber : subscribers) {
            subscriber.receiveTargetCreature(source, target);
        }
    }

    public static void update() {
        if (isActive) {
         //   if (InputHelper.justClickedRight || CInputActionSet.cancel.isJustPressed()) {
          //      CInputActionSet.cancel.unpress();
          //      close();
          //      GameCursor.hidden = false;
           // }

            hoveredCreature = null;
            hoveredHitbox = null;

            if (canBePlayer) {
                if (AbstractDungeon.player.hb.hovered)
                    hoveredCreature = AbstractDungeon.player;
            }
            if (hoveredCreature == null) {
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m.hb.hovered && !m.isDying) {
                        hoveredCreature = m;
                        break;
                    }
                }
            }

            if (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) {
                InputHelper.justClickedLeft = false;
                CInputActionSet.select.unpress();
                if (hoveredCreature != null) {
                    if (AbstractDungeon.player.hasPower("Surrounded")) {
                        AbstractDungeon.player.flipHorizontal = hoveredCreature.drawX < AbstractDungeon.player.drawX;
                    }
                    use(source, hoveredCreature);
                    if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                        AbstractDungeon.actionManager.addToBottom(new HandCheckAction());
                    }
                    close();
                    GameCursor.hidden = false;
                }

            }
        }
    }


    public static void render(SpriteBatch sb) {
        if (isActive) {
            if (hoveredCreature != null) {
                hoveredCreature.renderReticle(sb);
            }
            if (hoveredHitbox != null) {
                AbstractDungeon.player.renderReticle(sb, hoveredHitbox);
            }

            float x = (float) InputHelper.mX;
            float y = (float) InputHelper.mY;
            controlPoint = new Vector2(x - (x - from.x) / 4.0F, y + (y - from.y - 40.0F * Settings.scale) / 2.0F);
            float arrowScale;

            if (hoveredCreature == null) {
                arrowScale = Settings.scale;
                arrowScaleTimer = 0.0F;
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
            } else {
                arrowScaleTimer += Gdx.graphics.getDeltaTime();
                if (arrowScaleTimer > 1.0F) {
                    arrowScaleTimer = 1.0F;
                }
                arrowScale = Interpolation.elasticOut.apply(Settings.scale, Settings.scale * 1.2F, arrowScaleTimer);
                sb.setColor(new Color(1.0F, 0.2F, 0.3F, 1.0F));
            }

            Vector2 tmp = new Vector2(controlPoint.x - x, controlPoint.y - y);
            tmp.nor();
            drawCurvedLine(sb, new Vector2(from.x, from.y - 40.0F * Settings.scale), new Vector2(x, y), controlPoint);
            sb.draw(ImageMaster.TARGET_UI_ARROW, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, arrowScale, arrowScale, tmp.angle() + 90.0F, 0, 0, 256, 256, false, false);
        }
    }

    private static void drawCurvedLine(SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control) {
        float radius = 7.0F * Settings.scale;

        for (int i = 0; i < points.length - 1; ++i) {
            points[i] = Bezier.quadratic(points[i], (float) i / 20.0F, start, control, end, new Vector2());
            radius += 0.4F * Settings.scale;
            Vector2 tmp;
            float angle;
            if (i != 0) {
                tmp = new Vector2(points[i - 1].x - points[i].x, points[i - 1].y - points[i].y);
                angle = tmp.nor().angle() + 90.0F;
            } else {
                tmp = new Vector2(controlPoint.x - points[i].x, controlPoint.y - points[i].y);
                angle = tmp.nor().angle() + 270.0F;
            }

            sb.draw(ImageMaster.TARGET_UI_CIRCLE, points[i].x - 64.0F, points[i].y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, radius / 18.0F, radius / 18.0F, angle, 0, 0, 128, 128, false, false);
        }

    }

    public interface TargetArrowSubscriber extends ISubscriber {
        void receiveTargetCreature(AbstractCreature source, AbstractCreature target);

        void end();
    }

}
