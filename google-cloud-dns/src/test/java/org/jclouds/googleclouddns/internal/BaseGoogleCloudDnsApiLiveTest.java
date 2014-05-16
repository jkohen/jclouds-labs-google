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
package org.jclouds.googleclouddns.internal;

import org.jclouds.apis.BaseApiLiveTest;
import org.jclouds.googleclouddns.GoogleCloudDnsApi;
import org.jclouds.googleclouddns.GoogleCloudDnsConstants;

import java.net.URI;


/**
 * @author David Alves
 */
public class BaseGoogleCloudDnsApiLiveTest extends BaseApiLiveTest<GoogleCloudDnsApi> {

   protected static final String API_URL_PREFIX = "https://www.googleapis.com/dns/v1beta1/projects/";
   protected static final String MANAGED_ZONE_API_URL_SUFFIX = "/managedZones/";
   protected static final String DEFAULT_MANAGED_ZONE_NAME = "yourzonename";

   protected static final String GOOGLE_PROJECT = "google";

   public BaseGoogleCloudDnsApiLiveTest() {
      provider = GoogleCloudDnsConstants.DNS_PROVIDER_NAME;
   }

   protected URI getDefaultManagedZoneUrl(String project) {
      return getManagedZoneUrl(project, DEFAULT_MANAGED_ZONE_NAME);
   }

   protected URI getManagedZoneUrl(String project, String managedZone) {
      return URI.create(API_URL_PREFIX + project + MANAGED_ZONE_API_URL_SUFFIX + managedZone);
   }
}

