var http = "http://";
var vmIP = "localhost";
var path = ":8080/nasa-www-webapp/trends/";

var limitQueryParam = "?limit=";

var listAllLocationsUrl = http + vmIP + path + "listAllLocations";
var listAllTrendsUrl = http + vmIP + path + "listAllTrends";
var getTrendsByNameUrl = http + vmIP + path + "getTrends/";
var getLocationsByNameUrl = http + vmIP + path + "getLocations/";
var getTopTrendsUrl = http + vmIP + path + "topTrendsToday/";
var getTopTrendsPerLocationUrl = http + vmIP + path + "topTrendsLocation/";


function findAllLocations() {
    $.ajax({
        method: "GET",
        url: listAllLocationsUrl,
        dataType: "json",
        success: function (data, status, jqXHR) {
            createDomforLocations(data);
        },
        error: function (xhr, status) {
            console.log("error");
            console.log(xhr);
            console.log(status);
        }
    });
}

function findAllTrends() {
    $.ajax({
        method: "GET",
        url: listAllTrendsUrl,
        dataType: "json",
        success: function (data, status, jqXHR) {
            createDomforTrends(data);
        },
        error: function (xhr, status) {
            console.log("error");
            console.log(xhr);
            console.log(status);
        }
    });
}

function findLocationByName(locationName) {
    $.ajax({
        method: "GET",
        url: getLocationsByNameUrl + locationName,
        dataType: "json",
        success: function (data, status, jqXHR) {
            createDomforLocationPerName(data);
        },
        error: function (xhr, status) {
            console.log("error");
            console.log(xhr);
            console.log(status);
        }
    });
}

function getAnnotationData(trendName, position) {
	console.log(trendName)
    $.ajax({
        method: "GET",
        url: getTrendsByNameUrl + encodeURI(trendName),
        dataType: "json",
        success: function (data, status, jqXHR) {
            drawAnnotation(data, position);
        },
        error: function (xhr, status) {
            console.log("error");
            console.log(xhr);
            console.log(status);
        }
    });
}

function findTrendByName(trendName) {
    $.ajax({
        method: "GET",
        url: getTrendsByNameUrl + trendName,
        dataType: "json",
        success: function (data, status, jqXHR) {
            createDomforTrendPerName(data);
        },
        error: function (xhr, status) {
            console.log("error");
            console.log(xhr);
            console.log(status);
        }
    });
}

function findTopTrends(counter) {
    $.ajax({
        method: "GET",
        url: getTopTrendsUrl + counter,
        dataType: "json",
        success: function (data, status, jqXHR) {
            createDomforTopTrends(data);
        },
        error: function (xhr, status) {
            console.log("error");
            console.log(xhr);
            console.log(status);
        }
    });
}

function getAllLocations(callback) {
    $.ajax({
        method: "GET",
        url: listAllLocationsUrl,
        dataType: "json",
        success: function (data, status, jqXHR) {
            callback(data);
        },
        error: function (xhr, status) {
            console.log("error");
            console.log(xhr);
            console.log(status);
        }
    });
}

function topTrendsPerLocation(location, limit, active, callback) {
	var url = getTopTrendsPerLocationUrl + location.woeid;
	if (limit != undefined && active != undefined) {
		url = url + "?limit=" + limit + "&active=" + active;
	} else if (limit != undefined) {
		url = url + "?limit=" + limit;
	} else if (active != undefined) {
		url = url + "?active=" + active;
	}
	
    $.ajax({
        method: "GET",
        url: url,
        dataType: "json",
        success: function (data, status, jqXHR) {
        	callback(data, active);
        },
        error: function (xhr, status) {
            console.log("error");
            console.log(xhr);
            console.log(status);
        }
    });
}
