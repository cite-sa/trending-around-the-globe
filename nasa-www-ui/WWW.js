var wwd = undefined;
/*var drawContext = undefined;*/

var locations = undefined;

var locationsLayer = undefined;
var placemarkLayer = undefined;;
var trendsLayer = undefined;
var annotationsLayer = undefined;


$(document).ready(function () {
	
    var location = {
        long: undefined,
        lat: undefined
    };

    /*var canvas = document.getElementById("canvasOne");
    var gl = canvas.getContext('webgl');
    drawContext = new WorldWind.DrawContext(gl);*/
    //console.log(drawContext);

    /*initiate_geolocation();*/
    initWww();
    initDOM();

    function initWww(position) {

        wwd = new WorldWind.WorldWindow("canvasOne");
        
        wwd.deepPicking = true;

        /*location.latitude = position.coords.latitude;
        location.longitude = position.coords.longitude;
        wwd.navigator.lookAtLocation.latitude = location.latitude;
        wwd.navigator.lookAtLocation.longitude = location.longitude;
        wwd.navigator.range = 30e3; // 30km above the ellipsoid
*/
        WorldWind.Logger.setLoggingLevel(WorldWind.Logger.LEVEL_WARNING);

        WorldWind.configuration.gpuCacheSize = 250e6; // 250 MB

        /*var bMNGLayer = wwd.addLayer(new WorldWind.BMNGLayer());
        var BmngOneImageLayer = wwd.addLayer(new WorldWind.BMNGOneImageLayer());*/
        var bingAerialLayer = wwd.addLayer(new WorldWind.BingAerialLayer());
        /*var bingRoadsLayerObj = new WorldWind.BingRoadsLayer();*/
        /*bingRoadsLayerObj.opacity = 0.5;*/
        /*var bingRoadsLayer = wwd.addLayer(bingRoadsLayerObj);*/
        /*var blueMarbleLayer = wwd.addLayer(new WorldWind.BlueMarbleLayer(wwd));*/
        
        
        /*var bingAerialWithLabelsLayer = wwd.addLayer(new WorldWind.BingAerialWithLabelsLayer());*/
        /*var compassLayer = wwd.addLayer(new WorldWind.CompassLayer());*/
        
        var coordinatesLayer = wwd.addLayer(new WorldWind.CoordinatesDisplayLayer(wwd));
        var viewControlsLayer = wwd.addLayer(new WorldWind.ViewControlsLayer(wwd));
        
        initEventListeners();
        
        locationsLayer = new WorldWind.RenderableLayer("Locations");
        wwd.addLayer(locationsLayer);
        
        placemarkLayer = new WorldWind.RenderableLayer("Placemarks");
        wwd.addLayer(placemarkLayer);
        
        trendsLayer = new WorldWind.RenderableLayer("Trends");
        wwd.addLayer(trendsLayer);
        
        annotationsLayer = new WorldWind.RenderableLayer("TrendAnnotations");
        wwd.addLayer(annotationsLayer);
        
        wwd.redraw();
        
        getAndDrawAllLocations();       
        
    }
    
    function initDOM() {
    	createModal("city-modal");
    }
    
    function initEventListeners() {
    	
    	var highlightedItems = [];
    	
    	var handlePick = function(recognizer) {

            var x = recognizer.clientX,
                y = recognizer.clientY;

            var redrawRequired = highlightedItems.length > 0;

            for (var h = 0; h < highlightedItems.length; h++) {
                highlightedItems[h].highlighted = false;
            }
            
            highlightedItems = [];

            var pickList = wwd.pick(wwd.canvasCoordinates(x, y));
            if (pickList.objects.length > 0) {
                redrawRequired = true;
            }

            if (pickList.objects.length > 0) {
            	console.log(pickList);
                for (var p = 0; p < pickList.objects.length; p++) {
                    pickList.objects[p].userObject.highlighted = true;

                    highlightedItems.push(pickList.objects[p].userObject);
                    
                    if (pickList.objects.length == 1 && pickList.objects[p].isTerrain) {
                    	annotationsLayer.removeAllRenderables();
                    } else {

                    	console.log(pickList.objects[p].userObject.type)
	                    if (pickList.objects[p].userObject.type === "trend") {
	                    	annotationsLayer.removeAllRenderables();
	                    	console.log(pickList.objects[p].userObject.value.name)
	                    	getAnnotationData(pickList.objects[p].userObject.value.name, pickList.objects[p].position);
	                    } else if (pickList.objects[p].userObject.pickDelegate != undefined &&
	                    		pickList.objects[p].userObject.pickDelegate.type === "trendUrl") {
	                    	console.log("URL clicked: " + pickList.objects[p].userObject.pickDelegate.value)
	                    	window.open(pickList.objects[p].userObject.pickDelegate.value, "_blank");
	                    } else if (pickList.objects[p].userObject.type === "city") {
	                    	console.log("CITY");
	                    	buildModal(pickList.objects[p].userObject.value);
	                    }
                    }
                }
            }

            if (redrawRequired) {
                wwd.redraw();
            }

            /*if (pickList.objects.length == 1 && pickList.objects[0].isTerrain) {
                var position = pickList.objects[0].position;
                wwd.goTo(new WorldWind.Location(position.latitude, position.longitude));
            }*/
            
        }
    	var tapRecognizer = new WorldWind.TapRecognizer(wwd, handlePick);
        tapRecognizer.enabled = true;

        var clickRecognizer = new WorldWind.ClickRecognizer(wwd, handlePick);
        clickRecognizer.enabled = true;

        var eventListenerClick = wwd.addEventListener("mouseclick", handlePick);
    }

    /*function initiate_geolocation() {
        navigator.geolocation.getCurrentPosition(initWww, handle_errors);
    };*/

    function handle_errors(error) {

        switch (error.code) {
            case error.PERMISSION_DENIED:
                alert("user did not share geolocation data");
                break;

            case error.POSITION_UNAVAILABLE:
                alert("could not detect current position");
                break;

            case error.TIMEOUT:
                alert("retrieving position timed out");
                break;

            default:
                alert("unknown error");
                break;
        }
    };
    
});

//document.ready end :)

function drawSurfacePolygon(layer, location, boundary) {
	var attributes = new WorldWind.ShapeAttributes(null);
    attributes.outlineColor = WorldWind.Color.BLUE;
    attributes.drawInterior = false;
    attributes.drawOutline = false; 
    attributes.applyLighting = false;

    /*var highlightAttributes = new WorldWind.ShapeAttributes(attributes);
    highlightAttributes.interiorColor = new WorldWind.Color(1, 1, 1, 1);*/

    var polygon = new WorldWind.SurfacePolygon(boundary, attributes);
    polygon.enabled = true;
    /*polygon.highlightAttributes = highlightAttributes;*/
    
    
	/*var polygonAltitude = 1000;

	var polygonAttributes = new WorldWind.ShapeAttributes(null);
    polygonAttributes.drawInterior = true;
    polygonAttributes.drawOutline = true;

	polygonAttributes.interiorColor = new WorldWind.Color( 1, 2, 4,0.5);
	polygonAttributes.applyLighting = false;*/
	/*polygon.attributes = polygonAttributes;*/
	
    /*var polygon = new WorldWind.SurfacePolygon(boundaries, attributes);*/

    //polygon.altitudeMode = WorldWind.RELATIVE_TO_GROUND; // ABSOLUTE // , // RELATIVE_TO_GROUND // , //CLAMP_TO_GROUND
    //console.log(location)
    polygon.displayName = location.name + ", " + location.country;
    //polygon.extrude = true; // extrude the polygon  edges to the ground

    

    /*var highlightAttributes = new WorldWind.ShapeAttributes(polygonAttributes);
    highlightAttributes.outlineColor = WorldWind.Color.RED;
    highlightAttributes.interiorColor = new WorldWind.Color( 1, 1, 1,0.5);
    polygon.highlightAttributes = highlightAttributes; */
	
	layer.addRenderable(polygon);
}

function drawPlacemark(layer, location) {
	var centroidImageCities = "images/pushpins/twitter_logo.png";
    var centroidImageCountries = "images/pushpins/plain-red.png";

    var pinLibrary = WorldWind.configuration.baseUrl + "images/pushpins/";
    var placemarkAttributes = new WorldWind.PlacemarkAttributes(null);
    
    var placemark;
    var highlightAttributes;
    var placemarkPickDelegate = undefined;

    var latitude = location.centroidLatitude;
    var longitude = location.centroidLongitude;

    placemarkAttributes.depthTest = false;
    placemarkAttributes.imageScale = 0.08;
    placemarkAttributes.imageOffset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.3, WorldWind.OFFSET_FRACTION, 0.0);
    /*placemarkAttributes.imageColor = WorldWind.Color.WHITE;*/
    placemarkAttributes.labelAttributes.offset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.5, WorldWind.OFFSET_FRACTION, 1.0);

    if (location.parentID == 1) {
    	
        placemarkAttributes.labelAttributes.color = WorldWind.Color.WHITE;
        /*placemarkAttributes.imageSource = centroidImageCountries;*/
    }
    else {
    	
        placemarkAttributes.labelAttributes.color = WorldWind.Color.CYAN;
        placemarkAttributes.imageSource = centroidImageCities;
        placemarkPickDelegate = {type: "city", value: location};
        
    }
    
    placemarkAttributes.drawLeaderLine = true;
    placemarkAttributes.leaderLineAttributes.outlineColor = WorldWind.Color.RED;

    var placemarkPositon = new WorldWind.Position(location.centroidLatitude, location.centroidLongitude, 0);

    placemark = new WorldWind.Placemark(placemarkPositon, true, null);
    placemark.pickDelegate = placemarkPickDelegate;
    placemark.label = location.name;
    placemark.altitudeMode = WorldWind.RELATIVE_TO_GROUND;

    //placemarkAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);

    placemark.attributes = placemarkAttributes;

    highlightAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);
    highlightAttributes.imageScale = 0.6;// 1.2
    placemark.highlightAttributes = highlightAttributes;
    
    layer.addRenderable(placemark);
}

function drawLocations(locationsList) {
	locations = locationsList;
	
    /*var locationsStorage = [];*/

    $.each(locations, function (index, location) {
    	
    	if (location.woeid != 1 && location.parentID != 1) {

	        var boundaries = [];
	        boundaries.push(new WorldWind.Position(location.southEastLatitude, location.southEastLongitude, 0));
	        boundaries.push(new WorldWind.Position(location.northEastLatitude, location.northEastLongitude, 0));
	        boundaries.push(new WorldWind.Position(location.northWestLatitude, location.northWestLongitude, 0));
	        boundaries.push(new WorldWind.Position(location.southWestLatitude, location.southWestLongitude, 0));
	        
	        drawSurfacePolygon(locationsLayer, location, boundaries);
	        
	        drawPlacemark(placemarkLayer, location);
    	}
    	
    });
    wwd.redraw();
    //drawTrends(locations);
    //getAndDrawAllTrends();
};

function pickShapes() {
	/*console.log(wwd.viewport.width);
	console.log(wwd.viewport.height);*/
	
	/*var coord = wwd.canvasCoordinates(wwd.viewport.width, wwd.viewport.height);
	console.log(coord)*/
	
	/*var rectRadius = 50,*/
	/*var pickPoint = wwd.canvasCoordinates(0, 0);*/
    var pickRectangle = new WorldWind.Rectangle(0, 0-wwd.viewport.height, wwd.viewport.width, wwd.viewport.height);
	console.log("MinX: " + pickRectangle.getMinX());
	console.log("MinY: " + pickRectangle.getMinY());
	console.log("MaxX: " + pickRectangle.getMaxX());
	console.log("MaxY: " + pickRectangle.getMaxY());
	
    var shapes = wwd.pickShapesInRegion(pickRectangle);
	/*console.log(shapes.objects.length)*/
    console.log(shapes);
}

function drawTrends(locations) {

    $.each(locations, function(index, location) {
    	setTimeout(function () {
    		getAndDrawTrends(location);
    	  }, 1000);
    });
};

function getAndDrawAllTrends() {
	listAllTrends(drawPlacemarksForTrendsAllLocations, limit);
};

function getAndDrawAllLocations() {
	getAllLocations(drawLocations);
    
};

function getAndDrawTrends(location) {
	console.log("Range: " + wwd.navigator.range);
    if (wwd.navigator.range > 10000) {
    	
        var limit = 4;
        
        topTrendsPerLocation(location, limit, 1, drawPlacemarksForTrends);
        
    } else if (wwd.navigator.range > 5000) {
    	
        var limit = 3;
        topTrendsPerLocation(location, limit, 1, drawPlacemarksForTrends);
    } else if (wwd.navigator.range > 1000) {
    	
        var limit = 2;
        topTrendsPerLocation(location, limit, 1, drawPlacemarksForTrends);
    } else {
    	
        var limit = 1;
        topTrendsPerLocation(location, limit, 1, drawPlacemarksForTrends);
    }
    
};

function drawPlacemarksForTrends(trends, location) {
	
    /*var textAttributes = new WorldWind.TextAttributes(null);
    textAttributes.color = WorldWind.Color.GREEN;
    textAttributes.depthTest = false;*/


    var coordinates = generateRandomPointsInPolygon(
		location.southWestLatitude, location.southWestLongitude,
		location.northEastLatitude, location.northEastLongitude,
		trends.length
    );
    
    /*if (Object.keys(trends).length > 0) {
	    console.log("LOCATION");
		console.log(location.name);
		console.log("TRENDS")
		console.log(trends);
	    console.log("COORDINATES");
	    console.log(coordinates);
    }*/
    
    $.each(trends, function(index, trend) {
    	
        var trendPosition = new WorldWind.Position(coordinates[index].lat, coordinates[index].long, 0);
        
        var pinLibrary = WorldWind.configuration.baseUrl + "images/pushpins/";
        var placemarkAttributes = new WorldWind.PlacemarkAttributes(null);
        
        var placemark;
        var highlightAttributes;


        placemarkAttributes.depthTest = false;
        placemarkAttributes.imageScale = 1;
        placemarkAttributes.imageOffset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.3, WorldWind.OFFSET_FRACTION, 0.0);
        /*placemarkAttributes.imageColor = WorldWind.Color.WHITE;*/
        placemarkAttributes.labelAttributes.offset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.5, WorldWind.OFFSET_FRACTION, 1.0);

        placemarkAttributes.labelAttributes.color = WorldWind.Color.GREEN;
        /*placemarkAttributes.imageSource = centroidImageCountries;*/
        
        placemarkAttributes.drawLeaderLine = true;
        placemarkAttributes.leaderLineAttributes.outlineColor = WorldWind.Color.RED;


        placemark = new WorldWind.Placemark(trendPosition, true, null);

        placemark.label = trend.name;
        placemark.pickDelegate = {"type": "trend", "value": trend};
        placemark.altitudeMode = WorldWind.RELATIVE_TO_GROUND;

        //placemarkAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);

        placemark.attributes = placemarkAttributes;

        highlightAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);
        highlightAttributes.imageScale = 0.6;// 1.2
        placemark.highlightAttributes = highlightAttributes;
        
        trendsLayer.addRenderable(placemark);
    });
    wwd.redraw();
};

/*function drawPlacemarksForTrendsAllLocations(trends, limit) {
	$.each(locations, function() {
		var trendsInLocation = trends.filter(function(trend) {
			if (trend.locations.c)
		});
		var coordinates = generateRandomPointsInPolygon(
    			locations[i].southWestLatitude, locations[i].southWestLongitude,
    			locations[i].northEastLatitude, locations[i].northEastLongitude,
    			trends.length
    	    );
	});
	
	
	
	
	
	$.each(trends, function(index, trend) {
	
		$.each(trend.locations, function(index, location) {
			for (var i = 0; i < locations.length; i++) {
				if (locations[i].lID == location.lID) {

			    	var coordinates = generateRandomPointsInPolygon(
			    			locations[i].southWestLatitude, locations[i].southWestLongitude,
			    			locations[i].northEastLatitude, locations[i].northEastLongitude,
			    			limit
			    	    );
			    	
			    	var trendPosition = new WorldWind.Position(coordinates[index].lat, coordinates[index].long, 0);
			        console.log(location);
			        console.log(trendPosition);
			        
			        
			        
			        var pinLibrary = WorldWind.configuration.baseUrl + "images/pushpins/";
			        var placemarkAttributes = new WorldWind.PlacemarkAttributes(null);
			        
			        var placemark;
			        var highlightAttributes;


			        placemarkAttributes.depthTest = false;
			        placemarkAttributes.imageScale = 1;
			        placemarkAttributes.imageOffset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.3, WorldWind.OFFSET_FRACTION, 0.0);
			        placemarkAttributes.imageColor = WorldWind.Color.WHITE;
			        placemarkAttributes.labelAttributes.offset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.5, WorldWind.OFFSET_FRACTION, 1.0);

			        placemarkAttributes.labelAttributes.color = WorldWind.Color.GREEN;
			        placemarkAttributes.imageSource = centroidImageCountries;
			        
			        placemarkAttributes.drawLeaderLine = true;
			        placemarkAttributes.leaderLineAttributes.outlineColor = WorldWind.Color.RED;


			        placemark = new WorldWind.Placemark(trendPosition, true, null);

			        placemark.label = trend.name;
			        placemark.pickDelegate = {"type": "trend", "value": trend};
			        placemark.altitudeMode = WorldWind.RELATIVE_TO_GROUND;

			        //placemarkAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);

			        placemark.attributes = placemarkAttributes;

			        highlightAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);
			        highlightAttributes.imageScale = 0.6;// 1.2
			        placemark.highlightAttributes = highlightAttributes;
			        
			        trendsLayer.addRenderable(placemark);
			        
			        break;

			    }
			}

    	
		});
    });

    var coordinates = generateRandomPointsInPolygon(
		location.southWestLatitude, location.southWestLongitude,
		location.northEastLatitude, location.northEastLongitude,
		trends.length
    );
    
    if (Object.keys(trends).length > 0) {
	    console.log("LOCATION");
		console.log(location.name);
		console.log("TRENDS")
		console.log(trends);
	    console.log("COORDINATES");
	    console.log(coordinates);
    }
    
    $.each(trends, function(index, trend) {
    	
        var trendPosition = new WorldWind.Position(coordinates[index].lat, coordinates[index].long, 0);
        console.log(location);
        console.log(trendPosition);
        
        
        
        var pinLibrary = WorldWind.configuration.baseUrl + "images/pushpins/";
        var placemarkAttributes = new WorldWind.PlacemarkAttributes(null);
        
        var placemark;
        var highlightAttributes;


        placemarkAttributes.depthTest = false;
        placemarkAttributes.imageScale = 1;
        placemarkAttributes.imageOffset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.3, WorldWind.OFFSET_FRACTION, 0.0);
        placemarkAttributes.imageColor = WorldWind.Color.WHITE;
        placemarkAttributes.labelAttributes.offset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.5, WorldWind.OFFSET_FRACTION, 1.0);

        placemarkAttributes.labelAttributes.color = WorldWind.Color.GREEN;
        placemarkAttributes.imageSource = centroidImageCountries;
        
        placemarkAttributes.drawLeaderLine = true;
        placemarkAttributes.leaderLineAttributes.outlineColor = WorldWind.Color.RED;


        placemark = new WorldWind.Placemark(trendPosition, true, null);

        placemark.label = trend.name;
        placemark.pickDelegate = {"type": "trend", "value": trend};
        placemark.altitudeMode = WorldWind.RELATIVE_TO_GROUND;

        //placemarkAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);

        placemark.attributes = placemarkAttributes;

        highlightAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);
        highlightAttributes.imageScale = 0.6;// 1.2
        placemark.highlightAttributes = highlightAttributes;
        
        trendsLayer.addRenderable(placemark);
    });
    wwd.redraw();
};*/

function drawAnnotation(annotationData, position) {
	
	console.log("ANNOTATION")
	console.log(annotationData)
	
	var annotationAttributes = new WorldWind.AnnotationAttributes(null);
	
    annotationAttributes.cornerRadius = 8;
    annotationAttributes.backgroundColor = WorldWind.Color.WHITE;
    /*annotationAttributes.drawLeader = true;*/
    /*annotationAttributes.leaderGapWidth = 40;
    annotationAttributes.leaderGapHeight = 30;*/
    annotationAttributes.opacity = 1;
    annotationAttributes.scale = 1;
    annotationAttributes.width = 200;
    annotationAttributes.height = 100;
    annotationAttributes.textAttributes.color = WorldWind.Color.BLACK;
    annotationAttributes.insets = new WorldWind.Insets(10, 10, 10, 10);

    annotation = new WorldWind.Annotation(position, annotationAttributes);
    annotation.label = annotationData[0].name;
    annotation.pickDelegate = {"type": "trendUrl", "value": annotationData[0].url}
    
    /*annotations.push(annotation);*/
    
    annotationsLayer.addRenderable(annotation);
}

function generateRandomPointsInPolygon(southWestLat, southWestLong, northEastLat, northEastLong, numOfPoints) {
	var pointsGeoJSON = turf.random('points', numOfPoints, {
		  bbox: [southWestLat, southWestLong, northEastLat, northEastLong]
		});
	
	var points = [];
	$.each(pointsGeoJSON.features, function(index, feature) {
		points.push({
			lat: feature.geometry.coordinates[0],
			long: feature.geometry.coordinates[1]
		});
	});
	
	return points;
}



/*var polygon = new WorldWind.Polygon(boundaries,null);

polygon.altitudeMode = WorldWind.RELATIVE_TO_GROUND; // ABSOLUTE // , // RELATIVE_TO_GROUND // , //CLAMP_TO_GROUND
polygon.displayName = value.name;
polygon.extrude = true; // extrude the polygon  edges to the ground

var polygonAttributes = new WorldWind.ShapeAttributes( null);
polygonAttributes.drawInterior = true;
polygonAttributes.drawOutline = true;*/

/* if (value.parentID == 1){
	polygonAttributes.outlineColor = WorldWind.Color.BLUE;
}
else {
	polygonAttributes.outlineColor = WorldWind.Color.CYAN;
}*/

 /*polygonAttributes.interiorColor = new WorldWind.Color( 1, 2, 4,0.5);
 polygonAttributes.drawVerticals = polygon.extrude;
 polygonAttributes.applyLighting = false;
 polygonAttributes.displayName = value.name;
 polygon.attributes = polygonAttributes;*/

/*var highlightAttributes = new WorldWind.ShapeAttributes(polygonAttributes);
highlightAttributes.outlineColor = WorldWind.Color.RED;
highlightAttributes.interiorColor = new WorldWind.Color( 1, 1, 1,0.5);
polygon.highlightAttributes = highlightAttributes; */        
    
//End of creating Polygons

//Start of creating Placemarks
/*var centroidImageCities = "images/pushpins/social_media_icons_flat_shadow_set_24x24_0002_twitter.png";
var centroidImageCountries = "images/pushpins/twitter_e.png";

var pinLibrary = WorldWind.configuration.baseUrl + "images/pushpins/";
var placemarkAttributes = new WorldWind.PlacemarkAttributes(null);

var placemark;
var highlightAttributes;

var latitude = value.centroidLatitude;
var longitude = value.centroidLongitude;

placemarkAttributes.imageScale = 1;
placemarkAttributes.imageOffset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.3, WorldWind.OFFSET_FRACTION, 0.0);
placemarkAttributes.imageColor = WorldWind.Color.WHITE;
placemarkAttributes.labelAttributes.offset = new WorldWind.Offset(WorldWind.OFFSET_FRACTION, 0.5, WorldWind.OFFSET_FRACTION, 1.0);

if (value.parentID == 1) {
	
    placemarkAttributes.labelAttributes.color = WorldWind.Color.BLUE;
    placemarkAttributes.imageSource = centroidImageCountries; 
    polygonAttributes.outlineColor = WorldWind.Color.BLUE;
}
else {
	
    placemarkAttributes.labelAttributes.color = WorldWind.Color.CYAN;
    placemarkAttributes.imageSource = centroidImageCities;
    polygonAttributes.outlineColor = WorldWind.Color.CYAN;
}

polygon.attributes = polygonAttributes;

placemarkAttributes.drawLeaderLine = true;
placemarkAttributes.leaderLineAttributes.outlineColor = WorldWind.Color.RED;

var placemarkPositon = new WorldWind.Position(value.centroidLatitude, value.centroidLongitude, 1e2);

placemark = new WorldWind.Placemark(placemarkPositon, true, null);

placemark.label = value.name + "\n"
    + "Lat "
    + value.centroidLatitude
    + "\n" + "Lon "
    + value.centroidLongitude
    + "\n" + "woeid "
    + value.woeid + "\n"
    + "PopRank" + "\t"
    + value.popRank + "\n"
    + "AreaRank" + "\t"
    + value.areaRank;

placemark.altitudeMode = WorldWind.RELATIVE_TO_GROUND;

//placemarkAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);

placemark.attributes = placemarkAttributes;

highlightAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);
highlightAttributes.imageScale = 0.6;// 1.2
placemark.highlightAttributes = highlightAttributes;*/

//End of creating Placemarks

/*locationsLayer.addRenderable(polygon);*/
/*placemarkLayer.addRenderable(placemark);*/
