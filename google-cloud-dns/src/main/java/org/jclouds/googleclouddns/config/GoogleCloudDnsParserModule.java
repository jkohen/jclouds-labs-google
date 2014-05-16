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
package org.jclouds.googleclouddns.config;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import org.jclouds.googleclouddns.domain.Change;
import org.jclouds.googleclouddns.domain.ManagedZone;
import org.jclouds.googleclouddns.domain.Project;
import org.jclouds.googleclouddns.domain.Quota;
import org.jclouds.googleclouddns.domain.ResourceRecordSet;
import org.jclouds.json.config.GsonModule;

import java.beans.ConstructorProperties;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

/**
 * @author Javier Kohen
 */
public class GoogleCloudDnsParserModule extends AbstractModule {

   @Override
   protected void configure() {
      bind(GsonModule.DateAdapter.class).to(GsonModule.Iso8601DateAdapter.class);
   }

   @Provides
   @Singleton
   public Map<Type, Object> provideCustomAdapterBindings() {
      return new ImmutableMap.Builder<Type, Object>()
              .put(Change.class, new ChangeTypeAdapter())
              .put(ManagedZone.class, new ManagedZoneTypeAdapter())
              .put(Project.class, new ProjectTypeAdapter())
              .put(ResourceRecordSet.class, new ResourceRecordSetTypeAdapter())
              .build();
   }

   @Singleton
   private static class ChangeTypeAdapter implements JsonDeserializer<Change> {
      @Override
      public Change deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
         return Change.builder().fromChange((Change) context.deserialize(json, ChangeInternal.class)).build();
      }

      private static class ChangeInternal extends Change {
         @ConstructorProperties({
            "additions", "deletions", "startTime", "id", "status"
         })
         private ChangeInternal(List<ResourceRecordSet> additions, List<ResourceRecordSet> deletions, Date startTime, Long id, String status) {
            super(additions, deletions, startTime, id, status);
         }

      }
   }

   @Singleton
   private static class ManagedZoneTypeAdapter implements JsonDeserializer<ManagedZone> {
      @Override
      public ManagedZone deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
         return ManagedZone.builder().fromManagedZone((ManagedZone) context.deserialize(json, ManagedZoneInternal.class)).build();
      }

      private static class ManagedZoneInternal extends ManagedZone {
         @ConstructorProperties({
            "name", "dnsName", "description", "id", "nameServers", "creationTime"
         })
         private ManagedZoneInternal(String name, String dnsName, String description, Long id, Set<String> nameServers, Date creationTime) {
            super(name, dnsName, description, id, nameServers, creationTime);
         }

      }
   }

   @Singleton
   private static class ProjectTypeAdapter implements JsonDeserializer<Project> {
      @Override
      public Project deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
         return Project.builder().fromProject((Project) context.deserialize(json, ProjectInternal.class)).build();
      }

      private static class ProjectInternal extends Project {
         @ConstructorProperties({
            "id", "number", "quota"
         })
         private ProjectInternal(Long number, String id, Quota quota) {
            super(number, id, quota);
         }

      }
   }

   @Singleton
   private static class ResourceRecordSetTypeAdapter implements JsonDeserializer<ResourceRecordSet> {
      @Override
      public ResourceRecordSet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
         return ResourceRecordSet.builder().fromResourceRecordSet((ResourceRecordSet) context.deserialize(json, ResourceRecordSetInternal.class)).build();
      }

      private static class ResourceRecordSetInternal extends ResourceRecordSet {
         @ConstructorProperties({
            "name", "type", "ttl", "rrdatas"
         })
         private ResourceRecordSetInternal(String name, String type, Integer ttl, List<String> rrdatas) {
            super(name, type, ttl, rrdatas);
         }

      }
   }
}
