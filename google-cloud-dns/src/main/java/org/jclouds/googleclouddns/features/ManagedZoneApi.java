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
package org.jclouds.googleclouddns.features;

import static org.jclouds.googleclouddns.GoogleCloudDnsConstants.DNS_READONLY_SCOPE;
import static org.jclouds.googleclouddns.GoogleCloudDnsConstants.DNS_READWRITE_SCOPE;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.EmptyIterableWithMarkerOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.collect.PagedIterable;
import org.jclouds.googleclouddns.domain.ListPage;
import org.jclouds.googleclouddns.domain.ManagedZone;
import org.jclouds.googleclouddns.functions.internal.ParseManagedZones;
import org.jclouds.googleclouddns.options.ListOptions;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.oauth.v2.config.OAuthScopes;
import org.jclouds.oauth.v2.filters.OAuthAuthenticator;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SkipEncoding;
import org.jclouds.rest.annotations.Transform;

/**
 * Provides access to ManagedZones via its REST API.
 *
 * @author Maciek Weksej
 * @see <a href="https://developers.google.com/cloud-dns/api/v1beta1/managedZones"/>
 */
@SkipEncoding({'/', '='})
@RequestFilters(OAuthAuthenticator.class)
public interface ManagedZoneApi {
   /**
    * Creates a new managed zone.
    *
    * @param name name of the ManagedZone
    * @param dnsName DNS name of the ManagedZone
    * @param description description of the ManagedZone
    * @return if successful, this method returns created ManagedZone resource
    */
   @Named("ManagedZones:create")
   @POST
   @OAuthScopes(DNS_READWRITE_SCOPE)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/managedZones")
   @Fallback(NullOnNotFoundOr404.class)
   ManagedZone create(@PayloadParam("name") String name, @PayloadParam("dnsName") String dnsName,
                      @PayloadParam("description") String desription);
   
   /**
    * Deletes a previously created managed zone.
    *
    * @param name name or ID of the zone to be deleted
    * @return if successful, this method returns a Project resource
    */
   @Named("ManagedZones:delete")
   @DELETE
   @OAuthScopes(DNS_READWRITE_SCOPE)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/managedZones/{managedZone}")
   @Fallback(NullOnNotFoundOr404.class)
   void delete(@PathParam("managedZone") String name);
   
   /**
    * Fetches the representation of an existing managed zone.
    *
    * @param name name or ID of the zone to be fetched.
    * @return if successful, this method returns a ManagedZone resource
    */
   @Named("ManagedZones:get")
   @GET
   @OAuthScopes(DNS_READONLY_SCOPE)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/managedZones/{managedZone}")
   @Fallback(NullOnNotFoundOr404.class)
   ManagedZone get(@PathParam("managedZone") String name);
   
   /**
    * @see ManagedZoneApi#listAtMarker(String, org.jclouds.googleclouddns.options.ListOptions)
    */
   @Named("ManagedZones:list")
   @GET
   @OAuthScopes(DNS_READONLY_SCOPE)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/managedZones/{managedZone}")
   @ResponseParser(ParseManagedZones.class)
   @Transform(ParseManagedZones.ToPagedIterable.class)
   @Fallback(EmptyIterableWithMarkerOnNotFoundOr404.class)
   ListPage<ManagedZone> listFirstPage();
   
   /**
    * Enumerates managed zones that have been created but not yet deleted.
    *
    * By default the list as a maximum size of 100, if no options are provided or ListOptions#getMaxResults() has not
    * been set.
    *
    * @param marker      marks the beginning of the next list page
    * @param listOptions listing options
    * @return a page of the list
    * @see ListOptions
    * @see org.jclouds.googleclouddns.domain.ListPage
    */
   @Named("ManagedZones:list")
   @GET
   @OAuthScopes(DNS_READONLY_SCOPE)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/managedZones/{managedZone}")
   @ResponseParser(ParseManagedZones.class)
   @Transform(ParseManagedZones.ToPagedIterable.class)
   @Fallback(EmptyIterableWithMarkerOnNotFoundOr404.class)
   ListPage<ManagedZone> listAtMarker(@QueryParam("pageToken") @Nullable String marker);
   
   /**
    * @see ManagedZoneApi#listAtMarker(String, org.jclouds.googleclouddns.options.ListOptions)
    */
   @Named("ManagedZones:list")
   @GET
   @OAuthScopes(DNS_READONLY_SCOPE)
   @Consumes(MediaType.APPLICATION_JSON) 
   @Path("/managedZones/{managedZone}")
   @ResponseParser(ParseManagedZones.class)
   @Transform(ParseManagedZones.ToPagedIterable.class)
   @Fallback(EmptyIterableWithMarkerOnNotFoundOr404.class)
   ListPage<ManagedZone> listAtMarker(@QueryParam("pageToken") @Nullable String marker,
                                      ListOptions options);
   
   /**
    * @see ManagedZoneApi#listAtMarker(String, org.jclouds.googleclouddns.options.ListOptions)
    */
   @Named("ManagedZones:list")
   @GET
   @OAuthScopes(DNS_READONLY_SCOPE)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/managedZones/{managedZone}")
   @ResponseParser(ParseManagedZones.class)
   @Transform(ParseManagedZones.ToPagedIterable.class)
   @Fallback(NullOnNotFoundOr404.class)
   PagedIterable<ManagedZone> list(ListOptions options);
}
