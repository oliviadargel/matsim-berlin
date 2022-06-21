package org.matsim.prepare.network;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.utils.geometry.geotools.MGC;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.gis.ShapeFileReader;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class NetworkChanger {

	public static void rmTrafficFromRing(Network network){
		var shpFileName = "/net/ils/matsim-class/olivia/ring-001/input/Umweltzone.shp";

		var umweltzoneShape = ShapeFileReader.getAllFeatures(shpFileName);
		Geometry umweltzone = umweltzoneShape
				.stream()
				.map(simpleFeature -> (Geometry) simpleFeature.getDefaultGeometry())
				.collect(Collectors.toList()).get(0);

		var transformer = TransformationFactory.getCoordinateTransformation("EPSG:31468", "EPSG:25833");


		Coord transformedStart;
		Coord transformedTarget;
		Point startPoint;
		Point targetPoint;
		for (Link link: network.getLinks().values()) {
			// transform Startnode and Targetnode coordinates from the current link to the ShapeFile's Coordinate System
			transformedStart = transformer.transform(link.getFromNode().getCoord());
			transformedTarget = transformer.transform(link.getToNode().getCoord());
			startPoint = MGC.coord2Point(transformedStart);
			targetPoint = MGC.coord2Point(transformedTarget);

			// check whether Startnode or Targetnode of current link are inside the Berliner Ring
			if (umweltzone.contains(startPoint) || umweltzone.contains(targetPoint)){

				// delete car TransportMode, but leave other TransportModes
				Set<String> newModes = new HashSet<>(link.getAllowedModes());
				newModes.remove(TransportMode.car);

				// TODO maybe remove other TransportModes too?
//				newModes.remove(TransportMode.truck);
//				newModes.remove(TransportMode.motorcycle);

				link.setAllowedModes(newModes);
			}

		}
	}

	// For testing purposes:
	public static void main(String[] args) {
		var networkFileName = "berlin-v5.5-network.xml.gz";  // TODO add path to network file
		Network network = NetworkUtils.readNetwork(networkFileName);
		rmTrafficFromRing(network);
	}
}
