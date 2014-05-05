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

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * Quotas assigned to a given project.
 *
 * @see <a href="https://developers.google.com/cloud-dns/api/v1beta1/projects"/>
 */
@Beta
public class Quota {
   private int managedZones;
   private int rrsetsPerManagedZone;
   private int rrsetAdditionsPerChange;
   private int rrsetDeletionsPerChange;
   private int totalRrdataSizePerChange;
   private int resourceRecordsPerRrset;

   @ConstructorProperties({
           "managedZones", "rrsetsPerManagedZone", "rrsetAdditionsPerChange", "rrsetDeletionsPerChange", "totalRrdataSizePerChange", "resourceRecordsPerRrset"
   })
   public Quota(Integer managedZones, Integer rrsetsPerManagedZone, Integer rrsetAdditionsPerChange, Integer rrsetDeletionsPerChange, Integer totalRrdataSizePerChange, Integer resourceRecordsPerRrset) {
      this.managedZones = checkNotNull(managedZones, "managedZones");
      this.rrsetsPerManagedZone = checkNotNull(rrsetsPerManagedZone, "rrsetsPerManagedZone");
      this.rrsetAdditionsPerChange = checkNotNull(rrsetAdditionsPerChange, "rrsetAdditionsPerChange");
      this.rrsetDeletionsPerChange = checkNotNull(rrsetDeletionsPerChange, "rrsetDeletionsPerChange");
      this.totalRrdataSizePerChange = checkNotNull(totalRrdataSizePerChange, "totalRrdataSizePerChange");
      this.resourceRecordsPerRrset = checkNotNull(resourceRecordsPerRrset, "resourceRecordsPerRrset");
   }

   /**
    * @return maximum allowed number of managed zones in the project.
    */
   public Integer getManagedZones() {
      return managedZones;
   }

   /**
    * @return maximum allowed number of resource records per ResourceRecordSet.
    */
   public Integer getResourceRecordsPerRrset() {
      return resourceRecordsPerRrset;
   }

   /**
    * @return maximum allowed number of ResourceRecordSets to add per Changes.create request.
    */
   public Integer getRrsetAdditionsPerChange() {
      return rrsetAdditionsPerChange;
   }

   /**
    * @return maximum allowed number of ResourceRecordSets to delete per Changes.create request.
    */
   public Integer getRrsetDeletionsPerChange() {
      return rrsetDeletionsPerChange;
   }

   /**
    * @return maximum allowed number of ResourceRecordSets per zone in the project.
    */
   public Integer getRrsetsPerManagedZone() {
      return rrsetsPerManagedZone;
   }

   /**
    * @return maximum allowed size in bytes for the rrdata field in one Changes.create request.
    */
   public Integer getTotalRrdataSizePerChange() {
      return totalRrdataSizePerChange;
   }

   /**
    * {@inheritDoc}
    */
   public ToStringHelper string() {
      return Objects.toStringHelper(this)
              .omitNullValues()
              .add("managedZones", managedZones)
              .add("rrsetsPerManagedZone", rrsetsPerManagedZone)
              .add("rrsetAdditionsPerChange", rrsetAdditionsPerChange)
              .add("rrsetDeletionsPerChange", rrsetDeletionsPerChange)
              .add("totalRrdataSizePerChange", totalRrdataSizePerChange)
              .add("resourceRecordsPerRrset", resourceRecordsPerRrset);
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
      return builder().fromQuota(this);
   }

   public static class Builder {

      private Integer managedZones;
      private Integer rrsetsPerManagedZone;
      private Integer rrsetAdditionsPerChange;
      private Integer rrsetDeletionsPerChange;
      private Integer totalRrdataSizePerChange;
      private Integer resourceRecordsPerRrset;

      /**
       * @see Quota#getManagedZones();
       */
      public Builder managedZones(Integer managedZones) {
         this.managedZones = managedZones;
         return this;
      }

      /**
       * @see Quota#getRrsetsPerManagedZone();
       */
      public Builder rrsetsPerManagedZone(Integer rrsetsPerManagedZone) {
         this.rrsetsPerManagedZone = rrsetsPerManagedZone;
         return this;
      }

      /**
       * @see Quota#getRrsetAdditionsPerChange();
       */
      public Builder rrsetAdditionsPerChange(Integer rrsetAdditionsPerChange) {
         this.rrsetAdditionsPerChange = rrsetAdditionsPerChange;
         return this;
      }

      /**
       * @see Quota#getRrsetDeletionsPerChange();
       */
      public Builder rrsetDeletionsPerChange(Integer rrsetDeletionsPerChange) {
         this.rrsetDeletionsPerChange = rrsetDeletionsPerChange;
         return this;
      }

      /**
       * @see Quota#getTotalRrdataSizePerChange();
       */
      public Builder totalRrdataSizePerChange(Integer totalRrdataSizePerChange) {
         this.totalRrdataSizePerChange = totalRrdataSizePerChange;
         return this;
      }

      /**
       * @see Quota#getResourceRecordsPerRrset();
       */
      public Builder resourceRecordsPerRrset(Integer resourceRecordsPerRrset) {
         this.resourceRecordsPerRrset = resourceRecordsPerRrset;
         return this;
      }

      public Quota build() {
         return new Quota(managedZones, rrsetsPerManagedZone, rrsetAdditionsPerChange, rrsetDeletionsPerChange, totalRrdataSizePerChange, resourceRecordsPerRrset);
      }

      public Builder fromQuota(Quota quota) {
         return new Builder().managedZones(quota.getManagedZones()).rrsetsPerManagedZone(quota.getRrsetsPerManagedZone()).rrsetAdditionsPerChange(quota.getRrsetAdditionsPerChange()).rrsetDeletionsPerChange(quota.getRrsetDeletionsPerChange()).totalRrdataSizePerChange(quota.getTotalRrdataSizePerChange()).resourceRecordsPerRrset(quota.getResourceRecordsPerRrset());
      }
   }
}
