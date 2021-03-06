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

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.googleclouddns.domain.Project;
import org.jclouds.oauth.v2.config.OAuthScopes;
import org.jclouds.oauth.v2.filters.OAuthAuthenticator;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SkipEncoding;

/**
 * Provides access to Project via its REST API.
 *
 * @author Maciek Weksej
 * @see <a href="https://developers.google.com/cloud-dns/api/v1beta1/projects"/>
 */
@SkipEncoding({'/', '='})
@RequestFilters(OAuthAuthenticator.class)
public interface ProjectApi {
   /**
    * Returns the specified project resource.
    *
    * @param projectName name of the project to return
    * @return if successful, this method returns a Project resource
    */
   @Named("Projects:get")
   @GET
   @OAuthScopes(DNS_READONLY_SCOPE)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/projects/{project}")
   @Fallback(NullOnNotFoundOr404.class)
   Project get(@PathParam("project") String projectName);
}
