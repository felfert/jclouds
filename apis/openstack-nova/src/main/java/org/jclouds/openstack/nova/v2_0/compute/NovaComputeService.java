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
package org.jclouds.openstack.nova.v2_0.compute;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.compute.config.ComputeServiceProperties.TIMEOUT_NODE_RUNNING;
import static org.jclouds.compute.config.ComputeServiceProperties.TIMEOUT_NODE_SUSPENDED;
import static org.jclouds.compute.config.ComputeServiceProperties.TIMEOUT_NODE_TERMINATED;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import javax.inject.Provider;
import jakarta.inject.Singleton;

import org.jclouds.Constants;
import org.jclouds.collect.Memoized;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.callables.RunScriptOnNode;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.extensions.ImageExtension;
import org.jclouds.compute.extensions.SecurityGroupExtension;
import org.jclouds.compute.extensions.internal.DelegatingImageExtension;
import org.jclouds.compute.internal.BaseComputeService;
import org.jclouds.compute.internal.PersistNodeCredentials;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.compute.strategy.CreateNodesInGroupThenAddToSet;
import org.jclouds.compute.strategy.DestroyNodeStrategy;
import org.jclouds.compute.strategy.GetImageStrategy;
import org.jclouds.compute.strategy.GetNodeMetadataStrategy;
import org.jclouds.compute.strategy.InitializeRunScriptOnNodeOrPlaceInBadMap;
import org.jclouds.compute.strategy.ListNodesStrategy;
import org.jclouds.compute.strategy.RebootNodeStrategy;
import org.jclouds.compute.strategy.ResumeNodeStrategy;
import org.jclouds.compute.strategy.SuspendNodeStrategy;
import org.jclouds.domain.Credentials;
import org.jclouds.domain.Location;
import org.jclouds.openstack.nova.v2_0.compute.functions.CleanupResources;
import org.jclouds.openstack.nova.v2_0.compute.options.NovaTemplateOptions;
import org.jclouds.scriptbuilder.functions.InitAdminAccess;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListeningExecutorService;

@Singleton
public class NovaComputeService extends BaseComputeService {
   protected final CleanupResources cleanupResources;

   @Inject
   protected NovaComputeService(ComputeServiceContext context, Map<String, Credentials> credentialStore,
         @Memoized Supplier<Set<? extends Image>> images, @Memoized Supplier<Set<? extends Hardware>> sizes,
         @Memoized Supplier<Set<? extends Location>> locations, ListNodesStrategy listNodesStrategy,
         GetImageStrategy getImageStrategy, GetNodeMetadataStrategy getNodeMetadataStrategy,
         CreateNodesInGroupThenAddToSet runNodesAndAddToSetStrategy, RebootNodeStrategy rebootNodeStrategy,
         DestroyNodeStrategy destroyNodeStrategy, ResumeNodeStrategy startNodeStrategy,
         SuspendNodeStrategy stopNodeStrategy, Provider<TemplateBuilder> templateBuilderProvider,
         @Named("DEFAULT") Provider<TemplateOptions> templateOptionsProvider,
         @Named(TIMEOUT_NODE_RUNNING) Predicate<AtomicReference<NodeMetadata>> nodeRunning,
         @Named(TIMEOUT_NODE_TERMINATED) Predicate<AtomicReference<NodeMetadata>> nodeTerminated,
         @Named(TIMEOUT_NODE_SUSPENDED) Predicate<AtomicReference<NodeMetadata>> nodeSuspended,
         InitializeRunScriptOnNodeOrPlaceInBadMap.Factory initScriptRunnerFactory,
         RunScriptOnNode.Factory runScriptOnNodeFactory, InitAdminAccess initAdminAccess,
         PersistNodeCredentials persistNodeCredentials,
         @Named(Constants.PROPERTY_USER_THREADS) ListeningExecutorService userExecutor,
         CleanupResources cleanupResources, Optional<ImageExtension> imageExtension,
         Optional<SecurityGroupExtension> securityGroupExtension,
         DelegatingImageExtension.Factory delegatingImageExtension) {
      super(context, credentialStore, images, sizes, locations, listNodesStrategy, getImageStrategy,
            getNodeMetadataStrategy, runNodesAndAddToSetStrategy, rebootNodeStrategy, destroyNodeStrategy,
            startNodeStrategy, stopNodeStrategy, templateBuilderProvider, templateOptionsProvider, nodeRunning,
            nodeTerminated, nodeSuspended, initScriptRunnerFactory, initAdminAccess, runScriptOnNodeFactory,
            persistNodeCredentials, userExecutor, imageExtension, securityGroupExtension, delegatingImageExtension);
      this.cleanupResources = checkNotNull(cleanupResources, "cleanupResources");

   }

   @Override
   protected void cleanUpIncidentalResourcesOfDeadNodes(Set<? extends NodeMetadata> deadNodes) {
      for (NodeMetadata deadNode : deadNodes) {
         cleanupResources.apply(deadNode);
      }
   }

   /**
    * returns template options, except of type {@link NovaTemplateOptions}.
    */
   @Override
   public NovaTemplateOptions templateOptions() {
      return NovaTemplateOptions.class.cast(super.templateOptions());
   }

}
