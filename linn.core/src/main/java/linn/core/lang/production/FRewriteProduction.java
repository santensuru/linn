/**
 * Copyright (c) 2016 by Thomas Trojer <thomas@trojer.net>
 * LINN - A small L-System interpreter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package linn.core.lang.production;

import java.util.List;

import static com.google.common.base.Preconditions.*;

import linn.core.Linn;
import linn.core.execute.state.LinnTurtle;

/**
 * A special {@link RewriteProduction} that can be used if an
 * {@link FProduction} is either used as rule name itself (i.e. the letter "F"
 * is used as a rule name) or should simply be less
 * "dumb" and rewrite itself (i.e. pointing to any other rule by name).
 * <p>
 *
 * @author Thomas Trojer <thomas@trojer.net> -- Initial contribution
 *
 */
public class FRewriteProduction extends RewriteProduction {

	private FProduction fProduction;

	public FRewriteProduction(String ruleName, final FProduction fProduction, Linn linn) {
		super(ruleName, linn);
		checkNotNull(fProduction);
		this.fProduction = fProduction;
	}

	@Override
	public List<Production> execute(LinnTurtle state, ProductionParameter... parameters) {
		// fire and forget F production
		this.fProduction.execute(state, parameters);
		// just consider the rewrite result of this production
		return super.execute(state, parameters);
	}

	@Override
	public String getName() {
		char type = 'F';
		if (this.fProduction.jump) {
			type = 'f';
		}
		return type + "(" + this.fProduction.length + ", " + this.ruleName + ")";
	}
}
