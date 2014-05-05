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

package org.jclouds.googleclouddns.domain;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Optional.fromNullable;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.Set;

import org.jclouds.googleclouddns.domain.Resource;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;


/**
 * Represents a ManagedZone resource.
 *
 * @author mwek
 * @see <a href="https://developers.google.com/cloud-dns/api/v1beta1/managedZones"/>
 */
@Beta
public class ManagedZone extends Resource {

   private final String dnsName;
   private final Optional<String> description;
   private final Set<String> nameServers;
   private final Date creationTime;

   @ConstructorProperties({
      "name", "dnsName", "description", "id", "nameServers", "creationTime"
   })
   protected ManagedZone(String name, String dnsName, String description, Long id, Set<String> nameServers, Date creationTime) {
      super(Kind.MANAGED_ZONE, checkNotNull(id), checkNotNull(name));
      this.dnsName = checkNotNull(dnsName, "dnsName");
      this.description = fromNullable(description);
      this.nameServers = nameServers == null ? ImmutableSet.<String>of() : ImmutableSet.copyOf(nameServers);
      this.creationTime = checkNotNull(creationTime, "creationTime");
   }

   /**
    * @return the name for the zone, which can contain lower case alphanumeric characters and hyphens. The name must be unique within the project.
    */
   public String getName() {
      return getUniqueStringId();
   }

   /**
    * @return unique identifier for the resource, which is defined by the server.
    */
   public Long getId() {
      return getUniqueNumberId();
   }

   /**
    * @return the DNS name of this ManagedZone, in the format of example.com., which includes the period (.) at the end of the domain name.
    */

   public String getDnsName() {
      return dnsName;
   }

   /**
    * @return an optional description to help identify the resource.
    */
   public Optional<String> getDescription() {
      return description;
   }

   /**
    * @return delegate your managed zone to these virtual name servers; which are defined by the server.
    */

   public Set<String> getNameServers() {
      return nameServers;
   }

   /**
    * @return the time that this resource was created on the server. This is in RFC3339 text format.
    */
   public Date getCreationTime() {
      return creationTime;
   }

   /**
    * {@inheritDoc}
    */
   protected Objects.ToStringHelper string() {
      return super.string()
              .add("id", getId())
              .add("name", getName())
              .add("dnsName", dnsName)
              .add("description", description.orNull())
              .add("nameServers", nameServers)
              .add("creationTime", creationTime);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return string().toString();
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return new Builder().fromManagedZone(this);
   }

   public static final class Builder extends Resource.Builder<Builder> {
      private String dnsName;
      private String description;
      private ImmutableSet.Builder<String> nameServers = ImmutableSet.builder();
      private Date creationTime;

      /**
       * @see ManagedZone#getCreationTime()
       */
      public Builder creationTime(Date creationTime) {
         this.creationTime = creationTime;
         return this;
      }
      
      /**
       * @see ManagedZone#getId()
       */
      public Builder id(Long id) {
         return super.uniqueNumberId(id);
      }
      
      /**
       * @see ManagedZone#getDescription()
       */
      public Builder description(String description) {
         this.description = description;
         return this;
      }
      
      /**
       * @see ManagedZone#getNameServers()
       */
      public Builder addNameServer(String nameServer) {
         this.nameServers.add(checkNotNull(nameServer));
         return this;
      }
      
      /**
       * @see ManagedZone#getNameServers()
       */
      public Builder nameServers(Set<String> nameServers) {
         this.nameServers.addAll(checkNotNull(nameServers));
         return this;
      }
      
      /**
       * @see ManagedZone#getDnsName()
       */
      public Builder dnsName(String dnsName) {
         this.dnsName = dnsName;
         return this;
      }
      
      /**
       * @see ManagedZone#getName()
       */
      public Builder name(String name) {
         return super.uniqueStringId(name);
      }

      @Override
      protected Builder self() {
         return this;
      }

      public ManagedZone build() {
         return new ManagedZone(super.uniqueStringId, dnsName, description, super.uniqueNumberId, nameServers.build(), creationTime);
      }

      public Builder fromManagedZone(ManagedZone in) {
         return super.fromResource(in).creationTime(in.getCreationTime()).description(in.getDescription().orNull()).dnsName(in.getDnsName()).nameServers(in.getNameServers());
      }
   }
}
