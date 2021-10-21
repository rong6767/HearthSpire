package hearthSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import hearthSpire.orbs.ClickableOrb;
import javassist.CtBehavior;

@SpirePatch(
        clz= AbstractPlayer.class,
        method="combatUpdate"
)
public class ClickableOrbUpdatePatch
{
    @SpireInsertPatch(
            locator=Locator.class,
            localvars={"o"}
    )
    public static void Insert(AbstractPlayer __instance, AbstractOrb o)
    {
        if (o instanceof ClickableOrb) {
            ((ClickableOrb) o).clickUpdate();
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractOrb.class, "update");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}