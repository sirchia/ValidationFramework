/*
 * Copyright (c) 2012, Patrick Moawad
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.validationframework.demo.swing;

import com.github.validationframework.api.rule.TypedDataRule;
import com.github.validationframework.base.rule.string.StringRegexRule;
import com.github.validationframework.base.validator.AndTypedDataSimpleValidator;
import com.github.validationframework.base.validator.TypedDataSimpleValidator;
import com.github.validationframework.swing.dataprovider.JFormattedTextFieldTextProvider;
import com.github.validationframework.swing.dataprovider.JTextFieldTextProvider;
import com.github.validationframework.swing.resulthandler.AbstractColorFeedBack;
import com.github.validationframework.swing.resulthandler.AbstractIconFeedBack;
import com.github.validationframework.swing.resulthandler.AbstractStickerFeedBack;
import com.github.validationframework.swing.resulthandler.BooleanIconTipFeedBack;
import com.github.validationframework.swing.resulthandler.ComponentEnablingBooleanResultHandler;
import com.github.validationframework.swing.rule.JFormattedTextFieldFormatterRule;
import com.github.validationframework.swing.trigger.JFormattedTextFieldDocumentChangedTrigger;
import com.github.validationframework.swing.trigger.JTextFieldDocumentChangedTrigger;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.NumberFormatter;
import net.miginfocom.swing.MigLayout;

public class SimpleDemoApp extends JFrame {

	/**
	 * UIResource in case look-and-feel changes while result is visible. The new look-and-feel is allowed to replace it
	 * colors, otherwise, we may introduce permanent inconsistencies.
	 */
	private static final Color COLOR_NOK_EMPTY = new ColorUIResource(new Color(226, 125, 125, 127));
	private static final Color COLOR_NOK_TOO_LONG = new ColorUIResource(new Color(226, 125, 125));

	private enum InputFieldResult {

		OK("", null, null, null),
		NOK_EMPTY("Should not be empty", "/images/defaults/warning.png", null, COLOR_NOK_EMPTY),
		NOK_TOO_LONG("Cannot be more than 4 characters", "/images/defaults/invalid.png", COLOR_NOK_TOO_LONG, null);

		private final String text;
		private Icon icon;
		private final Color foreground;
		private final Color background;

		InputFieldResult(final String text, final String iconName, final Color foreground, final Color background) {
			this.text = text;
			this.foreground = foreground;
			this.background = background;

			// Error icon
			if ((iconName != null) && !iconName.isEmpty()) {
				final InputStream inputStream = getClass().getResourceAsStream(iconName);
				try {
					final BufferedImage image = ImageIO.read(inputStream);
					icon = new ImageIcon(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public Icon getIcon() {
			return icon;
		}

		public Color getForeground() {
			return foreground;
		}

		public Color getBackground() {
			return background;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	private class InputFieldRule implements TypedDataRule<String, InputFieldResult> {

		@Override
		public InputFieldResult validate(final String input) {
			InputFieldResult result = InputFieldResult.OK;

			if ((input == null) || (input.isEmpty())) {
				result = InputFieldResult.NOK_EMPTY;
			} else if (input.length() >= 5) {
				result = InputFieldResult.NOK_TOO_LONG;
			}

			return result;
		}
	}

	private class InputFieldToolTipFeedBack extends AbstractStickerFeedBack<InputFieldResult> {

		public InputFieldToolTipFeedBack(final JComponent owner) {
			super(owner);
		}

		@Override
		public void handleResult(final InputFieldResult result) {
			setToolTipText(result.toString());
			switch (result) {
				case OK:
					hideToolTip();
					break;
				default:
					showToolTip();
			}
		}
	}

	private class InputFieldColorFeedBack extends AbstractColorFeedBack<InputFieldResult> {

		public InputFieldColorFeedBack(final JComponent owner) {
			super(owner);
		}

		@Override
		public void handleResult(final InputFieldResult result) {
			setForeground(result.getForeground());
			setBackground(result.getBackground());
			switch (result) {
				case OK:
					hideColors();
					break;
				default:
					showColors();
			}
		}
	}

	private class InputFieldIconFeedBack extends AbstractIconFeedBack<InputFieldResult> {

		public InputFieldIconFeedBack(final JComponent owner) {
			super(owner);
		}

		@Override
		public void handleResult(final InputFieldResult result) {
			setIcon(result.getIcon());
			switch (result) {
				case OK:
					hideIconTip();
					break;
				default:
					showIconTip();
			}
		}
	}

	/**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = -2039502440268195814L;

	/**
	 * Default constructor.
	 */
	public SimpleDemoApp() {
		super();
		init();
	}

	/**
	 * Initializes the frame by creating its contents.
	 */
	private void init() {
		setTitle("Validation Framework Test");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Create content pane
		final JPanel contentPane = new JPanel(
				new MigLayout("fill, wrap 2", "[]related[grow]", "[]related[]related[]related[]unrelated[]"));
		final JScrollPane contentWrapper = new JScrollPane(contentPane);
		contentWrapper.setBorder(new CompoundBorder(new EmptyBorder(30, 30, 30, 30), contentWrapper.getBorder()));
		setContentPane(contentWrapper);

		// Input fields
		contentPane.add(new JLabel("Tooltip:"));
		contentPane.add(createInputField1(), "growx");
		contentPane.add(new JLabel("Color:"));
		contentPane.add(createInputField2(), "growx");
		contentPane.add(new JLabel("Icon:"));
		contentPane.add(createInputField3(), "growx");
		contentPane.add(new JLabel("Icon tip:"));
		final JButton applyButton = new JButton("Apply");
		contentPane.add(createInputField4(applyButton), "growx");

		// Apply button
		contentPane.add(applyButton, "growx, span");

		// Set size
		pack();
		final Dimension size = getSize();
		size.width += 100;
//		setMinimumSize(size);

		// Set location
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 3);
	}

	private Component createInputField1() {
		final JTextField textField = new JTextField();

		final TypedDataSimpleValidator<String, InputFieldResult> validator1 =
				new TypedDataSimpleValidator<String, InputFieldResult>();
		validator1.addTrigger(new JTextFieldDocumentChangedTrigger(textField));
		validator1.addDataProvider(new JTextFieldTextProvider(textField));
		validator1.addRule(new InputFieldRule());
		validator1.addResultHandler(new InputFieldToolTipFeedBack(textField));

		return textField;
	}

	private Component createInputField2() {
		final JTextField textField = new JTextField();

		final TypedDataSimpleValidator<String, InputFieldResult> validator2 =
				new TypedDataSimpleValidator<String, InputFieldResult>();
		validator2.addTrigger(new JTextFieldDocumentChangedTrigger(textField));
		validator2.addDataProvider(new JTextFieldTextProvider(textField));
		validator2.addRule(new InputFieldRule());
		validator2.addResultHandler(new InputFieldColorFeedBack(textField));

		return textField;
	}

	private Component createInputField3() {
		final JTextField textField = new JTextField();

		final TypedDataSimpleValidator<String, InputFieldResult> validator3 =
				new TypedDataSimpleValidator<String, InputFieldResult>();
		validator3.addTrigger(new JTextFieldDocumentChangedTrigger(textField));
		validator3.addDataProvider(new JTextFieldTextProvider(textField));
		validator3.addRule(new InputFieldRule());
		validator3.addResultHandler(new InputFieldIconFeedBack(textField));

		return textField;
	}

	private Component createInputField4(final JButton applyButton) {
		final NumberFormat courseFormat = NumberFormat.getIntegerInstance();
		courseFormat.setMinimumIntegerDigits(3);
		courseFormat.setMaximumIntegerDigits(4);
		courseFormat.setMaximumFractionDigits(0);
		final NumberFormatter courseFormatter = new NumberFormatter(courseFormat);
		courseFormatter.setMinimum(0.0);
		courseFormatter.setMaximum(359.0);
		final JFormattedTextField formattedTextField = new JFormattedTextField(courseFormatter);

		final AndTypedDataSimpleValidator<String> validator4 = new AndTypedDataSimpleValidator<String>();
		validator4.addTrigger(new JFormattedTextFieldDocumentChangedTrigger(formattedTextField));
		validator4.addDataProvider(new JFormattedTextFieldTextProvider(formattedTextField));
		validator4.addRule(new JFormattedTextFieldFormatterRule(formattedTextField));
		validator4.addRule(new StringRegexRule("^[0-9]{1,3}$"));
		validator4.addResultHandler(
				new BooleanIconTipFeedBack(formattedTextField, null, null, BooleanIconTipFeedBack.DEFAULT_INVALID_ICON,
						"Angle should be between 000 and 359"));
		validator4.addResultHandler(new ComponentEnablingBooleanResultHandler(applyButton));

//		final TypedDataSimpleValidator<Number, Boolean> validator4 = new TypedDataSimpleValidator<Number, Boolean>();
//		validator4.addTrigger(new JFormattedTextFieldDocumentChangedTrigger(formattedTextField));
//		validator4.addDataProvider(new JFormattedTextFieldNumberValueProvider(formattedTextField));
//		validator4.addRule(new AndCompositeTypedDataBooleanRule<Number>(new NumberGreaterThanOrEqualToRule(0.0),
//				new NumberLessThanRule(360.0)));
//		validator4.addResultHandler(
//				new BooleanIconTipFeedBack(formattedTextField, null, null, BooleanIconTipFeedBack.DEFAULT_INVALID_ICON,
//						"Angle should be between 000 and 359"));

		return formattedTextField;
	}

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				// Set look-and-feel
				try {
					for (final UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (UnsupportedLookAndFeelException e) {
					// handle exception
				} catch (ClassNotFoundException e) {
					// handle exception
				} catch (InstantiationException e) {
					// handle exception
				} catch (IllegalAccessException e) {
					// handle exception
				}

				// Show window
				new SimpleDemoApp().setVisible(true);
			}
		});
	}
}
