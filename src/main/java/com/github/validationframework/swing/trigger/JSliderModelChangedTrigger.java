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
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSliderModelChangedTrigger extends AbstractTrigger {

	private class SourceAdapter implements ChangeListener {

		@Override
		public void stateChanged(final ChangeEvent e) {
			if ((e.getSource() instanceof JSlider) && !(((JSlider) e.getSource()).getValueIsAdjusting())) {
				fireTriggerEvent(new TriggerEvent(source));
			}
		}
	}

	private JSlider source = null;

	private final ChangeListener sourceAdapter = new SourceAdapter();

	public JSliderModelChangedTrigger(final JSlider source) {
		super();
		this.source = source;
		source.addChangeListener(sourceAdapter);
	}

	/**
	 * @see AbstractTrigger#dispose()
	 */
	@Override
	public void dispose() {
		source.removeChangeListener(sourceAdapter);
		source = null;
	}
}
