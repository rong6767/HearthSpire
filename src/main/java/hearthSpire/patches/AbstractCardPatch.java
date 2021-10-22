package hearthSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import hearthSpire.DefaultMod;

@SpirePatch(
        clz = AbstractCard.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
        method = "freeToPlay" // This is the name of the method we will be patching.
)
public class AbstractCardPatch {
    public static final String POWER_ID = DefaultMod.makeID("FreeCardPower");

    public static SpireReturn<Boolean> Prefix() {
        if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && AbstractDungeon.player.hasPower(POWER_ID)) {
            return SpireReturn.Return(true);
        } else {
            return SpireReturn.Continue();
        }
    }
}

