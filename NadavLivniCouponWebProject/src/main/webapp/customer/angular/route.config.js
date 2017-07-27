(function() {

	var module = angular.module("couponSystem");

	// http://stackoverflow.com/questions/41211875/angularjs-1-6-0-latest-now-routes-not-working
	module.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.hashPrefix('');
	} ]);

	// router config
	module.config(function($stateProvider, $urlRouterProvider) {

		$stateProvider

		.state("/getAllPurchasedCoupons", {
			url : "/get",
			templateUrl : 'html/getallpurchasedcoupons.html',
			controller : "GetAllCouponsCtrl as g"
		}).state("couponbytype", {
			url : "/gettype",
			templateUrl : "html/couponbytype.html",
			controller : "CouponByTypeCtrl as t"
		}).state("purchasedbyprice", {
			url : "/getprice",
			templateUrl : "html/couponbyprice.html",
			controller : "CouponsByPriceCtrl as pr"
		}).state("purchaseCoupon", {
			url : "/purchase",
			templateUrl : "html/purchasecoupon.html",
			controller : "PurchaseCouponCtrl as pu"
		}).state("404", {
			url : "/404",
			controller : "404Ctrl as err"
		}).state("home", {
			url : "/home",
			templateUrl : "html/home.html",
		});

		$urlRouterProvider.when("", "/home"); // first browsing postfix is
		$urlRouterProvider.otherwise(function($injector, $location) {
			var $state = $injector.get('$state');
			$state.go('404', {
				title : "Page not found",
				message : 'Could not find a state associated with url "'
						+ $location.$$url + '"'
			});
		});
	});

})();