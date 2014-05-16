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

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.beans.ConstructorProperties;
import java.util.List;

/**
 * Represents the ResourceRecordSet Cloud DNS resource.
 *
 * @author Javier Kohen
 * @see <a href="https://developers.google.com/cloud-dns/api/v1beta1/resourceRecordSets"/>
 */
public class ResourceRecordSet extends Resource {

   private final String type;
   private final Integer ttl;
   private final List<String> rrdatas;

   @ConstructorProperties({
      "name", "type", "ttl", "rrdatas"
   })
   protected ResourceRecordSet(String name, String type, Integer ttl, List<String> rrdatas) {
      super(Kind.RESOURCE_RECORD_SET, null, name);
      this.type = type;
      this.ttl = ttl;
      this.rrdatas = rrdatas == null ? ImmutableList.<String>of() : ImmutableList.copyOf(rrdatas);
   }

   /**
    * @return the record name. For example, www.example.com., which includes the trailing period (.).
    */
   public String getName() {
      return getUniqueStringId();
   }

   /**
    * @return the type of resource record.
    */
   public String getType() {
      return type;
   }

   /**
    * @return the number of seconds that this resource record set can be cached by DNS resolvers.
    */
   public Integer getTtl() {
      return ttl;
   }

   /**
    * @return the list of resource records. As defined in RFC 1035 (section 5) and RFC 1034 (section 3.6.1).
    */
   public List<String> getRrdatas() {
      return rrdatas;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected Objects.ToStringHelper string() {
      return super.string()
              .add("name", getName())
              .add("type", type)
              .add("ttl", ttl)
              .add("rrdatas", rrdatas);
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

   @Override
   public Builder toBuilder() {
      return new Builder().fromResourceRecordSet(this);
   }

   public static final class Builder extends Resource.Builder<Builder> {
      private String type;
      private Integer ttl;
      private ImmutableList.Builder<String> rrdatas = ImmutableList.builder();

      /**
       * @see ResourceRecordSet#getType()
       */
      public Builder type(String type) {
         this.type = type;
         return this;
      }

      /**
       * @see ResourceRecordSet#getTtl()
       */
      public Builder ttl(Integer ttl) {
         this.ttl = ttl;
         return this;
      }

      /**
       * @see ResourceRecordSet#getRrdatas()
       */
      public Builder addRrdatas(String rrdata) {
         this.rrdatas.add(rrdata);
         return this;
      }

      /**
       * @see ResourceRecordSet#getRrdatas()
       */
      public Builder rrdatas(List<String> rrdatas) {
         this.rrdatas.addAll(checkNotNull(rrdatas));
         return this;
      }

      @Override
      protected Builder self() {
         return this;
      }

      public ResourceRecordSet build() {
         return new ResourceRecordSet(super.uniqueStringId, type, ttl, rrdatas.build());
      }

      public Builder fromResourceRecordSet(ResourceRecordSet in) {
         return super.fromResource(in).type(in.getType()).ttl(in.getTtl()).rrdatas(in.getRrdatas());
      }
   }
}
