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

package com.github.validationframework.base.validator;

import com.github.validationframework.api.rule.TypedDataRule;

/**
 * Simple validator using boolean results and aggregating all results from the rules into a single result using the OR
 * operation.
 *
 * @see TypedDataSimpleValidator
 * @see AndTypedDataSimpleValidator
 */
public class OrTypedDataSimpleValidator<D> extends TypedDataSimpleValidator<D, Boolean> {

	/**
	 * Checks the specified data against all the rules and aggregates all the boolean results to one single result using
	 * the OR operation.<br>If there are no rules, the default result will be false.
	 *
	 * @param data Data to be validated against all rules.
	 *
	 * @see TypedDataSimpleValidator#processData(Object)
	 */
	@Override
	protected void processData(final D data) {
		boolean result = false;

		// Check data against all rules
		for (final TypedDataRule<D, Boolean> rule : rules) {
			result |= rule.validate(data);
		}

		// Process overall result
		processResult(result);
	}
}
