(function() {
	var module = angular.module("couponSystem");

	module.controller("404Ctrl", go)
	function go() {
		window.location = "404.html";
	}
})();