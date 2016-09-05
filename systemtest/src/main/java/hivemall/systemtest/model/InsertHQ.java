/*
 * Hivemall: Hive scalable Machine Learning Library
 *
 * Copyright (C) 2016 Makoto YUI
 * Copyright (C) 2013-2015 National Institute of Advanced Industrial Science and Technology (AIST)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hivemall.systemtest.model;

import hivemall.utils.lang.Preconditions;

import java.util.List;

public class InsertHQ extends TableHQ {
    public List<Object[]> data;
    public List<String> header;


    InsertHQ(String tableName, List<String> header, List<Object[]> data) {
        super(tableName);

        int l = 0;
        for (Object[] objs : data) {
            Preconditions.checkArgument(objs.length == header.size(),
                "l.%d: Mismatch between number of elements in row(%d) and length of header(%d)", l,
                objs.length, header.size());
            l++;
        }

        this.data = data;
        this.header = header;
    }


    public String getAsValuesFormat() {
        StringBuilder sb = new StringBuilder();
        for (Object[] row : data) {
            sb.append("(");
            for (Object val : row) {
                sb.append(serialize(val));
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("),");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public static String serialize(Object val) {
        // TODO array, map
        if (val instanceof String) {
            return "'" + String.valueOf(val) + "'";
        } else {
            return String.valueOf(val);
        }
    }
}
