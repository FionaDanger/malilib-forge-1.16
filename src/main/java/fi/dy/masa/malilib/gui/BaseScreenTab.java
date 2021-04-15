package fi.dy.masa.malilib.gui;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiScreen;
import fi.dy.masa.malilib.gui.widget.button.ButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.malilib.util.data.ModInfo;

public class BaseScreenTab implements ScreenTab
{
    protected final String name;
    protected final String translationKey;
    protected final Function<GuiScreen, BaseScreen> screenFactory;
    protected final Function<BaseTabbedScreen, ButtonActionListener> listenerFactory;
    protected final Predicate<GuiScreen> screenChecker;
    @Nullable protected String hoverTextTranslationKey;

    public BaseScreenTab(ModInfo modInfo, String name, Predicate<GuiScreen> screenChecker,
                         Function<GuiScreen, BaseScreen> screenFactory)
    {
        this(name, modInfo.getModId() + ".label.screen_tab." + name, screenChecker, screenFactory);
    }

    public BaseScreenTab(String name, String translationKey, Predicate<GuiScreen> screenChecker,
                         Function<GuiScreen, BaseScreen> screenFactory)
    {
        this.name = name;
        this.translationKey = translationKey;
        this.screenChecker = screenChecker;
        this.screenFactory = screenFactory;
        this.listenerFactory = (scr) -> (btn, mbtn) -> this.openTab(scr);
    }

    public BaseScreenTab(String name, String translationKey, Predicate<GuiScreen> screenChecker,
                         Function<GuiScreen, BaseScreen> screenFactory,
                         Function<BaseTabbedScreen, ButtonActionListener> listenerFactory)
    {
        this.name = name;
        this.translationKey = translationKey;
        this.screenChecker = screenChecker;
        this.screenFactory = screenFactory;
        this.listenerFactory = listenerFactory;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getDisplayName()
    {
        return StringUtils.translate(this.translationKey);
    }

    @Nullable
    @Override
    public String getHoverText()
    {
        return this.hoverTextTranslationKey;
    }

    public BaseScreenTab setHoverText(@Nullable String hoverTextTranslationKey)
    {
        this.hoverTextTranslationKey = hoverTextTranslationKey;
        return this;
    }

    @Override
    public boolean canUseCurrentScreen(@Nullable GuiScreen currentScreen)
    {
        return this.screenChecker.test(currentScreen);
    }

    @Override
    public BaseScreen createScreen(GuiScreen currentScreen)
    {
        return this.screenFactory.apply(currentScreen);
    }

    @Override
    public ButtonActionListener getButtonActionListener(final BaseTabbedScreen currentScreen)
    {
        return this.listenerFactory.apply(currentScreen);
    }

    /**
     * Switches the active tab to this tab, or opens a new screen if the current screen
     * is not suitable for this tab.
     */
    public void openTab(@Nullable BaseTabbedScreen currentScreen)
    {
        if (currentScreen != null && this.canUseCurrentScreen(currentScreen))
        {
            currentScreen.switchToTab(this);
        }
        else
        {
            this.createAndOpenScreen(currentScreen);
        }
    }
}
