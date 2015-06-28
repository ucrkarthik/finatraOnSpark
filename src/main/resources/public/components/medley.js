
/**
 * Gets the value of the parameter specified in the URL.
 * @param name of the parameter
 */
function getParamValueByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


$.ajax({
    url: "wordCount?input.file=" + getParamValueByName("input.file"),
    success: function (response) {

        console.log(response.toSource());

        var width = 750,
            height = 500;

        var leaderScale = d3.scale.linear().range([10,60]);

        var fill = d3.scale.category20();

        leaderScale.domain([
            d3.min(response, function(d) { return d.size;}),
            d3.max(response, function(d) { return d.size;})
        ]);

        var layout = d3.layout.cloud()
            .size([width, height])
            .words(response)
            .padding(0)
            .font("Impact")
            .fontSize(function(d) { return leaderScale(d.size); })
            .on("end", drawCloud);
        layout.start();

        function drawCloud(words) {
            d3.select("#word-cloud").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + (width / 2) + "," + (height / 2) + ")")
                .selectAll("text")
                .data(words)
                .enter().append("text")
                .style("font-size", function(d) { return d.size + "px"; })
                .style("font-family", "Impact")
                .style("fill", function(d, i) { return fill(i); })
                .attr("text-anchor", "middle")
                .attr("transform", function(d) {
                    return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
                })
                .text(function(d) { return d.text; });
        }
    }
});

