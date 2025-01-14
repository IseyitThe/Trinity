package me.leon.trinity.gui.setting;

import me.leon.trinity.gui.IComponent;
import me.leon.trinity.gui.button.ButtonComponent;
import me.leon.trinity.hacks.client.ClickGUI;
import me.leon.trinity.setting.rewrite.BooleanSetting;
import me.leon.trinity.setting.rewrite.Setting;

import java.awt.*;

public class BooleanComponent extends ISetting<BooleanSetting> {
    public BooleanComponent(IComponent parent, ButtonComponent superParent, Setting set, int offset) {
        super(parent, superParent, set, offset);
    }

    @Override
    public void render(Point point) {
        drawBack(point, set.getName(), set.getValue());
    }

    @Override
    public void update(Point point) {
        subs.forEach(e -> e.update(point));
    }

    @Override
    public boolean buttonClick(int button, Point point) {
        if(onButton(point)) {
            switch (button) {
                case 1: {
                    open = !open;
                    return true;
                }
                case 0: {
                    set.setValue(!set.getValue());
                    return true;
                }
            }
        }
        for (ISetting<?> sub : subs) {
            if(sub.buttonClick(button, point)) return true;
        }
        return false;
    }

    @Override
    public boolean buttonRelease(int button, Point point) {
        for (ISetting<?> sub : subs) {
            if(sub.buttonRelease(button, point)) return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(int code) {
        return false;
    }

    @Override
    protected Color getColor(Point point, boolean enabled) {
        if(onButton(point)) {
            if(enabled) {
                return ClickGUI.enabledBooleanColor.getValue().brighter();
            } else {
                return ClickGUI.disabledBooleanColor.getValue().brighter();
            }
        } else {
            if(enabled) {
                return ClickGUI.enabledBooleanColor.getValue();
            } else {
                return ClickGUI.disabledBooleanColor.getValue();
            }
        }
    }
}
