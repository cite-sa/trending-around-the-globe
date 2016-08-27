/*
 Functions to fill dom with some of the data of our dataset
 */

function createModal(id) {
	var html = '<div id=' + id + ' class="modal fade" tabindex="-1" role="dialog">' +
	  '<div class="modal-dialog modal-lg" role="document">' +
	    '<div class="modal-content">' +
	      '<div class="modal-header">' +
	        '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' + 
	        	'<span aria-hidden="true">&times;</span>' +
        	'</button>' +
	        '<h4 class="modal-title"></h4>' +
	      '</div>' +
	      '<div class="modal-body">' +
	      '</div>' +
	    '</div>' +
	  '</div>' +
	'</div>';
	
	
	$("body").append(html);
	
	$('#city-modal').on('hidden.bs.modal', function (e) {
		  
	});
	
}

function buildModal(location) {
	var cityModal = $("#city-modal");
	cityModal.find(".modal-title").first().text(location.name + ", " + location.country);
	buildCityModalBody(location);
	
}

function buildCityModalBody(location) {
	
	var cityModalBody = $("#city-modal").find(".modal-body").first();
	cityModalBody.empty();

	var trendsBodyHtml = '<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">' +
				'<div class="panel panel-default">' +
					'<div class="panel-heading" role="tab" id="current-trends-heading">' +
						'<h4 class="panel-title">' +
							'<a role="button"' + 
								'data-toggle="collapse"' +
								'data-parent="#accordion"' + 
								'href="#current-trends"' +
								'aria-expanded="true"' +
								'aria-controls="collapseOne">' +
								'Current Trends' +
						'</a>' +
					'</h4>' +
				'</div>' +
				'<div id="current-trends" class="panel-collapse collapse" role="tabpanel"' + 
					'aria-labelledby="current-trends-heading">' +
					'<div class="panel-body"></div>' +
				'</div>' +
			'</div>' +
			'<div class="panel panel-default">' +
		    '<div class="panel-heading" role="tab" id="past-trends-heading">' +
		      '<h4 class="panel-title">' +
		        '<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#past-trends" aria-expanded="false" aria-controls="collapseTwo">' +
		          'Past Trends' +
		        '</a>' +
		      '</h4>' +
		    '</div>' +
		    '<div id="past-trends" class="panel-collapse collapse" role="tabpanel" aria-labelledby="past-trends-heading">' +
		      '<div class="panel-body"></div>' +
		    '</div>' +
		  '</div>' +
		  '</div>';
	
	var trendsBody = $(trendsBodyHtml);
	cityModalBody.append(trendsBody);
	
	topTrendsPerLocation(location, undefined, 1, buildTrendsDropdown);
	topTrendsPerLocation(location, undefined, 0, buildTrendsDropdown);
	
	$("#city-modal").modal('show');
	
}

function buildTrendsDropdown(trends, active) {
		  
	var buildTrendRow = function(trend) {
		return $("<div>", {class: "row"})
			.append($("<a>", {id: trend.trendId, class: "col-xs-12", href: trend.url, target: "_blank"}).text(trend.name));
	};
	
	var currentTrends = $("#current-trends").children(".panel-body");
	var pastTrends = $("#past-trends").children(".panel-body");
	
	if (trends.length > 0) {
		$.each(trends, function(index, trend) {
			if (active == 1) {
				currentTrends.append(buildTrendRow(trend));
			} else if (active == 0) {
				pastTrends.append(buildTrendRow(trend));				
			}
		});
	} else {
		if (active == 1) {
			currentTrends.text("No current trends to display");
		} else if (active == 0) {
			pastTrends.text("No past trends to display");	
		}
	}
	if (active == 0) {
		if (currentTrends.children().size() == 0 && pastTrends.children().size() == 0) {

			$("#city-modal").find(".modal-body").first().empty().append("No trends to display");
		}
	}

	
}

function createDomforLocations(data) {
    var input = '';
    $.each(data, function (index, value) {
        input = input + '<div><p>' + value.name + ' : ' + value.woeid + '</p></div>';
        if (index > 10) {
            return false;
        }
    });
    $('.locationResult').append(input);
}

function createDomforTrends(data) {
    var input = '';
    $.each(data, function (index, value) {
        input = input + '<div><p>' + value.name + ' : ' + value.locations.tweet_volume + '</p></div>';
        if (index > 10) {
            return false;
        }
    });
    $('.trendResult').append(input);
}

function createDomforLocationPerName(data) {
    var input = '';
    $.each(data, function (index, value) {
        input = input + '<div><p>' + value.name + ' : ' + value.woeid + '</p></div>';
        if (index > 10) {
            return false;
        }
    });
    $('.locationNameResult').append(input);
}

function createDomforTrendPerName(data) {
    var input = '';
    $.each(data, function (index, value) {
        input = input + '<div><p>' + value.name + ' : ' + value.tweet_volume + '</p></div>';
        if (index > 10) {
            return false;
        }
    });
    $('.trendNameResult').append(input);
}

function createDomforTopTrends(data) {
    var input = '';
    $.each(data, function (index, value) {
        input = input + '<div><p>' + value.name + ' : ' + value.locations.tweet_volume + '</p></div>';
        if (index > 10) {
            return false;
        }
    });
    $('.topTrendsResult').append(input);
}
