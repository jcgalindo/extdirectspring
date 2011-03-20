/**
 * Copyright 2010-2011 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.ralscha.extdirectspring.filter;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumericFilterTest {

	@Test
	public void testNumeric() {
		NumericFilter filter = new NumericFilter("field", 42, Comparison.GREATER_THAN);
		assertEquals(42, filter.getValue());
		assertEquals("field", filter.getField());
		assertEquals("NumericFilter [value=42, comparison=GREATER_THAN, getField()=field]", filter.toString());

		filter = new NumericFilter("xy", 23, Comparison.EQUAL);
		assertEquals(23, filter.getValue());
		assertEquals("xy", filter.getField());
		assertEquals("NumericFilter [value=23, comparison=EQUAL, getField()=xy]", filter.toString());
	}
}
