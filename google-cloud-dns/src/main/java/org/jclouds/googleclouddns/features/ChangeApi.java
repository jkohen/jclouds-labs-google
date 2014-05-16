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

import org.jclouds.Fallbacks.EmptyPagedIterableOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.collect.PagedIterable;
import org.jclouds.googleclouddns.domain.Change;
import org.jclouds.googleclouddns.domain.ListPage;
import org.jclouds.googleclouddns.domain.ResourceRecordSet;
import org.jclouds.googleclouddns.functions.internal.ParseChanges;
import org.jclouds.googleclouddns.options.ListOptions;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.oauth.v2.config.OAuthScopes;
import org.jclouds.oauth.v2.filters.OAuthAuthenticator;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SkipEncoding;
import org.jclouds.rest.annotations.Transform;
import org.jclouds.rest.binders.BindToJsonPayload;

import java.util.List;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Provides access to Change via its REST API.
 *
 * @author Javier Kohen
 * @see <a href="https://developers.google.com/cloud-dns/api/v1beta1/changes"/>
 */
@SkipEncoding({'/', '='})
@RequestFilters(OAuthAuthenticator.class)
public interface ChangeApi {
   /**
    * Returns the specified change resource.
    *
    * @param managedZone     Name of the managedZone the change is in.
    * @param changeId The identifier of the requested change, from a previous ResourceRecordSetsChangeResponse.
    * @return a Change resource.
    */
   @Named("Changes:get")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/managedZones/{managedZone}/changes/{changeId}")
   @OAuthScopes(DNS_READONLY_SCOPE)
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   Change getInManagedZone(@PathParam("managedZone") String managedZone, @PathParam("changeId") String changeId);

   /**
    * Creates a change resource.
    *
    *
    * @param managedZone     the name of the region where the change is to be created.
    * @param additions The list of ResourceRecordSets to add in this Change.
    * @param deletions The list of ResourceRecordSets to remove in this Change. The entries in this list must match existing data exactly.
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("Changes:create")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/managedZone/{managedZone}/changes")
   @OAuthScopes({DNS_READWRITE_SCOPE})
   @MapBinder(BindToJsonPayload.class)
   Change createInManagedZone(
         @PayloadParam("additions") List<ResourceRecordSet> additions,
         @PayloadParam("deletions") List<ResourceRecordSet> deletions,
         @PathParam("managedZone") String managedZone);

   /**
    * Enumerates Changes that have been created but not yet deleted.
    *
    * @param managedZone the managed zone to list in
    * @return a Paged, Fluent Iterable that is able to fetch additional pages when required
    * @see org.jclouds.collect.PagedIterable
    */
   @Named("Changes:list")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/managedZones/{managedZone}/rrsets")
   @OAuthScopes(DNS_READONLY_SCOPE)
   @ResponseParser(ParseChanges.class)
   @Transform(ParseChanges.ToPagedIterable.class)
   @Fallback(EmptyPagedIterableOnNotFoundOr404.class)
   ListPage<Change> listAtMarkerInManagedZone(
         @PathParam("managedZone") String managedZone, @QueryParam("nextPageToken") @Nullable String marker, ListOptions listOptions);

   /**
    * A paginated version of {@link #listAtMarkerInManagedZone(String, String, ListOptions)}.
    *
    * @param managedZone the managed zone to list in
    * @return a Paged, Fluent Iterable that is able to fetch additional pages when required
    * @see org.jclouds.collect.PagedIterable
    */
   @Named("Changes:list")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/managedZones/{managedZone}/rrsets")
   @OAuthScopes(DNS_READONLY_SCOPE)
   @ResponseParser(ParseChanges.class)
   @Transform(ParseChanges.ToPagedIterable.class)
   @Fallback(EmptyPagedIterableOnNotFoundOr404.class)
   PagedIterable<Change> listInManagedZone(@PathParam("managedZone") String managedZone);
}
