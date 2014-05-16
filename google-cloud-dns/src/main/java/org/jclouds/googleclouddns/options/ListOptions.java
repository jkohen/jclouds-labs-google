/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.googleclouddns.options;

import static com.google.common.base.Preconditions.checkNotNull;

import org.jclouds.http.options.BaseHttpRequestOptions;

/**
 * Allows to optionally specify max results and other optional parameters for paginated list REST methods.
 * 
 * <p>Not all APIs support all list options. Refer to the API documentation for details.</p>
 *
 * @author Javier Kohen
 */
public class ListOptions extends BaseHttpRequestOptions {

   enum SortOrder {
      ASCENDING("ascending"),
      DESCENDING("descending");

      private String value;

      private SortOrder(String value) {
         this.value = value;
      }
      
      public String getValue() {
         return value;
      }
      static SortOrder fromString(String value) throws IllegalArgumentException {
         if (ASCENDING.getValue().equals(value)) {
            return ASCENDING;
         } else if (DESCENDING.getValue().equals(value)) {
            return DESCENDING;
         } else {
            throw new IllegalArgumentException("Unknown sort order: " + value);
         }
      }
   }
   
   /**
    * Optional. Sets Maximum count of results to be returned. Maximum and default value is 100. Acceptable items are 0 to
    * 100, inclusive. (Default: 100)
    */
   public ListOptions maxResults(Integer maxResults) {
      this.queryParameters.put("maxResults", checkNotNull(maxResults, "maxResults") + "");
      return this;
   }

   public Integer getMaxResults() {
      String maxresults = getFirstQueryOrNull("maxresults");
      return (maxresults != null) ? Integer.valueOf(maxresults) : null;
   }

   /**
    * Restricts the list to return only records with this fully qualified domain name.
    */
   public ListOptions name(String name) {
      this.queryParameters.put("name", checkNotNull(name, "name") + "");
      return this;
   }

   public String getName() {
      return getFirstQueryOrNull("name");
   }
   
   /**
    * Optional. Restricts the list to return only records of this type. If present, the name parameter must also be present.
    * See the <a href="https://developers.google.com/cloud-dns/what-is-cloud-dns#supported_record_types">full list of supported types</a>.
    */
   public ListOptions typeWithName(String type, String name) {
      this.queryParameters.put("type", checkNotNull(type, "type") + "");
      return name(name);
   }

   public String getType() {
      return getFirstQueryOrNull("type");
   }

   /**
    * Sorting order direction. The default is SortOrder.DESCENDING.
    */
   public ListOptions sortOrder(SortOrder sortOrder) {
      this.queryParameters.put("sortOrder", checkNotNull(sortOrder, "sortOrder").getValue() + "");
      return this;
   }

   public SortOrder getSortOrder() {
      try {
         return SortOrder.fromString(getFirstQueryOrNull("sortOrder"));
      } catch (IllegalArgumentException e) {
         return null;
      }
   }

   public static class Builder {

      /**
       * @see ListOptions#maxResults(Integer)
       */
      public ListOptions maxResults(Integer maxResults) {
         return new ListOptions().maxResults(maxResults);
      }
   }
}
