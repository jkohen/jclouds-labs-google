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

import java.beans.ConstructorProperties;

import org.jclouds.googleclouddns.domain.Resource;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;


/**
 * Represents a project resource.
 * 
 * @author Maciek Weksej
 * @see <a href="https://developers.google.com/cloud-dns/api/v1beta1/projects"/>
 */
@Beta
public class Project extends Resource {
   
   private final Quota quota;
   
   @ConstructorProperties({
      "number", "id", "quota"
   })
   protected Project(Long number, String id, Quota quota) {
      super(Kind.PROJECT, checkNotNull(number), checkNotNull(id));
      this.quota = checkNotNull(quota);
      
   }
   
   /**
    * @return user assigned unique identifier for the resource.
    */
   public String getId() {
      return getUniqueStringId();
   }
   
   /**
    * @return unique identifier for the resource, which is defined by the server.
    */
   public Long getNumber() {
      return getUniqueNumberId();
   }
   
   /**
    * @return quotas assigned to this project.
    */
   public Quota getQuota() {
      return quota;
   }
   
   /**
    * {@inheritDoc}
    */
   protected Objects.ToStringHelper string() {
      return super.string()
              .add("id", getId())
              .add("number", getNumber())
              .add("quota", quota);
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
      return new Builder().fromProject(this);
   }
   
   public static final class Builder extends Resource.Builder<Builder> {
      private Quota quota;

      /**
       * @see Project#getNumber()
       */
      public Builder number(Long number) {
         return super.uniqueNumberId(number);
      }
      
      /**
       * @see Project#getId()
       */
      public Builder id(String id) {
         return super.uniqueStringId(id);
      }
      
      /**
       * @see Project#getQuota()
       */
      public Builder quota(Quota quota) {
         this.quota = quota;
         return this;
      }
      
      @Override
      protected Builder self() {
         return this;
      }
      
      public Project build() {
         return new Project(super.uniqueNumberId, super.uniqueStringId, quota);
      }
      
      public Builder fromProject(Project in) {
         return super.fromResource(in).number(in.getNumber()).quota(in.getQuota());
      }
   }
}
