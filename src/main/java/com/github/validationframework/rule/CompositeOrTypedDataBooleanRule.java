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

package com.github.validationframework.rule;

/**
 * Composite rule checking data of a known specific type using sub-rules, and returning a boolean as an aggregation of
 * the boolean results from its sub-rules.<br>The aggregation is basically an OR operation: the result will be true if
 * the result of  at least one sub-rule is also true.<br>If there are no sub-rules, the default result will be false.
 *
 * @param <D> Type of data to be validated.<br>It can be, for instance, the type of data handled by a component, or the
 * type of the component itself.
 *
 * @see AbstractTypedDataRule
 * @see CompositeAndTypedDataBooleanRule
 */
public class CompositeOrTypedDataBooleanRule<D> extends AbstractTypedDataRule<D, Boolean> {

	/**
	 * @see AbstractTypedDataRule#AbstractTypedDataRule()
	 */
	public CompositeOrTypedDataBooleanRule() {
		super();
	}

	/**
	 * @see AbstractTypedDataRule#AbstractTypedDataRule(TypedDataRule[])
	 */
	public CompositeOrTypedDataBooleanRule(final TypedDataRule<D, Boolean>... rules) {
		super(rules);
	}

	/**
	 * @see AbstractTypedDataRule#validate(Object)
	 */
	@Override
	public Boolean validate(final D data) {
		Boolean result = false;

		for (final TypedDataRule<D, Boolean> rule : rules) {
			result |= rule.validate(data);
			if (result) {
				break;
			}
		}

		return result;
	}
}
