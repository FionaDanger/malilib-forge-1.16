package fi.dy.masa.malilib.gui.widget.list.entry;

import javax.annotation.Nullable;
import fi.dy.masa.malilib.action.ActionRegistry;
import fi.dy.masa.malilib.action.AliasAction;
import fi.dy.masa.malilib.gui.icon.DefaultIcons;
import fi.dy.masa.malilib.gui.widget.ScreenContext;
import fi.dy.masa.malilib.gui.widget.button.GenericButton;
import fi.dy.masa.malilib.gui.widget.list.DataListWidget;
import fi.dy.masa.malilib.render.text.StyledTextLine;
import fi.dy.masa.malilib.util.StyledTextUtils;
import fi.dy.masa.malilib.util.data.LeftRight;

public class AliasActionEntryWidget extends BaseDataListEntryWidget<AliasAction>
{
    protected final StyledTextLine nameText;
    protected final GenericButton removeButton;

    public AliasActionEntryWidget(int x, int y, int width, int height, int listIndex,
                                  int originalListIndex, @Nullable AliasAction data,
                                  @Nullable DataListWidget<? extends AliasAction> listWidget)
    {
        super(x, y, width, height, listIndex, originalListIndex, data, listWidget);

        this.removeButton = GenericButton.createIconOnly(0, 0, DefaultIcons.LIST_REMOVE_MINUS_9);
        this.removeButton.translateAndAddHoverStrings("malilib.gui.button.hover.list.remove");
        this.removeButton.setActionListener(this::removeAlias);

        this.renderHoverBackground = false;
        this.setHoveredBorderWidth(1);
        this.setHoveredBorderColor(0xFF00FF60);

        StyledTextLine fullName = StyledTextLine.of(data.getWidgetDisplayName());
        this.nameText = StyledTextUtils.clampStyledTextToMaxWidth(fullName, width - 20, LeftRight.RIGHT, " ...");

        this.getHoverInfoFactory().setStringListProvider("action_info", data::getHoverInfo);
    }

    @Override
    public void reAddSubWidgets()
    {
        super.reAddSubWidgets();

        this.addWidget(this.removeButton);
    }

    @Override
    public void updateSubWidgetsToGeometryChanges()
    {
        super.updateSubWidgetsToGeometryChanges();

        this.removeButton.setPosition(this.getRight() - 12, this.getY() + 2);
    }

    protected void removeAlias()
    {
        ActionRegistry.INSTANCE.removeAlias(this.data.getRegistryName());
        this.listWidget.refreshEntries();
    }

    @Override
    public void renderAt(int x, int y, float z, ScreenContext ctx)
    {
        super.renderAt(x, y, z, ctx);

        int ty = y + this.getHeight() / 2 - this.fontHeight / 2;
        this.renderTextLine(x + 4, ty, z + 0.1f, 0xFFFFFFFF, true, ctx, this.nameText);
    }
}
