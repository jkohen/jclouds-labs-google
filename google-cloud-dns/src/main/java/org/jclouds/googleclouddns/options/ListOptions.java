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
 * @author Javier Kohen
 */
public class ListOptions extends BaseHttpRequestOptions {

   /**
    * Optional. Sets Maximum count of results to be returned. Maximum and default value is 100. Acceptable items are 0 to
    * 100, inclusive. (Default: 100)
    */
   public ListOptions maxResults(Integer maxResults) {
      this.queryParameters.put("maxResults", checkNotNull(maxResults, "maxResults") + "");
      return this;
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
