package hearthSpire.cards;


import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hearthSpire.DefaultMod;

import static hearthSpire.DefaultMod.makeCardPath;

public class ScrollofWonderCard extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ScrollofWonderCard.class.getSimpleName());

    public static final String IMG = makeCardPath("ScrollofWonder.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.STATUS;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public ScrollofWonderCard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isInAutoplay =
        this.isEthereal = true;
        this.exhaust = true;
    }


    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        if (upgraded) {
            card.upgrade();
        }
        AbstractDungeon.getCurrRoom().souls.remove(card);
        card.exhaustOnUseOnce = true;
        AbstractDungeon.player.limbo.group.add(card);
        card.current_y = -200.0F * Settings.scale;
        card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = (float) Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        this.addToTop(new NewQueueCardAction(card, AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng), false, true));
        this.addToTop(new UnlimboAction(card));
        this.addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        this.addToTop(new DrawCardAction(1));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();

        }
    }


}
