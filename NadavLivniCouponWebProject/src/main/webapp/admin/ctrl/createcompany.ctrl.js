(function() {

	var module = angular.module("couponSystem");

	module.controller("CreateCompanyCtrl", CreateCompanyCtrlCtor);

	// Ctor method for the CreateCouponCtrl
	function CreateCompanyCtrlCtor(mainAdminServiceHTTP) {
		var self = this;
		this.newCompany;
		this.success = false;
		this.failure = false;

		this.createCompany = function() {
			this.createButton = true;
			this.pressedButton = function() {
				this.createButton = true;
			}
			if (this.newCompany == undefined || this.newCompany.id == undefined
					|| this.newCompany.compName == undefined
					|| this.newCompany.password == undefined
					|| this.newCompany.email == undefined) {
				this.success = false;
				this.failure = true;
				return;
			}

			this.success = false;
			this.failure = false;
			var self = this;

			var promisePost = mainAdminServiceHTTP
					.createCompany(this.newCompany);
			promisePost.then(function(resp) {
				self.companies = resp.data;
				self.newCompany = {};
				self.success = true;
				self.failure = false;

			}, function(err) {
				if (err.status == 404) {
					window.location.replace("../404.html");
				}
				self.success = false;
				self.failure = true;
			});

		}

	}

})();