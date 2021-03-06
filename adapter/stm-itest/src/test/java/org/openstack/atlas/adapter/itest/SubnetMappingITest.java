package org.openstack.atlas.adapter.itest;


import org.junit.Test;
import org.openstack.atlas.service.domain.pojos.Hostssubnet;

public class SubnetMappingITest extends STMTestBase {

    private static Hostssubnet EXISTING_MAPPINGS;

    @Test
    public void deprecatedClass() {

    }
//    @BeforeClass
//    public static void setUpClass() throws StmRollBackException {
//        EXISTING_MAPPINGS = stmAdapter.getSubnetMappings(config, TARGET_HOST);
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws StmRollBackException {
//        try {
//        stmAdapter.deleteLoadBalancer(config, lb);
//        } catch(Exception e) {
//
//        }
//    }
//
//    @Test
//    public void getSubnetMappingsTest() throws StmRollBackException {
//        Hostssubnet existingMapping = stmAdapter.getSubnetMappings(config, TARGET_HOST);
//        List<Hostsubnet> existingSubnets = existingMapping.getHostsubnets();
//        Hostsubnet targetHost = existingSubnets.get(0);
//        List<NetInterface> interfaceList = targetHost.getNetInterfaces();
//        NetInterface netInterface = interfaceList.get(0);
//        String ifaceName = netInterface.getName();
//        List<Cidr> cidrList = netInterface.getCidrs();
//        Cidr cidr = cidrList.get(0);
//        String subnetBlock = cidr.getBlock();
//
//        Assert.assertNotNull(existingMapping);
//        Assert.assertFalse(existingSubnets.isEmpty());
//        Assert.assertNotNull(targetHost);
//        Assert.assertFalse(interfaceList.isEmpty());
//        Assert.assertNotNull(netInterface);
//        Assert.assertFalse(cidrList.isEmpty());
//        Assert.assertFalse(ifaceName.isEmpty());
//        Assert.assertNotNull(cidr);
//        Assert.assertFalse(subnetBlock.isEmpty());
//    }
//
//    @Test
//    public void setSubnetMappingsTest() throws StmRollBackException {
//        Hostssubnet newHss = makeHostssubnet();
//        Hostssubnet beforeHss = stmAdapter.getSubnetMappings(config, TARGET_HOST);
//        List<String> cidrBlocksExisting = getCidrBlocks(EXISTING_MAPPINGS);
//        List<String> cidrBlocksBefore = getCidrBlocks(beforeHss);
//        List<String> cidrBlocksNew = getCidrBlocks(newHss);
//
//        Assert.assertEquals(cidrBlocksBefore, cidrBlocksExisting);
//        Assert.assertFalse(cidrBlocksBefore.equals(cidrBlocksNew));
//
//        stmAdapter.setSubnetMappings(config, newHss);
//        Hostssubnet setHss = stmAdapter.getSubnetMappings(config, TARGET_HOST);
//        List<String> cidrBlocksSet = getCidrBlocks(setHss);
//        Assert.assertEquals(cidrBlocksSet, cidrBlocksNew);
//
//        stmAdapter.setSubnetMappings(config, beforeHss);
//        setHss = stmAdapter.getSubnetMappings(config, TARGET_HOST);
//        cidrBlocksSet = getCidrBlocks(setHss);
//        Assert.assertEquals(cidrBlocksSet, cidrBlocksBefore);
//    }
//
//    private void createSubnetMapping() {
//        String subnetName = "TestSubnet";
//        String netInterfaceName = "TestNetInterface";
//        String cidrBlock = "IDK";
//        Hostssubnet hostssubnet = new Hostssubnet();
//        List<Hostsubnet> subnetList = new ArrayList<Hostsubnet>();
//        Hostsubnet hostsubnet = new Hostsubnet();
//        hostsubnet.setName(subnetName);
//    }
//
//
//    @Test
//    public void deleteSubnetMappingsTest() throws StmRollBackException {
//        Hostssubnet backupHss = null;
//        Hostssubnet beforeHss = stmAdapter.getSubnetMappings(config, TARGET_HOST);
//        List<String> cidrBlocksBefore = null;
//        try {
//            cidrBlocksBefore = getCidrBlocks(beforeHss);
//        } catch (Exception e) {
//        }
//
//        if (cidrBlocksBefore == null || cidrBlocksBefore.isEmpty()) {
//            stmAdapter.setSubnetMappings(config, makeHostssubnet());
//            backupHss = beforeHss;
//            beforeHss = stmAdapter.getSubnetMappings(config, TARGET_HOST);
//            cidrBlocksBefore = getCidrBlocks(beforeHss);
//        }
//
//        Assert.assertFalse(cidrBlocksBefore.isEmpty());
//
//        String interfaceName = beforeHss.getHostsubnets().get(0).getNetInterfaces().get(0).getName();
//        String firstBlock = cidrBlocksBefore.get(0);
//
//        Assert.assertFalse(interfaceName.isEmpty());
//        Assert.assertFalse(firstBlock.isEmpty());
//
//        Hostssubnet newHss = new Hostssubnet();
//        Hostsubnet newHs = new Hostsubnet();
//        NetInterface netInterface = new NetInterface();
//        Cidr cidr = new Cidr();
//
//        List<Hostsubnet> hostsubnetList = new ArrayList<Hostsubnet>();
//        List<NetInterface> netInterfaceList = new ArrayList<NetInterface>();
//        List<Cidr> cidrList = new ArrayList<Cidr>();
//
//        cidr.setBlock(firstBlock);
//        cidrList.add(cidr);
//
//        netInterface.setName(interfaceName);
//        netInterface.setCidrs(cidrList);
//        netInterfaceList.add(netInterface);
//
//        newHs.setName(TARGET_HOST);
//        newHs.setNetInterfaces(netInterfaceList);
//
//        hostsubnetList.add(newHs);
//        newHss.setHostsubnets(hostsubnetList);
//
//        stmAdapter.deleteSubnetMappings(config, newHss);
//
//        Hostssubnet afterHss = stmAdapter.getSubnetMappings(config, TARGET_HOST);
//        List<String> cidrBlocksAfter = getCidrBlocks(afterHss);
//        Assert.assertFalse(cidrBlocksAfter.contains(firstBlock));
//
//        if (backupHss != null)
//            stmAdapter.setSubnetMappings(config, backupHss);
//        else
//            stmAdapter.setSubnetMappings(config, beforeHss);
//    }
//
//    private List<String> getCidrBlocks(Hostssubnet hss) {
//        List<Cidr> cidrList = hss.getHostsubnets().get(0).getNetInterfaces().get(0).getCidrs();
//        List<String> cidrBlocks = new ArrayList<String>();
//        for (Cidr cidr : cidrList) {
//            cidrBlocks.add(cidr.getBlock());
//        }
//        return cidrBlocks;
//    }
//
//    private Hostssubnet makeHostssubnet() {
//        Hostssubnet newHss = new Hostssubnet();
//        Hostsubnet newHs = new Hostsubnet();
//        NetInterface netInterface = new NetInterface();
//        Cidr cidr = new Cidr();
//
//        List<Hostsubnet> hostsubnetList = new ArrayList<Hostsubnet>();
//        List<NetInterface> netInterfaceList = new ArrayList<NetInterface>();
//        List<Cidr> cidrList = new ArrayList<Cidr>();
//
//        cidr.setBlock("192.168.0.0/21");
//        cidrList.add(cidr);
//
//        netInterface.setName("eth0");
//        netInterface.setCidrs(cidrList);
//        netInterfaceList.add(netInterface);
//
//        newHs.setName(TARGET_HOST);
//        newHs.setNetInterfaces(netInterfaceList);
//
//        hostsubnetList.add(newHs);
//        newHss.setHostsubnets(hostsubnetList);
//
//        return newHss;
//    }
//
//    @Ignore
//    @Test
//    public void visualConfirmTest() throws StmRollBackException, InsufficientRequestException, StingrayRestClientObjectNotFoundException, StingrayRestClientException {
//        List<Child> trafficManagerList = stmClient.getTrafficManagers();
//
//        for (Child c : trafficManagerList) {
//            String trafficManagerName = c.getName();
//            TrafficManager trafficManager = stmClient.getTrafficManager(trafficManagerName);
//            TrafficManagerProperties trafficManagerProperties = trafficManager.getProperties();
//            TrafficManagerBasic trafficManagerBasic = trafficManagerProperties.getBasic();
//            List<TrafficManagerTrafficIp> trafficManagerTrafficIpList = trafficManagerBasic.getTrafficip();
//            System.out.println("Traffic Manager: " + trafficManagerName);
//
//            for (TrafficManagerTrafficIp trafficManagerTrafficIp : trafficManagerTrafficIpList) {
//                String interfaceName = trafficManagerTrafficIp.getName();
//                Set<String> masks = trafficManagerTrafficIp.getNetworks();
//                System.out.println("\tNetwork Interface: " + interfaceName);
//                System.out.println("\tSubnets:");
//                for (String mask : masks) {
//                    System.out.println("\t\t" + mask);
//                }
//            }
//        }
//    }
}
