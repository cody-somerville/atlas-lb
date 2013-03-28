package org.openstack.atlas.usagerefactor.generator;

import org.openstack.atlas.usagerefactor.PolledUsageRecord;
import sun.util.calendar.Gregorian;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class PolledUsageRecordGenerator {

    public static List<PolledUsageRecord> generate(List<GeneratorPojo> generatorPojoList, Calendar initialPollTime){
        return generate(generatorPojoList, initialPollTime, 5, 0, 0, 0, false, null);
    }

    public static List<PolledUsageRecord> generate(List<GeneratorPojo> generatorPojoList, Calendar initialPollTime,
                                                   List<String> eventTypes){
        return generate(generatorPojoList, initialPollTime, 5, 0, 0, 0, false, eventTypes);
    }

    public static List<PolledUsageRecord> generate(List<GeneratorPojo> generatorPojoList, Calendar initialPollTime,
                                                   long bandwidthOut, long bandwidthIn, boolean ssl){
        return generate(generatorPojoList, initialPollTime, 5, bandwidthOut, bandwidthIn, 0, ssl, null);
    }

    public static List<PolledUsageRecord> generate(List<GeneratorPojo> generatorPojoList, Calendar initialPollTime,
                                                   long bandwidthOut, long bandwidthIn, boolean ssl,
                                                   List<String> eventTypes){
        return generate(generatorPojoList, initialPollTime, 5, bandwidthOut, bandwidthIn, 0, ssl, eventTypes);
    }

    public static List<PolledUsageRecord> generate(List<GeneratorPojo> generatorPojoList, Calendar initialPollTime,
                                                   int pollIntervalInMins, long bandwidthOut, long bandwidthIn,
                                                   long concurrentConnections, boolean ssl, List<String> eventTypes) {
        List<PolledUsageRecord> polledUsageRecords = new ArrayList<PolledUsageRecord>();

        Calendar pollTime;
        int idCnt = 1;

        for (GeneratorPojo generatorPojo : generatorPojoList) {
            pollTime = initialPollTime;
            String eventType = null;
            for (int j = 0; j < generatorPojo.getNumRecords(); j++) {
                if(eventTypes != null && j < eventTypes.size()){
                    eventType = eventTypes.get(j);
                }
                int port = 80;
                if(ssl){
                    port = 443;
                }
                PolledUsageRecord polledUsageRecord = new PolledUsageRecord(
                        idCnt++,
                        generatorPojo.getAccountId(),
                        generatorPojo.getLoadbalancerId(),
                        "10.0.0.1",
                        "TCP",
                        port,
                        ssl,
                        bandwidthOut,
                        bandwidthIn,
                        pollTime,
                        concurrentConnections,
                        eventType
                );

                polledUsageRecords.add(polledUsageRecord);
                Calendar newPollTime = new GregorianCalendar(pollTime.get(Calendar.YEAR), pollTime.get(Calendar.MONTH),
                        pollTime.get(Calendar.DAY_OF_MONTH), pollTime.get(Calendar.HOUR), pollTime.get(Calendar.MINUTE),
                        pollTime.get(Calendar.SECOND));
                newPollTime.add(Calendar.MINUTE, pollIntervalInMins);
                pollTime = newPollTime;
            }
        }

        return polledUsageRecords;
    }
}
