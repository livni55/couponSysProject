(function() {

	var module = angular.module("couponSystem");

	module.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.hashPrefix('');
	} ]);

	module.config(function($stateProvider, $urlRouterProvider) {

		$stateProvider.state("manageCompanies", {
			url : "/manageCompanies",
			templateUrl : "html/manageCompanies.html",
			controller : "CompaniesCtrl as companiesCtrl"
		}).state("CreateCompanyCtrl", {
			url : "/createcompany",
			templateUrl : "html/createcompany.html",
			controller : "CreateCompanyCtrl as createcompany"
		}).state("manageCustomers", {
			url : "/manageCustomers",
			templateUrl : "html/manageCustomers.html",
			controller : "CustomerCtrl as customerCtrl"
		}).state("CreateCustomerCtrl", {
			url : "/createcustomer",
			templateUrl : "html/createcustomer.html",
			controller : "CreateCustomerCtrl as createcustomer"
		}).state("404", {
			url : "/404",
			controller : "404Ctrl as err"
		}).state("home", {
			url : "/home",
			templateUrl : "html/home.html",
		});

		$urlRouterProvider.when("", "/home");
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