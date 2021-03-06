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

import com.github.validationframework.api.rule.UntypedDataRule;

/**
 * Composite rule performing validation using sub-rules, and returning a boolean as an aggregation of the boolean
 * results from its sub-rules.<br>The aggregation is basically an AND operation: the result will be true if the results
 * of sub-rules are also true.<br>If there are no sub-rules, the default result will be true.
 *
 * @see AbstractCompositeUntypedDataRule
 * @see OrCompositeUntypedDataBooleanRule
 */
public class AndCompositeUntypedDataBooleanRule extends AbstractCompositeUntypedDataRule<Boolean> {

	/**
	 * @see AbstractCompositeUntypedDataRule#AbstractCompositeUntypedDataRule()
	 */
	public AndCompositeUntypedDataBooleanRule() {
		super();
	}

	/**
	 * @see AbstractCompositeUntypedDataRule#AbstractCompositeUntypedDataRule(com.github.validationframework.api.rule.UntypedDataRule[])
	 */
	public AndCompositeUntypedDataBooleanRule(final UntypedDataRule<Boolean>... rules) {
		super(rules);
	}

	/**
	 * @see AbstractCompositeUntypedDataRule#validate()
	 */
	@Override
	public Boolean validate() {
		Boolean result = true;

		for (final UntypedDataRule<Boolean> rule : rules) {
			result &= rule.validate();
			if (!result) {
				break;
			}
		}

		return result;
	}
}
