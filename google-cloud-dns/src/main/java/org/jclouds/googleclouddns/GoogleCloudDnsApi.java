package org.jclouds.googleclouddns;

import com.google.common.annotations.Beta;

import org.jclouds.googleclouddns.features.ChangeApi;
import org.jclouds.googleclouddns.features.ResourceRecordSetApi;
import org.jclouds.rest.annotations.Delegate;

import java.io.Closeable;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Provides access to Google Cloud  DNS.
 *
 * @author Javier Kohen
 * @author Maciek Weksej
 * @see <a href="https://developers.google.com/cloud-dns/api/v1beta1/">api doc</a>
 */
@Beta
public interface GoogleCloudDnsApi extends Closeable {
   /**
    * Provides access to ResourceRecordSet features
    *
    * @param projectName the name of the project
    */
   @Delegate
   @Path("/projects/{project}")
   ResourceRecordSetApi getResourceRecordSetApiForProject(@PathParam("project") String projectName);

   /**
    * Provides access to Change features
    *
    * @param projectName the name of the project
    */
   @Delegate
   @Path("/projects/{project}")
   ChangeApi getChangeApiForProject(@PathParam("project") String projectName);
}
