function showConsumptionGraph(holonObjectId)
{
	//$("#consumptionGraphBody").html("<p>Hrnjdjshd</p>");
	
	var dataAttributes= {
			holonObjectId : holonObjectId
	}
	ajaxRequest("showHolonObjectConsumption", dataAttributes, getDataForConsumptionGraphCallBack, dataAttributes);
//	openDiv("consumptionGraphBody");
	
 //alert(holonObjectId);	
}

function getDataForConsumptionGraphCallBack(data,options)
{
	var graphForConsumption;
	var holonObjectId=data.split("*")[1];
	var currentConsumption=parseInt(data.split("*")[0]);
	console.log(holonObjectId+"****"+currentConsumption+"****"+data);
	openDiv("consumptionGraphBody");
	if(currentConsumption==0 || holonObjectId==undefined ){
		alert("No Holon Elements to show");
	closeDiv("consumptionGraphBody");
	}
	else{
		//openDiv("consumptionGraphBody");
		
		Highcharts.setOptions({
	        global: {
	            useUTC: false
	        }
	    });
		var graphForConsumption= new Highcharts.Chart({
	        chart: {
	        	renderTo: "consumptionGraphBody",
	            type: 'spline',
	            animation: Highcharts.svg, // don't animate in old IE
	            marginRight: 10,
	            events: {
	                load: function () {
	                	// set up the updating of the chart each second
	                    var series = this.series[0];

	                	if(series) { setInterval(function () {
	                        var x = (new Date()).getTime(), // current time
                            y = currentConsumption+ Math.floor((Math.random() * 10) + 1);
                        series.addPoint([x, y], true, true);
                    }, 1000); }	                    
	                }
	            }
	        },
	        title: {
	            text: 'Consumption Graph for Holon Object Id : '+holonObjectId
	        },
	        xAxis: {
	            type: 'datetime',
	            tickPixelInterval: 150
	        },
	        yAxis: {
	            title: {
	                text: 'Consumption'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            formatter: function () {
	                return '<b>' + this.series.name + '</b><br/>' +
	                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
	                    Highcharts.numberFormat(this.y, 2);
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        exporting: {
	            enabled: false
	        },
	        series: [{
	            name: 'Element Consumption',
	            data: (function () {
	                // generate an array of random data
	                var data1 = [],
	                    time = (new Date()).getTime(),
	                    i;

	                for (i = -19; i <= 0; i += 1) {
	                    data1.push({
	                        x: time + i * 1000,
	                        y: currentConsumption + Math.floor((Math.random() * 10) + 1)
	                    });
	                }
	                return data1; 
	            }())
	        }],
	        exporting: {
	            buttons: {
	            	contextButton: {
	                    enabled: false
	                },
	                closeButton: {
	                    text: 'Close',
	                    onclick: function () {
	                    	closeDiv('consumptionGraphBody');
	                    }
	                },
	                exportButton: {
	                    text: 'Download',
	                    // Use only the download related menu items from the default context button
	                    menuItems: Highcharts.getOptions().exporting.buttons.contextButton.menuItems.splice(2)
	                }
	                
	            }
	        }
	    });

	}
	    
	//Render To consumptionGraphBody
	console.log(data);
}