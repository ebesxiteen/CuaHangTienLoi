package org.netbeans.lib.awtextra;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AbsoluteLayout implements LayoutManager2, Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<Component, AbsoluteConstraints> constraints = new HashMap<>();

    @Override
    public void addLayoutComponent(String name, Component comp) {
        addLayoutComponent(comp, null);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraint) {
        if (constraint instanceof AbsoluteConstraints absoluteConstraints) {
            constraints.put(comp, absoluteConstraints);
        } else {
            constraints.put(comp, new AbsoluteConstraints(0, 0));
        }
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        constraints.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return layoutSize(parent);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return layoutSize(parent);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    @Override
    public void layoutContainer(Container parent) {
        for (Component component : parent.getComponents()) {
            AbsoluteConstraints absoluteConstraints = constraints.get(component);
            if (absoluteConstraints == null) {
                continue;
            }

            Dimension preferredSize = component.getPreferredSize();
            int width = absoluteConstraints.width >= 0 ? absoluteConstraints.width : preferredSize.width;
            int height = absoluteConstraints.height >= 0 ? absoluteConstraints.height : preferredSize.height;

            component.setBounds(new Rectangle(absoluteConstraints.x, absoluteConstraints.y, width, height));
        }
    }

    private Dimension layoutSize(Container parent) {
        int maxWidth = 0;
        int maxHeight = 0;

        for (Component component : parent.getComponents()) {
            AbsoluteConstraints absoluteConstraints = constraints.get(component);
            if (absoluteConstraints == null) {
                continue;
            }

            Dimension preferredSize = component.getPreferredSize();
            int width = absoluteConstraints.width >= 0 ? absoluteConstraints.width : preferredSize.width;
            int height = absoluteConstraints.height >= 0 ? absoluteConstraints.height : preferredSize.height;

            maxWidth = Math.max(maxWidth, absoluteConstraints.x + width);
            maxHeight = Math.max(maxHeight, absoluteConstraints.y + height);
        }

        return new Dimension(maxWidth, maxHeight);
    }
}