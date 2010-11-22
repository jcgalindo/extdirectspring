/**
 * Copyright 2010 Ralph Schaer <ralphschaer@gmail.com>
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

package ch.ralscha.extdirectspring.itest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectResponse;
import ch.ralscha.extdirectspring.bean.ExtDirectResponseBuilder;

@Controller
@RequestMapping("/user")
public class UserController {

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "itest_user")
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public ExtDirectResponse updateUser(HttpServletRequest request, @Valid User user, BindingResult result) {
		ExtDirectResponseBuilder builder = new ExtDirectResponseBuilder(request);

		builder.addErrors(result);

		builder.addResultProperty("name", user.getName());
		builder.addResultProperty("age", user.getAge());
		return builder.build();
	}

}
