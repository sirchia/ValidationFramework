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

package com.github.validationframework.swing.trigger;

import com.github.validationframework.api.trigger.AbstractTrigger;
import com.github.validationframework.api.trigger.TriggerEvent;
import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class JComboBoxOpenedTrigger extends AbstractTrigger {

	private class SourceAdapter implements PopupMenuListener {

		@Override
		public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
			fireTriggerEvent(new TriggerEvent(source));
		}

		@Override
		public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
			// Nothing to be done
		}

		@Override
		public void popupMenuCanceled(final PopupMenuEvent e) {
			// Nothing to be done
		}
	}

	private JComboBox source = null;

	private final PopupMenuListener sourceAdapter = new SourceAdapter();

	public JComboBoxOpenedTrigger(final JComboBox source) {
		super();
		this.source = source;
		source.addPopupMenuListener(sourceAdapter);
	}

	/**
	 * @see AbstractTrigger#dispose()
	 */
	@Override
	public void dispose() {
		source.removePopupMenuListener(sourceAdapter);
		source = null;
	}
}
