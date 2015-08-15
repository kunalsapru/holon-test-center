
function showConsumptionGraph(holonElementId,holonObjectId,holonType)
{
	//$("#consumptionGraphBody").html("<p>Hrnjdjshd</p>");
	if(holonType=="ho"){
		var dataAttributes= {
				holonObjectId : holonObjectId
		}
		ajaxRequest("showHolonObjectConsumption", dataAttributes, getDataForConsumptionGraphCallBack, dataAttributes);
	}else{
		var currrentConsumption=holonType;
		showGraphForHolonTypes(holonElementId,parseInt(currrentConsumption),"Holon Element");
	}
	
//	openDiv("consumptionGraphBody");
	
 //alert(holonObjectId);	
}

function getDataForConsumptionGraphCallBack(data,options)
{
	var graphForConsumption;
	var holonObjectId=data.split("*")[1];
	var currentConsumption=parseInt(data.split("*")[0]);
	showGraphForHolonTypes(holonObjectId,currentConsumption,"Holon Object");
	
}

function showGraphForHolonTypes(id,currentConsumption,holonType)
{
	if(currentConsumption==0 || id==undefined ){
		alert("No Data to show");
	}
	else{
		openDiv("consumptionGraphBody");
		
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
	            //marginRight: 10,
	            events: {
	                load : function (event) {
	                	// set up the updating of the chart each second
	                    var series = this.series[0];
	                 intervalTimeConsumptionGraph = setInterval(function () {
	                        var x = (new Date()).getTime(), // current time
                            y = currentConsumption+ Math.floor((Math.random() * 10) + 1);
                        series.addPoint([x, y], true, true);
                    }, 1000);                    
	                }
	            }
	        },
	        credits:{
	        	enabled : false
	        },
	        title: {
	            text: 'Consumption Graph for ' +holonType+":"+ +id,
	            align: "left"
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
	                    	clearInterval(intervalTimeConsumptionGraph);
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
	  
	
}