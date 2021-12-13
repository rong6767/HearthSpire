package hearthSpire.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hearthSpire.DefaultMod;
import hearthSpire.cards.ArcanistDawngraspRarePower;
import hearthSpire.util.TextureLoader;

import java.util.HashMap;
import java.util.Map;

import static hearthSpire.DefaultMod.makePowerPath;

public class ReachthePortalRoomPower extends AbstractPower {
    public static final String POWER_ID = DefaultMod.makeID("ReachthePortalRoomPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private Map<String,Integer> orbs= new HashMap<>();

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ReachthePortalRoom_power32.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ReachthePortalRoom_power84.png"));

    public ReachthePortalRoomPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = 0;

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount.)

    @Override
    public void onChannel(AbstractOrb orb) {
        if(orb.ID.equals("Plasma")) {
            orbs.put("Plasma",1);
        }
        if(orb.ID.equals("Frost")) {
            orbs.put("Frost",1);
        }
        if(orb.ID.equals("Lightning")) {
            orbs.put("Lightning",1);
        }
        if (orb.ID.equals("Dark")) {
            orbs.put("Dark",1);
        }
        if(orbs.containsKey("Plasma") && orbs.get("Plasma") == 1 &&  orbs.containsKey("Frost") && orbs.get("Frost") == 1 && orbs.containsKey("Lightning") && orbs.get("Lightning") == 1 && orbs.containsKey("Dark")&& orbs.get("Dark") == 1) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            this.addToBot(new MakeTempCardInDrawPileAction(new ArcanistDawngraspRarePower(), 1, true, true));
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}