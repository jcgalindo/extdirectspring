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

package ch.ralscha.extdirectspring.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import ch.ralscha.extdirectspring.annotation.ExtDirectStoreReadMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResponse;

@Named
public class RemoteProvider2 {

  @ExtDirectStoreReadMethod
  public List<Row> method1() {
    return createRows();
  }

  @ExtDirectStoreReadMethod
  public List<Row> method2() {
    return null;
  }

  @ExtDirectStoreReadMethod
  public List<Row> method3(HttpServletResponse response, HttpServletRequest request, HttpSession session, Locale locale) {
    Assert.assertNotNull(response);
    Assert.assertNotNull(request);
    Assert.assertNotNull(session);
    Assert.assertEquals(Locale.ENGLISH, locale);

    return createRows();
  }

  @ExtDirectStoreReadMethod
  public ExtDirectStoreResponse<Row> method4(ExtDirectStoreReadRequest request) {
    return createExtDirectStoreResponse(request);
  }

  @ExtDirectStoreReadMethod
  public ExtDirectStoreResponse<Row> method5(ExtDirectStoreReadRequest request, Locale locale, @RequestParam(value = "id") int id) {
    Assert.assertEquals(10, id);
    Assert.assertEquals(Locale.ENGLISH, locale);
    return createExtDirectStoreResponse(request);
  }

  @ExtDirectStoreReadMethod
  public ExtDirectStoreResponse<Row> method6(@RequestParam(value = "id", defaultValue = "1") int id, HttpServletRequest servletRequest,
      ExtDirectStoreReadRequest request) {
    Assert.assertEquals(1, id);
    Assert.assertNotNull(servletRequest);
    return createExtDirectStoreResponse(request);
  }

  @ExtDirectStoreReadMethod
  public List<Row> method7(@RequestParam(value = "id", required = false) Integer id, String dummy) {
    Assert.assertNull(id);
    Assert.assertNull(dummy);
    return createRows();
  }

  private ExtDirectStoreResponse<Row> createExtDirectStoreResponse(ExtDirectStoreReadRequest request) {
    List<Row> rows = createRows();

    int totalSize = rows.size();

    if (request != null) {

      if (StringUtils.hasText(request.getSort())) {
        Assert.assertEquals("name", request.getSort());

        if (request.isAscendingSort()) {
          Collections.sort(rows);
        } else if (request.isDecendingSort()) {
          Collections.sort(rows, new Comparator<Row>() {

            @Override
            public int compare(Row o1, Row o2) {
              return o2.getName().compareTo(o1.getName());
            }
          });
        }
      }

      if (request.getStart() != null && request.getLimit() != null) {
        rows = rows.subList(request.getStart(), Math.min(totalSize, request.getStart() + request.getLimit()));
      }

    }

    return new ExtDirectStoreResponse<Row>(totalSize, rows);

  }

  private List<Row> createRows() {
    List<Row> rows = new ArrayList<Row>();
    for (int i = 0; i < 100; i += 2) {
      rows.add(new Row(i, "name: " + i, true, "" + (1000 + i)));
      rows.add(new Row(i + 1, "firstname: " + (i + 1), false, "" + (10 + i + 1)));
    }
    return rows;
  }

}