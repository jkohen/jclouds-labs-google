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
package org.jclouds.googleclouddns.functions.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Function;
import com.google.inject.TypeLiteral;

import org.jclouds.collect.IterableWithMarker;
import org.jclouds.googleclouddns.GoogleCloudDnsApi;
import org.jclouds.googleclouddns.domain.Change;
import org.jclouds.googleclouddns.domain.ListPage;
import org.jclouds.googleclouddns.options.ListOptions;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.json.Json;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Andrew Bayer
 * @author Javier Kohen
 */
@Singleton
public class ParseChanges extends ParseJson<ListPage<Change>> {

   @Inject
   public ParseChanges(Json json) {
      super(json, new TypeLiteral<ListPage<Change>>() {
      });
   }

   public static class ToPagedIterable extends BaseWithManagedZoneToPagedIterable<Change, ToPagedIterable> {

      private final GoogleCloudDnsApi api;

      @Inject
      protected ToPagedIterable(GoogleCloudDnsApi api) {
         this.api = checkNotNull(api, "api");
      }

      @Override
      protected Function<Object, IterableWithMarker<Change>> fetchNextPage(
            final String projectName, final String zoneName, final ListOptions options) {
         return new Function<Object, IterableWithMarker<Change>>() {

            @Override
            public IterableWithMarker<Change> apply(Object input) {
               return api.getChangeApiForProject(projectName)
                       .listAtMarkerInManagedZone(zoneName, input.toString(), options);
            }
         };
      }
   }

}
