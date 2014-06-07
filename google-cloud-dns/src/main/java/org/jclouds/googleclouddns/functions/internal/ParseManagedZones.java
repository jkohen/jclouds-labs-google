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
import org.jclouds.googleclouddns.domain.ListPage;
import org.jclouds.googleclouddns.domain.ManagedZone;
import org.jclouds.googleclouddns.options.ListOptions;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.json.Json;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Andrew Bayer
 * @author Maciek Weksej
 */
@Singleton
public class ParseManagedZones extends ParseJson<ListPage<ManagedZone>> {

   @Inject
   public ParseManagedZones(Json json) {
      super(json, new TypeLiteral<ListPage<ManagedZone>>() {
      });
   }

   public static class ToPagedIterable extends BaseToPagedIterable<ManagedZone, ToPagedIterable> {

      private final GoogleCloudDnsApi api;

      @Inject
      protected ToPagedIterable(GoogleCloudDnsApi api) {
         this.api = checkNotNull(api, "api");
      }

      @Override
      protected Function<Object, IterableWithMarker<ManagedZone>> fetchNextPage(
            final String projectName, final ListOptions options) {
         return new Function<Object, IterableWithMarker<ManagedZone>>() {

            @Override
            public IterableWithMarker<ManagedZone> apply(Object input) {
               return api.getManagedZoneApiForProject(projectName)
                       .listAtMarker(input.toString(), options);
            }
         };
      }
   }

}
