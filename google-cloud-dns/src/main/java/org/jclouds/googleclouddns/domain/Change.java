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
import java.util.Date;
import java.util.List;

/**
 * Represents the Change Cloud DNS resource.
 *
 * @author jkohen
 * @see <a href="https://developers.google.com/cloud-dns/api/v1beta1/changes"/>
 */
public class Change extends Resource {

   private final List<ResourceRecordSet> additions;
   private final List<ResourceRecordSet> deletions;
   private final Date startTime;
   private final String status;

   @ConstructorProperties({
      "additions", "deletions", "startTime", "id", "status"
   })
   protected Change(List<ResourceRecordSet> additions, List<ResourceRecordSet> deletions, Date startTime, Long id, String status) {
      super(Kind.CHANGE, id, null);
      this.additions = additions == null ? ImmutableList.<ResourceRecordSet>of() : ImmutableList.copyOf(additions);
      this.deletions = deletions == null ? ImmutableList.<ResourceRecordSet>of() : ImmutableList.copyOf(deletions);
      this.startTime = startTime;
      this.status = status;
   }

   /**
    * @return the list of ResourceRecordSets to add in this Change.
    */
   public List<ResourceRecordSet> getAdditions() {
      return additions;
   }

   /**
    * @return the list of ResourceRecordSets to remove in this Change. The entries in this list must match existing data exactly.
    */
   public List<ResourceRecordSet> getDeletions() {
      return deletions;
   }

   /**
    * @return the time that this operation was started by the server. This is in RFC3339 text format.
    */
   public Date getStartTime() {
      return startTime;
   }

   /**
    * @return the status of the operation. 
    * 
    * <p>Acceptable values are:
    * <ul>
    *   <li>"done": The operation has finished.</li>
    *   <li>"pending": The operation is still in progress.</li>
    * </ul>
    */
   public String getStatus() {
      return status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected Objects.ToStringHelper string() {
      return super.string()
              .add("additions", getAdditions())
              .add("deletions", getDeletions())
              .add("startTime", getStartTime())
              .add("status", getStatus());
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
      return new Builder().fromChange(this);
   }

   public static final class Builder extends Resource.Builder<Builder> {
      private ImmutableList.Builder<ResourceRecordSet> additions = ImmutableList.builder();
      private ImmutableList.Builder<ResourceRecordSet> deletions = ImmutableList.builder();
      private Date startTime;
      private String status;

      /**
       * @see Change#getAdditions()
       */
      public Builder addAddition(ResourceRecordSet addition) {
         this.additions.add(addition);
         return this;
      }

      /**
       * @see Change#getAdditions()
       */
      public Builder additions(List<ResourceRecordSet> additions) {
         this.additions.addAll(checkNotNull(additions));
         return this;
      }

      /**
       * @see Change#getDeletions()
       */
      public Builder addDeletion(ResourceRecordSet deletion) {
         this.deletions.add(deletion);
         return this;
      }

      /**
       * @see Change#getDeletions()
       */
      public Builder deletions(List<ResourceRecordSet> deletions) {
         this.deletions.addAll(checkNotNull(deletions));
         return this;
      }

      /**
       * @see Change#getStartTime()
       */
      public Builder startTime(Date startTime) {
         this.startTime = startTime;
         return this;
      }

      /**
       * @see Change#getStatus()
       */
      public Builder status(String status) {
         this.status = status;
         return this;
      }

      @Override
      protected Builder self() {
         return this;
      }

      public Change build() {
         return new Change(additions.build(), deletions.build(), startTime, super.uniqueNumberId, status);
      }

      public Builder fromChange(Change in) {
         return super.fromResource(in).additions(in.getAdditions()).deletions(in.getDeletions()).startTime(in.getStartTime()).status(in.getStatus());
      }
   }

}
