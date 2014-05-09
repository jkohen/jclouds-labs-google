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

import static com.google.common.base.Objects.ToStringHelper;
import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;

import com.google.common.annotations.Beta;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * Base class for Google Cloud DNS resources.
 *
 * @author Maciek Weksej
 */
@Beta
public abstract class Resource {

   public enum Kind {
      CHANGE,
      CHANGES_LIST_RESPONSE,
      MANAGED_ZONE,
      MANAGED_ZONES_LIST_RESPONSE,
      PROJECT,
      RESOURCE_RECORD_SET,
      RESOURCE_RECORD_SETS_LIST_RESPONSE;

      public String value() {
         return Joiner.on("#").join("dns", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name()));
      }

      @Override
      public String toString() {
         return value();
      }

      public static Kind fromValue(String kind) {
         return valueOf(CaseFormat.LOWER_CAMEL.to(CaseFormat
                 .UPPER_UNDERSCORE,
                 Iterables.getLast(Splitter.on("#").split(checkNotNull(kind,
                         "kind")))));
      }
   }

   protected final Kind kind;
   protected final Long uniqueNumberId;
   protected final String uniqueStringId;
   
   @ConstructorProperties({
           "kind", "uniqueNumberId", "uniqueStringId"
   })
   protected Resource(Kind kind, Long uniqueNumberId, String uniqueStringId) {
      this.kind = checkNotNull(kind, "kind");
      this.uniqueNumberId = uniqueNumberId;
      this.uniqueStringId = uniqueStringId;
   }

   /**
    * @return the Type of the resource
    */
   public Kind getKind() {
      return kind;
   }
   
   /**
    * Derived class should wrap it either in getName() or getId().
    * @return the unique String ID of the resource
    */
   protected String getUniqueStringId() {
      return uniqueStringId;
   }
   
   /**
    * Derived class should wrap it either in getName() or getId().
    * @return the unique Number ID of the resource
    */
   protected Long getUniqueNumberId() {
      return uniqueNumberId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return Objects.hashCode(kind, uniqueNumberId, uniqueStringId);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Resource that = Resource.class.cast(obj);
      return equal(this.kind, that.kind)
              && equal(this.uniqueNumberId, that.uniqueNumberId)
              && equal(this.uniqueStringId, that.uniqueStringId);
   }
   
   /**
    * {@inheritDoc}
    */
   protected ToStringHelper string() {
      return toStringHelper(this)
              .omitNullValues()
              .add("kind", kind);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return string().toString();
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromResource(this);
   }

   public abstract static class Builder<T extends Builder<T>> {

      protected abstract T self();

      protected Kind kind;
      protected Long uniqueNumberId;
      protected String uniqueStringId;

      /**
       * @see Resource#getKind()
       */
      protected T kind(Kind kind) {
         this.kind = kind;
         return self();
      }
      
      protected T uniqueNumberId(Long uniqueNumberId) {
         this.uniqueNumberId = uniqueNumberId;
         return self();
      }
      
      protected T uniqueStringId(String uniqueStringId) {
         this.uniqueStringId = uniqueStringId;
         return self();
      }

      public T fromResource(Resource in) {
         return this
                 .kind(in.getKind())
                 .uniqueNumberId(in.getUniqueNumberId())
                 .uniqueStringId(in.getUniqueStringId());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }
}
