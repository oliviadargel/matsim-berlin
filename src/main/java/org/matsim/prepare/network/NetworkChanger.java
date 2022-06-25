package org.matsim.prepare.network;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.network.NetworkUtils;

import java.util.HashSet;
import java.util.Set;

public class NetworkChanger {


	public static void addA100Extension(Network network) {

		// set specification for all links
		Set<String> allowedModes = new HashSet<>();
		allowedModes.add(TransportMode.car);
		allowedModes.add(TransportMode.ride);
		allowedModes.add(TransportMode.motorcycle);
		allowedModes.add(TransportMode.truck);
		allowedModes.add(TransportMode.taxi);


		double freespeed = 80 / 3.6;
		double freespeed_as = 60 / 3.6;
		double lanes = 3;
		double capacity = lanes * 2 * 1000.0;
		double capacity_as = 4500;


		// set link specific properties and add links to network
		// ---- Abschnitt 16 (3.2 km) ----
//		{
			// Grenzallee to Sonnenallee
			Node sonnenalleeN1 = network.getFactory().createNode(Id.createNodeId("sonnenallee-nord1"), new Coord(4599632, 5816201));
			Node sonnenalleeN2 = network.getFactory().createNode(Id.createNodeId("sonnenallee-nord2"), new Coord(4599672, 5816235));

			Node sonnenalleeS1 = network.getFactory().createNode(Id.createNodeId("sonnenallee-sued1"), new Coord(4599661, 5816246));
			Node sonnenalleeS2 = network.getFactory().createNode(Id.createNodeId("sonnenallee-sued2"), new Coord(4599621, 5816212));

			network.addNode(sonnenalleeN1);
			network.addNode(sonnenalleeN2);
			network.addNode(sonnenalleeS1);
			network.addNode(sonnenalleeS2);

			Node startNode = network.getNodes().get(Id.get("206191207", Node.class));  // Motorway Grenzallee

			// add motorway north/east direction
			addLinkToNetwork(network, "bab100-grenzallee-sonnenallee",
						startNode, sonnenalleeN1, allowedModes, capacity, freespeed, lanes, 746.230);
			addLinkToNetwork(network, "bab100-sonnenallee-n",
					sonnenalleeN1, sonnenalleeN2, allowedModes, capacity, freespeed, lanes, 52.458);

			// add motorway south/west direction
			Node targetNode = network.getNodes().get(Id.get("27542427", Node.class));  // Motorway Grenzallee
			addLinkToNetwork(network, "bab100-sonnenallee-s",
					sonnenalleeS1, sonnenalleeS2, allowedModes, capacity, freespeed, lanes, 52.547);
			addLinkToNetwork(network, "bab100-sonnenallee-grenzallee",
					sonnenalleeS2, targetNode, allowedModes, capacity, freespeed, lanes, 746.417);

			// Sonnenallee
			// add AS for northern motorway
			// motorway to street
			Node node1 = network.getNodes().get(Id.get("31357357", Node.class));
			Node node2 = network.getNodes().get(Id.get("31390656", Node.class));
			addLinkToNetwork(network, "bab100-as-sonnenallee-n1",
				sonnenalleeN1, node1, allowedModes, capacity_as, freespeed_as, lanes, 46.891);

			addLinkToNetwork(network, "bab100-as-sonnenallee-n2",
				sonnenalleeN1, node2, allowedModes, capacity_as, freespeed_as, lanes, 29.027);

			// street to motorway
			addLinkToNetwork(network, "bab100-as-sonnenallee-n3",
					node2, sonnenalleeN2, allowedModes, capacity_as, freespeed_as, lanes, 41.814);

			addLinkToNetwork(network, "bab100-as-sonnenallee-n4",
					node1, sonnenalleeN2, allowedModes, capacity_as, freespeed_as, lanes, 40.529);

			// add AS for southern motorway
			// motorway to street
			addLinkToNetwork(network, "bab100-as-sonnenallee-s1",
					sonnenalleeS1, node1, allowedModes, capacity_as, freespeed_as, lanes, 27.534);

			addLinkToNetwork(network, "bab100-as-sonnenallee-s2",
					sonnenalleeS1, node2, allowedModes, capacity_as, freespeed_as, lanes, 34.344);

			// street to motorway
			addLinkToNetwork(network, "bab100-as-sonnenallee-s3",
					node1, sonnenalleeS2, allowedModes, capacity_as, freespeed_as, lanes, 38.131);

			addLinkToNetwork(network, "bab100-as-sonnenallee-s4",
					node2, sonnenalleeS2, allowedModes, capacity_as, freespeed_as, lanes, 20.381);


//			// Sonnenallee to Treptower Park
			Node trParkN1 = network.getFactory().createNode(Id.createNodeId("trpark-nord1"), new Coord(4599218,5818728));
			Node trParkN2 = network.getFactory().createNode(Id.createNodeId("trpark-nord2"), new Coord(4599232,5818744));

			Node trParkS1 = network.getFactory().createNode(Id.createNodeId("trpark-sued1"), new Coord(4599229,5818746));
			Node trParkS2 = network.getFactory().createNode(Id.createNodeId("trpark-sued2"), new Coord(4599215,5818729));

			network.addNode(trParkN1);
			network.addNode(trParkN2);
			network.addNode(trParkS1);
			network.addNode(trParkS2);

			addLinkToNetwork(network, "bab100-sonnenallee-trpark",
					sonnenalleeN2, trParkN1, allowedModes, capacity, freespeed, lanes, 2525.724);
			addLinkToNetwork(network, "bab100-trpark-n",
					trParkN1, trParkN2, allowedModes, capacity, freespeed, lanes, 21.320);

			// add motorway south/west direction
			addLinkToNetwork(network, "bab100-trpark-s",
					trParkS1, trParkS2, allowedModes, capacity, freespeed, lanes, 21.936);
			addLinkToNetwork(network, "bab100-trpark-sonnenallee",
					trParkS2, sonnenalleeS1, allowedModes, capacity, freespeed, lanes, 2523.780);

			// Treptower Park
			// add AS for northern motorway
			// motorway to street
			node1 = network.getNodes().get(Id.get("29787110", Node.class));
			node2 = network.getNodes().get(Id.get("29787103", Node.class));
			addLinkToNetwork(network, "bab100-as-trpark-n1",
					trParkN1, node1, allowedModes, capacity_as, freespeed_as, lanes, 17.237);

			addLinkToNetwork(network, "bab100-as-trpark-n2",
					trParkN1, node2, allowedModes, capacity_as, freespeed_as, lanes, 25.292);

			// street to motorway
			addLinkToNetwork(network, "bab100-as-trpark-n3",
					node1, trParkN2, allowedModes, capacity_as, freespeed_as, lanes, 6.519);

			addLinkToNetwork(network, "bab100-as-trpark-n4",
					node2, trParkN2, allowedModes, capacity_as, freespeed_as, lanes, 25.440);

			// add AS for northern motorway
			// motorway to street
			addLinkToNetwork(network, "bab100-as-trpark-s1",
					trParkS1, node1, allowedModes, capacity_as, freespeed_as, lanes, 4.296);

			addLinkToNetwork(network, "bab100-as-trpark-s2",
					trParkS1, node2, allowedModes, capacity_as, freespeed_as, lanes, 22.075);


			// street to motorway
			addLinkToNetwork(network, "bab100-as-trpark-s3",
					node1, trParkS2, allowedModes, capacity_as, freespeed_as, lanes, 17.888);

			addLinkToNetwork(network, "bab100-as-trpark-s4",
					node2, trParkS2, allowedModes, capacity_as, freespeed_as, lanes, 23.262);

//		}
//
//		// ---- Abschnitt 17 (4.1 km) ----
//		{
//			// Treptower Park to Ostkreuz
//			startNode1 = null;  // 29787110 nach Norden
//			startNode2 = null;  // 29787103 nach Sueden
//			targetNode1 = null;  // 4317221792    --> Ostkreuz hat nur einen Punkt auf der Markgrafenstr, einen auf Hauptstr
//			targetNode2 = null;  // Hauptstr: 4370346530 nach Osten, 4245068305   nach Westen
			Node ostkreuzN1 = network.getFactory().createNode(Id.createNodeId("ostkreuz-nord1"), new Coord(4599744, 5819705));
			Node ostkreuzN2 = network.getFactory().createNode(Id.createNodeId("ostkreuz-nord2"), new Coord(4599807, 5819805));
			Node ostkreuzS1 = network.getFactory().createNode(Id.createNodeId("ostkreuz-sued1"), new Coord(4599793, 5819813));
			Node ostkreuzS2 = network.getFactory().createNode(Id.createNodeId("ostkreuz-sued2"), new Coord(4599730, 5819713));

			network.addNode(ostkreuzN1);
			network.addNode(ostkreuzN2);
			network.addNode(ostkreuzS1);
			network.addNode(ostkreuzS2);

			addLinkToNetwork(network, "bab100-trpark-ostkreuz",
					trParkN2, ostkreuzN1, allowedModes, capacity, freespeed, lanes, 1088.652);
			addLinkToNetwork(network, "bab100-ostkreuz-n",
					ostkreuzN1, ostkreuzN2, allowedModes, capacity, freespeed, lanes, 118.073);

			// add motorway south/west direction
			addLinkToNetwork(network, "bab100-ostkreuz-s",
					ostkreuzS1, ostkreuzS2, allowedModes, capacity, freespeed, lanes, 117.805);
			addLinkToNetwork(network, "bab100-ostkreuz-trpark",
					ostkreuzS2, trParkS1, allowedModes, capacity, freespeed, lanes, 1087.971);

			// Ostkreuz
			// add AS for northern motorway
			// motorway to street
			node1 = network.getNodes().get(Id.get("4370346530", Node.class));  // Hauptstr. -> east
			Node node3 = network.getNodes().get(Id.get("4245068305", Node.class));  // Hauptstr. -> west
			node2 = network.getNodes().get(Id.get("4317221792", Node.class));  // Markgrafenstr.
			addLinkToNetwork(network, "bab100-as-ostkreuz-n1",
					ostkreuzN1, node1, allowedModes, capacity_as, freespeed_as, lanes,  204.254);

			addLinkToNetwork(network, "bab100-as-ostkreuz-n2",
					ostkreuzN1, node2, allowedModes, capacity_as, freespeed_as, lanes, 399.905);

			// street to motorway
			addLinkToNetwork(network, "bab100-as-ostkreuz-n3",
					node2, ostkreuzN2, allowedModes, capacity_as, freespeed_as, lanes, 517.954);

			addLinkToNetwork(network, "bab100-as-ostkreuz-n4",
					node3, ostkreuzN2, allowedModes, capacity_as, freespeed_as, lanes, 116.594);

			// add AS for southern motorway
			// motorway to street
			addLinkToNetwork(network, "bab100-as-ostkreuz-s1",
					ostkreuzS1, node1, allowedModes, capacity_as, freespeed_as, lanes, 130.743);

			addLinkToNetwork(network, "bab100-as-ostkreuz-s2",
					ostkreuzS1, node2, allowedModes, capacity_as, freespeed_as, lanes, 517.024);

			// street to motorway
			addLinkToNetwork(network, "bab100-as-ostkreuz-s3",
					node3, ostkreuzS2, allowedModes, capacity_as, freespeed_as, lanes, 213.123);

			addLinkToNetwork(network, "bab100-as-ostkreuz-s4",
					node2, ostkreuzS2, allowedModes, capacity_as, freespeed_as, lanes, 518.711);



//			// Ostkreuz to Frankfurter Allee
//			targetNode2 = null;  //  Kreuzung Moellendorffstr/Frankfurter Allee (von unten links gegen den Uhrzeigersinn):
//			// 12614683, 598234402, 288267826, 29784919
			Node frankfurterN1 = network.getFactory().createNode(Id.createNodeId("frankfurter-nord1"), new Coord(4600297, 5820952));
			Node frankfurterN2 = network.getFactory().createNode(Id.createNodeId("frankfurter-nord2"), new Coord(4600304, 5821024));
			Node frankfurterS1 = network.getFactory().createNode(Id.createNodeId("frankfurter-sued1"), new Coord(4600288, 5821026));
			Node frankfurterS2 = network.getFactory().createNode(Id.createNodeId("frankfurter-sued2"), new Coord(4600281, 5820965));

			network.addNode(frankfurterN1);
			network.addNode(frankfurterN2);
			network.addNode(frankfurterS1);
			network.addNode(frankfurterS2);

			addLinkToNetwork(network, "bab100-ostkreuz-frankfurter",
					ostkreuzN2, frankfurterN1, allowedModes, capacity, freespeed, lanes, 1246.026);
			addLinkToNetwork(network, "bab100-frankfurter-n",
					frankfurterN1, frankfurterN2, allowedModes, capacity, freespeed, lanes, 72.416);

			// add motorway south/west direction
			addLinkToNetwork(network, "bab100-frankfurter-s",
					frankfurterS1, frankfurterS2, allowedModes, capacity, freespeed, lanes, 61.390);
			addLinkToNetwork(network, "bab100-frankfurter-ostkreuz",
					frankfurterS2, ostkreuzS1, allowedModes, capacity, freespeed, lanes, 1250.446);

			// Frankfurter Allee
			// add AS for northern motorway
			// motorway to street
			node1 = network.getNodes().get(Id.get("598234402", Node.class));  // Moellendorffstr. -> north
			node2 = network.getNodes().get(Id.get("12614683", Node.class));  // Moellendorffstr. -> south
			node3 = network.getNodes().get(Id.get("1791505417", Node.class));  // Guertelstr. -> both directions
			addLinkToNetwork(network, "bab100-as-frankfurter-n1",
					frankfurterN1, node1, allowedModes, capacity_as, freespeed_as, lanes,  133.202);

			addLinkToNetwork(network, "bab100-as-frankfurter-n2",
					frankfurterN1, node3, allowedModes, capacity_as, freespeed_as, lanes, 69.908);

			// street to motorway
			addLinkToNetwork(network, "bab100-as-frankfurter-n3",
					node3, frankfurterN2, allowedModes, capacity_as, freespeed_as, lanes, 140.789);

			addLinkToNetwork(network, "bab100-as-frankfurter-n4",
					node2, frankfurterN2, allowedModes, capacity_as, freespeed_as, lanes, 69.509);

			// add AS for southern motorway
			// motorway to street
			addLinkToNetwork(network, "bab100-as-frankfurter-s1",
					frankfurterS1, node1, allowedModes, capacity_as, freespeed_as, lanes, 96.193);

			addLinkToNetwork(network, "bab100-as-frankfurter-s2",
					frankfurterS1, node3, allowedModes, capacity_as, freespeed_as, lanes, 140.105);

			// street to motorway
			addLinkToNetwork(network, "bab100-as-frankfurter-s3",
					node2, frankfurterS2, allowedModes, capacity_as, freespeed_as, lanes, 125.315);

			addLinkToNetwork(network, "bab100-as-frankfurter-s4",
					node3, frankfurterS2, allowedModes, capacity_as, freespeed_as, lanes, 78.587);



//			// Frankfurter Allee to Storkower Strasse (28373619, 28373623)
			Node storkowerN = network.getFactory().createNode(Id.createNodeId("storkower-nord"), new Coord(4600508, 5822103));
			Node storkowerS = network.getFactory().createNode(Id.createNodeId("storkower-sued"), new Coord(4600494, 5822103));

			network.addNode(storkowerN);
			network.addNode(storkowerS);

			targetNode = network.getNodes().get(Id.get("28373623", Node.class));  // Storkower Str

			addLinkToNetwork(network, "bab100-frankfurter-storkower",
					frankfurterN2, storkowerN, allowedModes, capacity, freespeed, lanes, 1096.887);
			addLinkToNetwork(network, "bab100-as-storkower-n",
					storkowerN, targetNode, allowedModes, capacity_as, freespeed_as, lanes, 45.345);


			// add motorway south/west direction to network
			startNode = network.getNodes().get(Id.get("28373619", Node.class));  // Storkower Str
			addLinkToNetwork(network, "bab100-as-storkower-s",
					startNode, storkowerS, allowedModes, capacity_as, freespeed_as, lanes, 40.081);
			addLinkToNetwork(network, "bab100-storkower-frankfurter",
					storkowerS, frankfurterS1, allowedModes, capacity, freespeed, lanes,1096.549);

//		}
//		NetworkUtils.writeNetwork(network, "scenarios/berlin-bab100-network-out.xml");
	}

	private static void addLinkToNetwork(Network network,
	                                     String linkId,
	                                     Node startNode, Node targetNode,
	                                     Set<String> allowedModes,
	                                     double capacity,
	                                     double freespeed,
	                                     double lanes,
	                                     double length){

		Link newLink = network.getFactory().createLink(Id.createLinkId(linkId), startNode, targetNode);

		// make sure that start and target node are already in the network
		if (!network.getNodes().containsValue(startNode)){
			network.addNode(startNode);
		}
		if (!network.getNodes().containsValue(targetNode)){
			network.addNode(targetNode);
		}


		// add start and target node to link
		newLink.setFromNode(startNode);
		newLink.setToNode(targetNode);

		newLink.setAllowedModes(allowedModes);
		newLink.setCapacity(capacity);
		newLink.setFreespeed(freespeed);
		newLink.setLength(length);
		newLink.setNumberOfLanes(lanes);

		network.addLink(newLink);
	}

	public static void main(String[] args) {
		Network network = NetworkUtils.readNetwork(); // TODO add path to network file
		addA100Extension(network);
	}
}
