package org.openstack.atlas.usage.execution;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.atlas.restclients.atomhopper.util.AtomHopperUtil;
import org.openstack.atlas.service.domain.entities.JobName;
import org.openstack.atlas.service.domain.entities.Usage;
import org.openstack.atlas.service.domain.events.repository.AlertRepository;
import org.openstack.atlas.service.domain.events.repository.LoadBalancerEventRepository;
import org.openstack.atlas.service.domain.repository.LoadBalancerRepository;
import org.openstack.atlas.service.domain.repository.UsageRepository;
import org.openstack.atlas.usage.BatchAction;
import org.openstack.atlas.usage.ExecutionUtilities;
import org.openstack.atlas.usage.thread.UsageThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UsageAtomHopperRetryExecution extends AbstractAtomHopperUsageExecution {
    private final Log LOG = LogFactory.getLog(UsageAtomHopperRetryExecution.class);

    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private UsageRepository usageRepository;
    @Autowired
    private LoadBalancerRepository loadBalancerRepository;
    @Autowired
    private LoadBalancerEventRepository loadBalancerEventRepository;

    @Override
    public JobName getJobName() {
        return JobName.ATOM_LOADBALANCER_USAGE_POLLER;
    }

    @Override
    public void pushUsageToAtomHopper() throws Exception {
        final List<Usage> allUsages = loadBalancerRepository.getUsageRetryNeedsPushed(AtomHopperUtil.getStartCal(),
                AtomHopperUtil.getNow(), NUM_ATTEMPTS);

        if (!allUsages.isEmpty()) {
            LOG.info(String.format("Processing %d records marked for retry", allUsages.size()));
            BatchAction<Usage> batchAction = new BatchAction<Usage>() {
                public void execute(Collection<Usage> allUsages) throws Exception {
                    executeTasks(allUsages);
                }
            };

            ExecutionUtilities.ExecuteInBatches(allUsages, BATCH_SIZE, batchAction);
        } else {
            LOG.debug("No usage to retry found for processing at this time...");
        }
    }

    private void executeTasks(Collection<Usage> allUsages) throws Exception {
        ArrayList<Usage> usageList = new ArrayList<Usage>();
        usageList.addAll(allUsages);
        poolExecutor.execute(new UsageThread(usageList, ahuslClient, identityClient, usageRepository,
                loadBalancerEventRepository, alertRepository));
    }
}