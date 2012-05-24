package com.github.validationframework.decoration.swing;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.github.validationframework.decoration.swing.utils.Anchor;
import com.github.validationframework.decoration.swing.utils.DualAnchor;

public class IconDecorator extends AbstractDecorator {

	private Icon icon = null;

	private static final DualAnchor DEFAULT_DUAL_ANCHOR_WITH_OWNER = new DualAnchor(Anchor.BOTTOM_LEFT, Anchor.CENTER);

	public IconDecorator(JComponent owner) {
		this(owner, null);
	}

	public IconDecorator(JComponent owner, Icon icon) {
		super(owner, DEFAULT_DUAL_ANCHOR_WITH_OWNER);
		this.icon = icon;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
		followOwner();
	}

	@Override
	protected int getWidth() {
		int width = 0;
		if (icon != null) {
			width = icon.getIconWidth();
		}
		return width;
	}

	@Override
	protected int getHeight() {
		int height = 0;
		if (icon != null) {
			height = icon.getIconHeight();
		}
		return height;
	}

	@Override
	public void paint(Graphics g) {
		if (isVisible() && (icon != null)) {
			icon.paintIcon(decorationHolder, g, 0, 0);
		}
	}
}