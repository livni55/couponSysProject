(function() {

	var module = angular.module("couponSystem");

	module.controller("GetCompanyCtrl", GetCompanyCtrlCtor);

	function GetCompanyCtrlCtor(mainCompanyService) {
		this.company = {};
		var self = this;
		var failure=false;
		var promiseGet = mainCompanyService.getCompany();
		promiseGet.then(function(resp) {
			self.company = resp.data;
		}).catch(function(err) {
			this.failure=true;
			$("#fail").text(err.data);
		});

	}
	// }

})();