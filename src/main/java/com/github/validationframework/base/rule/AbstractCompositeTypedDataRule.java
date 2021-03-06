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

package com.github.validationframework.base.rule;

import com.github.validationframework.api.rule.TypedDataRule;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCompositeTypedDataRule<D, R> implements TypedDataRule<D, R> {

	/**
	 * Typed data sub-rules to be checked.
	 */
	protected final List<TypedDataRule<D, R>> rules = new ArrayList<TypedDataRule<D, R>>();

	/**
	 * Default constructor.
	 */
	public AbstractCompositeTypedDataRule() {
		// Nothing to be done
	}

	/**
	 * Constructor specifying the sub-rule(s) to be added.
	 *
	 * @param rules Sub-rule(s) to be added.
	 *
	 * @see #addRule(TypedDataRule)
	 */
	public AbstractCompositeTypedDataRule(final TypedDataRule<D, R>... rules) {
		if (rules != null) {
			for (final TypedDataRule<D, R> rule : rules) {
				addRule(rule);
			}
		}
	}

	/**
	 * Adds the specified sub-rule to be checked.
	 *
	 * @param rule Sub-rule to be added.
	 */
	public void addRule(final TypedDataRule<D, R> rule) {
		rules.add(rule);
	}

	/**
	 * Removes the specified sub-rule to be checked.
	 *
	 * @param rule Sub-rule tobe removed
	 */
	public void removeRule(final TypedDataRule<D, R> rule) {
		rules.remove(rule);
	}
}
